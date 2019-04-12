package Presentation;

import javax.swing.*;
import java.awt.*;

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

    public void setProductTable(JTable productTable) {
        this.productTable = productTable;
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER, 0);

        remove(1);
        revalidate();
        repaint();
    }
}
