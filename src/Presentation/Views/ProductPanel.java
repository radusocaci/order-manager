package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Defines the product related part of the GUI
 *
 * @author Socaci Radu Andrei
 */
public class ProductPanel extends JPanel {
    private JTable productTable;

    private JButton addProduct;
    private JButton deleteProduct;
    private JButton updateProduct;

    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;

    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;

    /**
     * Creates the look and feel of the panel and instantiates all components
     */
    public ProductPanel() {
        productTable = new JTable();

        addProduct = new JButton("Add Product");
        updateProduct = new JButton("Update Product");
        deleteProduct = new JButton("Delete Product");

        idTextField = new JTextField("");
        nameTextField = new JTextField("");
        priceTextField = new JTextField("");
        stockTextField = new JTextField("");

        idLabel = new JLabel("ID");
        nameLabel = new JLabel("Name");
        priceLabel = new JLabel("Price");
        stockLabel = new JLabel("In Stock");

        idTextField.setColumns(10);
        nameTextField.setColumns(10);
        priceTextField.setColumns(10);
        stockTextField.setColumns(10);

        productTable.setFillsViewportHeight(true);

        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dataPanel.add(idLabel);
        dataPanel.add(idTextField);
        dataPanel.add(nameLabel);
        dataPanel.add(nameTextField);
        dataPanel.add(priceLabel);
        dataPanel.add(priceTextField);
        dataPanel.add(stockLabel);
        dataPanel.add(stockTextField);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        btnPanel.add(addProduct);
        btnPanel.add(updateProduct);
        btnPanel.add(deleteProduct);

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(dataPanel);
        southPanel.add(btnPanel);

        setLayout(new BorderLayout());
        add(productTable, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Setter method for the product table (JTable).
     * Also refreshes the view to display the new table.
     *
     * @param productTable product table
     */
    public void setProductTable(JTable productTable) {
        this.productTable = productTable;
        JScrollPane scrollPane = new JScrollPane(productTable);
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
     * Returns the content of the name text field as a string
     *
     * @return name as string
     */
    public String getNameTextField() {
        return nameTextField.getText();
    }

    /**
     * Returns the content of the price text field as a string
     *
     * @return price as string
     */
    public String getPriceTextField() {
        return priceTextField.getText();
    }

    /**
     * Returns the content of the stock text field as a string
     *
     * @return stock as string
     */
    public String getStockTextField() {
        return stockTextField.getText();
    }

    /**
     * Resets all text fields
     */
    public void resetDataPanel() {
        idTextField.setText("");
        nameTextField.setText("");
        priceTextField.setText("");
        stockTextField.setText("");
    }

    /**
     * Adds action listener for the insert button
     *
     * @param listener insert action listener
     */
    public void setAddProductListener(ActionListener listener) {
        addProduct.addActionListener(listener);
    }

    /**
     * Adds action listener for the update button
     *
     * @param listener update action listener
     */
    public void setUpdateProductListener(ActionListener listener) {
        updateProduct.addActionListener(listener);
    }

    /**
     * Adds action listener for the delete button
     *
     * @param listener delete action listener
     */
    public void setDeleteProductListener(ActionListener listener) {
        deleteProduct.addActionListener(listener);
    }
}
