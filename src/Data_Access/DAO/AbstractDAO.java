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

/**
 * Provides basic CRUD functionality for the given table
 *
 * @param <T> reference to a table in the database
 */
public class AbstractDAO<T> {
    private final Class<T> type;

    /**
     * Retrieves the class type for the generic argument
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Initializes the id field (autoincrement)
     *
     * @throws SQLException thrown for illegal access
     */
    public void initId() throws SQLException {
        String query = "ALTER TABLE `" + type.getSimpleName().toLowerCase() + "` AUTO_INCREMENT = 1;";

        executeUpdate(query);
    }

    /**
     * Retrieves all records from the table
     *
     * @return list of records of type T
     */
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

    /**
     * Retrieves the record having the given id
     *
     * @param id id
     * @return record with id = id
     */
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

    /**
     * Given a resultSet, creates a list of records
     *
     * @param resultSet result set from database
     * @return list of records from the result set
     */
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

    /**
     * Retrieves all field names of the given generic type as a list of strings
     *
     * @return list of strings
     */
    private List<String> getFieldNames() {
        List<String> fields = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field.getName());
        }

        return fields;
    }

    /**
     * Retrieves all field values for the given object as a list of strings
     *
     * @param t record object
     * @return list of strings
     */
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

    /**
     * Insert a record in the database table referenced by the generic type T
     *
     * @param t record object
     * @return inserted object
     * @throws SQLException thrown for bad insertion/access
     */
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

    /**
     * Updates a record in the database table referenced by the generic type T.
     * The record to be updated is the one with id = given id
     *
     * @param t  record object
     * @param id id
     * @return inserted object
     * @throws SQLException thrown for bad update/access
     */
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

    /**
     * Deletes a record from the database table referenced by the generic type T.
     * The record to be deleted is the one with id = id
     *
     * @param id id
     * @throws SQLException thrown for bad delete/access
     */
    public void delete(int id) throws SQLException {
        List<String> fieldNames = getFieldNames();

        String query = "DELETE FROM `" + type.getSimpleName().toLowerCase() + "` WHERE " + fieldNames.get(0) + " = " + id;

        executeUpdate(query);
    }

    /**
     * Deletes all records from the database table referenced by the generic type T.
     *
     * @throws SQLException thrown for bad delete/access
     */
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM `" + type.getSimpleName().toLowerCase() + "`;";

        executeUpdate(query);
    }

    /**
     * Helper method used for updating the database.
     * Used by insert, delete, init and update
     *
     * @param sql string representation of the query to be executed
     * @throws SQLException thrown for bad sql/operation
     */
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
