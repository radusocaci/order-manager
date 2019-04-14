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

/**
 * Provides interaction with the customer table of the database
 *
 * @author Socaci Radu Andrei
 */
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

    /**
     * Inserts a customer into the database
     *
     * @param customer customer
     * @return index of newly inserted client
     * @throws SQLException             throws for bad insertion
     * @throws IllegalArgumentException thrown for bad email
     */
    public int insert(Customer customer) throws SQLException, IllegalArgumentException {
        validator.validate(customer);
        customerDAO.insert(customer);
        customer.setId(idGenerator.getAndIncrement());
        return customer.getId();
    }

    /**
     * Updates a customer in the database
     *
     * @param customer customer
     * @return index of newly updated customer
     * @throws NoSuchElementException   throws if customer to update is not in the database
     * @throws SQLException             throws for bad update
     * @throws IllegalArgumentException thrown for bad email
     */
    public int update(Customer customer) throws NoSuchElementException, SQLException, IllegalArgumentException {
        get(customer.getId());
        validator.validate(customer);
        return customerDAO.update(customer, customer.getId()).getId();
    }

    /**
     * Deletes a customer from the database
     *
     * @param id customer id to delete
     * @throws NoSuchElementException throws if customer to delete is not in the database
     * @throws SQLException           throws for bad delete
     */
    public void delete(int id) throws NoSuchElementException, SQLException {
        get(id);

        List<Order> toDelete = orderDAO.findAll().stream().filter((order) -> (order.getIdCustomer() == id)).collect(Collectors.toList());
        for (Order order : toDelete) {
            orderDAO.delete(order.getId());
        }

        customerDAO.delete(id);
    }

    /**
     * Deletes all customers from the database
     *
     * @throws SQLException throws for bad delete
     */
    public void deleteAll() throws SQLException {
        customerDAO.deleteAll();
        customerDAO.initId();
    }

    /**
     * Retrieves all customers in the database
     *
     * @return customer list
     */
    public List<Customer> getAll() {
        return customerDAO.findAll();
    }

    /**
     * Retrieves customer with given id from the database
     *
     * @param id id
     * @return customer
     * @throws NoSuchElementException throws if customer to retrieve is not in the database
     */
    public Customer get(int id) throws NoSuchElementException {
        Customer customer = customerDAO.findById(id);

        if (customer == null) {
            throw new NoSuchElementException("The customer with id = " + id + " was not found!");
        }

        return customer;
    }
}