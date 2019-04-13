package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderPanel extends JPanel {
    private JButton order;
    private JButton generateBill;

    private JTextField customerIdTextField;
    private JTextField productIdTextField;
    private JTextField nrItemsTextField;

    private JLabel customerIdLabel;
    private JLabel productIdLabel;
    private JLabel nrItemsLabel;

    public OrderPanel() {
        order = new JButton("Place Order");
        generateBill = new JButton("Generate Bills");

        customerIdLabel = new JLabel("Customer ID");
        productIdLabel = new JLabel("Product ID");
        nrItemsLabel = new JLabel("Quantity");

        customerIdTextField = new JTextField("");
        productIdTextField = new JTextField("");
        nrItemsTextField = new JTextField("");

        customerIdTextField.setColumns(10);
        productIdTextField.setColumns(10);
        nrItemsTextField.setColumns(10);

        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dataPanel.add(customerIdLabel);
        dataPanel.add(customerIdTextField);
        dataPanel.add(productIdLabel);
        dataPanel.add(productIdTextField);
        dataPanel.add(nrItemsLabel);
        dataPanel.add(nrItemsTextField);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        btnPanel.add(order);
        btnPanel.add(generateBill);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(dataPanel);
        centerPanel.add(btnPanel);

        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        add(centerPanel);
    }

    public String getCustomerIdTextField() {
        return customerIdTextField.getText();
    }

    public String getProductIdTextField() {
        return productIdTextField.getText();
    }

    public String getQuentityTextField() {
        return nrItemsTextField.getText();
    }

    public void setOrderListener(ActionListener listener) {
        order.addActionListener(listener);
    }

    public void setGenerateBillListener(ActionListener listener) {
        generateBill.addActionListener(listener);
    }
}
