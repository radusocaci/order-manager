package Business_Logic;

import Business_Logic.validators.EmailValidator;
import Business_Logic.validators.Validator;
import Data_Access.DAO.CustomerDAO;
import Data_Access.DAO.OrderDAO;
import Model.Customer;
import Model.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CustomerBLL {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private Validator<Customer> validator;

    public CustomerBLL() {
        this.customerDAO = new CustomerDAO();
        this.validator = new EmailValidator();
        this.orderDAO = new OrderDAO();
    }

    public int insert(Customer customer) throws SQLException, IllegalArgumentException {
        validator.validate(customer);
        customerDAO.insert(customer);
        customer.setId(idGenerator.getAndIncrement());
        return customer.getId();
    }

    public int update(Customer customer) throws NoSuchElementException, SQLException, IllegalArgumentException {
        get(customer.getId());
        validator.validate(customer);
        return customerDAO.update(customer, customer.getId()).getId();
    }

    public void delete(int id) throws NoSuchElementException, SQLException {
        get(id);

        List<Order> toDelete = orderDAO.findAll().stream().filter((order) -> (order.getIdCustomer() == id)).collect(Collectors.toList());
        for (Order order : toDelete) {
            orderDAO.delete(order.getId());
        }

        customerDAO.delete(id);
    }

    public void deleteAll() throws SQLException {
        customerDAO.deleteAll();
        customerDAO.initId();
    }

    public List<Customer> getAll() {
        return customerDAO.findAll();
    }

    public Customer get(int id) throws NoSuchElementException {
        Customer customer = customerDAO.findById(id);

        if (customer == null) {
            throw new NoSuchElementException("The customer with id = " + id + " was not found!");
        }

        return customer;
    }
}