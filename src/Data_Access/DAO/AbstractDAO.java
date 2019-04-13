package Data_Access.DAO;

import Data_Access.Connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void initId() throws SQLException {
        String query = "ALTER TABLE `" + type.getSimpleName().toLowerCase() + "` AUTO_INCREMENT = 1;";

        executeUpdate(query);
    }

    public List<T> findAll() {
        String query = "SELECT * FROM `" + type.getSimpleName().toLowerCase() + "`;";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(resultSet);
        }

        return null;
    }

    public T findById(int id) {
        String query = "SELECT * FROM `" + type.getSimpleName().toLowerCase() + "` WHERE " + getFieldNames().get(0) + " = " + id;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);

            if (!result.isEmpty()) {
                return result.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {

        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }

                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                SecurityException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<String> getFieldNames() {
        List<String> fields = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field.getName());
        }

        return fields;
    }

    private List<String> getValuesAsString(T t) {
        List<String> values = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (value == null) {
                break;
            }

            if (value instanceof String) {
                values.add("'" + value + "'");
            } else {
                values.add(value.toString());
            }
        }

        return values;
    }

    public T insert(T t) throws SQLException {
        StringBuilder sb = new StringBuilder("INSERT INTO `" + type.getSimpleName().toLowerCase() + "` (");

        List<String> fieldNames = getFieldNames();

        for (int i = 1; i < fieldNames.size(); i++) {
            if (i == fieldNames.size() - 1) {
                sb.append(fieldNames.get(i) + ") VALUES (");
            } else {
                sb.append(fieldNames.get(i) + ", ");
            }
        }

        List<String> fieldValues = getValuesAsString(t);

        for (int i = 1; i < fieldValues.size(); i++) {
            if (i == fieldValues.size() - 1) {
                sb.append(fieldValues.get(i) + ");");
            } else {
                sb.append(fieldValues.get(i) + ", ");
            }
        }

        executeUpdate(sb.toString());

        return t;
    }

    public T update(T t, int id) throws SQLException {
        StringBuilder sb = new StringBuilder("UPDATE `" + type.getSimpleName().toLowerCase() + "` SET ");

        List<String> fieldNames = getFieldNames();
        List<String> fieldValues = getValuesAsString(t);

        for (int i = 1; i < fieldNames.size(); i++) {
            sb.append(fieldNames.get(i) + " = " + fieldValues.get(i));

            if (i == fieldNames.size() - 1) {
                sb.append(" WHERE " + fieldNames.get(0) + " = " + id);
            } else {
                sb.append(", ");
            }
        }

        executeUpdate(sb.toString());

        return t;
    }

    public void delete(int id) throws SQLException {
        List<String> fieldNames = getFieldNames();

        String query = "DELETE FROM `" + type.getSimpleName().toLowerCase() + "` WHERE " + fieldNames.get(0) + " = " + id;

        executeUpdate(query);
    }

    public void deleteAll() throws SQLException {
        String query = "DELETE FROM `" + type.getSimpleName().toLowerCase() + "`;";

        executeUpdate(query);
    }

    private void executeUpdate(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        connection = ConnectionFactory.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(sql);

        ConnectionFactory.close(connection);
        ConnectionFactory.close(statement);
    }
}
