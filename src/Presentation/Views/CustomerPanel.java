package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Defines the customer related part of the GUI
 *
 * @author Socaci Radu Andrei
 */
public class CustomerPanel extends JPanel {
    private JTable customerTable;

    private JButton addCustomer;
    private JButton deleteCustomer;
    private JButton updateCustomer;

    private JTextField idTextField;
    private JTextField lastNameTextField;
    private JTextField firstNameTextField;
    private JTextField emailTextField;
    private JTextField addressTextField;

    private JLabel idLabel;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;

    /**
     * Creates the look and feel of the panel and instantiates all components
     */
    public CustomerPanel() {
        customerTable = new JTable();

        addCustomer = new JButton("Add Customer");
        updateCustomer = new JButton("Update Customer");
        deleteCustomer = new JButton("Delete Customer");

        idTextField = new JTextField("");
        lastNameTextField = new JTextField("");
        firstNameTextField = new JTextField("");
        emailTextField = new JTextField("");
        addressTextField = new JTextField("");

        idLabel = new JLabel("ID");
        lastNameLabel = new JLabel("Last Name");
        firstNameLabel = new JLabel("First Name");
        emailLabel = new JLabel("email");
        addressLabel = new JLabel("Address");

        idTextField.setColumns(10);
        lastNameTextField.setColumns(10);
        firstNameTextField.setColumns(10);
        emailTextField.setColumns(10);
        addressTextField.setColumns(10);

        customerTable.setFillsViewportHeight(true);

        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dataPanel.add(idLabel);
        dataPanel.add(idTextField);
        dataPanel.add(lastNameLabel);
        dataPanel.add(lastNameTextField);
        dataPanel.add(firstNameLabel);
        dataPanel.add(firstNameTextField);
        dataPanel.add(emailLabel);
        dataPanel.add(emailTextField);
        dataPanel.add(addressLabel);
        dataPanel.add(addressTextField);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        btnPanel.add(addCustomer);
        btnPanel.add(updateCustomer);
        btnPanel.add(deleteCustomer);

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(dataPanel);
        southPanel.add(btnPanel);

        setLayout(new BorderLayout());
        add(customerTable, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Setter method for the customer table (JTable).
     * Also refreshes the view to display the new table.
     *
     * @param customerTable customer table
     */
    public void setCustomerTable(JTable customerTable) {
        this.customerTable = customerTable;
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER, 0);

        remove(1);
        revalidate();
        repaint();
    }

    /**
     * Returns the content of the id text field as a string
     *
     * @return id as string
     */
    public String getIdTextField() {
        return idTextField.getText();
    }

    /**
     * Returns the content of the last name text field as a string
     *
     * @return last name as string
     */
    public String getLastNameTextField() {
        return lastNameTextField.getText();
    }

    /**
     * Returns the content of the first name text field as a string
     *
     * @return first name as string
     */
    public String getFirstNameTextField() {
        return firstNameTextField.getText();
    }

    /**
     * Returns the content of the email text field as a string
     *
     * @return email as string
     */
    public String getEmailTextField() {
        return emailTextField.getText();
    }

    /**
     * Returns the content of the address text field as a string
     *
     * @return address as string
     */
    public String getAddressTextField() {
        return addressTextField.getText();
    }

    /**
     * Resets all text fields
     */
    public void resetDataPanel() {
        idTextField.setText("");
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        emailTextField.setText("");
        addressTextField.setText("");
    }

    /**
     * Adds action listener for the insert button
     *
     * @param listener insert action listener
     */
    public void setAddCustomerListener(ActionListener listener) {
        addCustomer.addActionListener(listener);
    }

    /**
     * Adds action listener for the update button
     *
     * @param listener update action listener
     */
    public void setUpdateCustomerListener(ActionListener listener) {
        updateCustomer.addActionListener(listener);
    }

    /**
     * Adds action listener for the delete button
     *
     * @param listener delete action listener
     */
    public void setDeleteCustomerListener(ActionListener listener) {
        deleteCustomer.addActionListener(listener);
    }
}
