package Business_Logic;

import Business_Logic.validators.EmailValidator;
import Business_Logic.validators.Validator;
import Data_Access.DAO.CustomerDAO;
import Model.Customer;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerBLL {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private CustomerDAO customerDAO;
    private Validator<Customer> validator;

    public CustomerBLL() {
        this.customerDAO = new CustomerDAO();
        this.validator = new EmailValidator();
        customerDAO.initId();
    }

    public int insert(Customer customer) {
        try {
            validator.validate(customer);
            customerDAO.insert(customer);
            customer.setId(idGenerator.getAndIncrement());
            return customer.getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(Customer customer) throws NoSuchElementException {
        try {
            get(customer.getId());
            validator.validate(customer);
            return customerDAO.update(customer, customer.getId()).getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void delete(int id) {
        customerDAO.delete(id);
    }

    public void deleteAll() {
        customerDAO.deleteAll();
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