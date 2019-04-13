package Presentation.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View extends JFrame {
    private CustomerPanel customerPanel;
    private ProductPanel productPanel;
    private OrderPanel orderPanel;

    public View(String title) throws HeadlessException {
        super(title);

        customerPanel = new CustomerPanel();
        productPanel = new ProductPanel();
        orderPanel = new OrderPanel();

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Customers", customerPanel);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Products", productPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        tabbedPane.addTab("Orders", orderPanel);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setContentPane(tabbedPane);
        setSize((int) screenSize.getWidth() * 3 / 4, (int) screenSize.getHeight() * 3 / 4);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static JTable createTable(List<? extends Object> objects) throws IndexOutOfBoundsException {
        Field[] fields = objects.get(0).getClass().getDeclaredFields();
        List<Object> objectList = new ArrayList<>();
        Arrays.stream(fields).forEach((field) -> field.setAccessible(true));
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, fields.length);
        JTable table = new JTable(defaultTableModel);

        defaultTableModel.setColumnIdentifiers(Arrays.stream(fields).map(Field::getName).toArray(String[]::new));

        for (Object object : objects) {
            for (Field field : fields) {
                Object value = null;

                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                objectList.add(value);
            }

            defaultTableModel.addRow(objectList.toArray());
            objectList.clear();
        }

        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.setDefaultRenderer(table.getColumnClass(i), defaultTableCellRenderer);
        }

        return table;
    }

    public void updateCustomerPanel(List<? extends Object> objects) {
        try {
            JTable table = View.createTable(objects);
            customerPanel.setCustomerTable(table);
        } catch (IndexOutOfBoundsException e) {
            customerPanel.setCustomerTable(new JTable());
        }
    }

    public void updateProductPanel(List<? extends Object> objects) {
        try {
            JTable table = View.createTable(objects);
            productPanel.setProductTable(table);
        } catch (IndexOutOfBoundsException e) {
            productPanel.setProductTable(new JTable());
        }
    }

    public CustomerPanel getCustomerPanel() {
        return customerPanel;
    }

    public ProductPanel getProductPanel() {
        return productPanel;
    }

    public OrderPanel getOrderPanel() {
        return orderPanel;
    }

    public void showError(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
