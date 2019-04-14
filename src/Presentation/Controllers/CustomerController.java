package Presentation.Controllers;

import Business_Logic.CustomerBLL;
import Model.Customer;
import Presentation.Views.CustomerPanel;
import Presentation.Views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Defines all functionality for the user interaction with the customer panel
 *
 * @author Socaci Radu Andrei
 */
public class CustomerController {
    private View view;
    private CustomerBLL customerBLL;

    /**
     * Instantiates all components and adds listener for the customer panel buttons
     *
     * @param view view reference
     */
    public CustomerController(View view) {
        this.view = view;
        this.customerBLL = new CustomerBLL();

        view.getCustomerPanel().setAddCustomerListener(new AddCustomerListener());
        view.getCustomerPanel().setUpdateCustomerListener(new UpdateCustomerListener());
        view.getCustomerPanel().setDeleteCustomerListener(new DeleteCustomerListener());
    }

    /**
     * Customer BLL getter. Does not allow multiple definitions
     *
     * @return customerBLL
     */
    public CustomerBLL getCustomerBLL() {
        return customerBLL;
    }

    /**
     * When the add customer button is pressed, a new customer is inserted in the database.
     * Provides all input checking needed and handles all possible exception cases.
     */
    private class AddCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerPanel customerPanel = view.getCustomerPanel();

            if (customerPanel.getLastNameTextField().isEmpty() || customerPanel.getLastNameTextField().isEmpty() ||
                    customerPanel.getLastNameTextField().isEmpty() || customerPanel.getLastNameTextField().isEmpty()) {
                view.showError("Need to specify last name, first name, email and address!");
                return;
            }

            Customer customer = new Customer(customerPanel.getLastNameTextField(), customerPanel.getFirstNameTextField(),
                    customerPanel.getEmailTextField(), customerPanel.getAddressTextField());

            try {
                customerBLL.insert(customer);
                view.updateCustomerPanel(customerBLL.getAll());
                customerPanel.resetDataPanel();
            } catch (SQLException | IllegalArgumentException ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    /**
     * When the update customer button is pressed, the customer with the given id is updated in the database.
     * Provides all input checking needed and handles all possible exception cases.
     */
    private class UpdateCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerPanel customerPanel = view.getCustomerPanel();

            if (customerPanel.getIdTextField().isEmpty()) {
                view.showError("Unspecified id!");
                return;
            }

            if (customerPanel.getLastNameTextField().isEmpty() || customerPanel.getLastNameTextField().isEmpty() ||
                    customerPanel.getLastNameTextField().isEmpty() || customerPanel.getLastNameTextField().isEmpty()) {
                view.showError("Need to specify new last name, first name, email and address!");
                return;
            }

            Customer customer = new Customer(Integer.parseInt(customerPanel.getIdTextField()), customerPanel.getLastNameTextField(),
                    customerPanel.getFirstNameTextField(), customerPanel.getEmailTextField(), customerPanel.getAddressTextField());

            try {
                customerBLL.update(customer);
                view.updateCustomerPanel(customerBLL.getAll());
                customerPanel.resetDataPanel();
            } catch (NoSuchElementException ex) {
                view.showError("Customer with id = " + customer.getId() + " doesn't exist!");
            } catch (SQLException | IllegalArgumentException ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    /**
     * When the delete customer button is pressed, the customer with given id is deleted from the database.
     * Provides all input checking needed and handles all possible exception cases.
     */
    private class DeleteCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.getCustomerPanel().getIdTextField().isEmpty()) {
                view.showError("Unspecified id!");
                return;
            }

            try {
                customerBLL.delete(Integer.parseInt(view.getCustomerPanel().getIdTextField()));
                view.updateCustomerPanel(customerBLL.getAll());
                view.getCustomerPanel().resetDataPanel();
            } catch (NoSuchElementException ex) {
                view.showError("Customer with id = " + Integer.parseInt(view.getCustomerPanel().getIdTextField()) + " doesn't exist!");
            } catch (SQLException ex) {
                view.showError(ex.getMessage());
            }
        }
    }
}
