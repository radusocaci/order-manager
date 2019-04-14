package Presentation.Controllers;

import Business_Logic.CustomerBLL;
import Business_Logic.ProductBLL;
import Business_Logic.StockBLL;
import Data_Access.DAO.OrderDAO;
import Model.Customer;
import Model.Order;
import Model.Product;
import Model.Stock;
import Presentation.Views.OrderPanel;
import Presentation.Views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Defines all functionality for the user interaction with the order panel
 *
 * @author Socaci Radu Andrei
 */
public class OrderController {
    private View view;
    private OrderDAO orderDAO;
    private CustomerBLL customerBLL;
    private ProductBLL productBLL;
    private StockBLL stockBLL;

    /**
     * Instantiates all components and adds listener for the order panel buttons
     *
     * @param view        view reference
     * @param customerBLL customerBLL reference
     * @param productBLL  productBLL reference
     * @param stockBLL    stockBLL reference
     */
    public OrderController(View view, CustomerBLL customerBLL, ProductBLL productBLL, StockBLL stockBLL) {
        this.orderDAO = new OrderDAO();

        this.view = view;
        this.customerBLL = customerBLL;
        this.productBLL = productBLL;
        this.stockBLL = stockBLL;

        try {
            orderDAO.deleteAll();
            orderDAO.initId();
            customerBLL.deleteAll();
            productBLL.deleteAll();
        } catch (SQLException e) {
            view.showError(e.getMessage());
        }

        File directory = new File("bills");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] filesInFolder = directory.listFiles();
        if (filesInFolder != null) {
            Arrays.stream(filesInFolder).forEach(File::delete);
        }

        view.getOrderPanel().setOrderListener(new OrderListener());
        view.getOrderPanel().setGenerateBillListener(new BillListener());
    }

    /**
     * When the order button is pressed, a new order is created with the specified information.
     * Provides all input checking needed and handles all possible exception cases.
     */
    private class OrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            OrderPanel orderPanel = view.getOrderPanel();

            if (orderPanel.getCustomerIdTextField().isEmpty() || orderPanel.getProductIdTextField().isEmpty() || orderPanel.getQuantityTextField().isEmpty()) {
                view.showError("Need to specify customer, product and quantity!");
                return;
            }

            Customer customer = null;
            Product product = null;
            Stock stock = null;

            try {
                customer = customerBLL.get(Integer.parseInt(orderPanel.getCustomerIdTextField()));
                product = productBLL.get(Integer.parseInt(orderPanel.getProductIdTextField()));
            } catch (NoSuchElementException ex) {
                view.showError("Customer/Product ID need to be present in the database!");
                return;
            }

            if ((stock = stockBLL.get(product.getStockId())).getStock() < Integer.parseInt(orderPanel.getQuantityTextField())) {
                view.showError("Out of stock!");
                return;
            }

            try {
                orderDAO.insert(new Order(product.getId(), customer.getId(), Integer.parseInt(orderPanel.getQuantityTextField())));
                stockBLL.update(new Stock(product.getStockId(), stock.getStock() - Integer.parseInt(orderPanel.getQuantityTextField())));
            } catch (SQLException ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    /**
     * When the generate bills button is pressed, a bill is generated for each client in the Bills folder.
     * Provides all input checking needed and handles all possible exception cases.
     */
    private class BillListener implements ActionListener {
        private int totalPaid = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            Customer customer = null;
            List<Order> orders;
            FileWriter fileWriter = null;

            List<Integer> customers = orderDAO.findAll().stream().map(Order::getIdCustomer).distinct().collect(Collectors.toList());

            for (int id : customers) {
                customer = customerBLL.get(id);
                orders = orderDAO.findAll().stream().filter((order) -> (order.getIdCustomer() == id)).collect(Collectors.toList());

                File billFile = new File("bills/BillNo" + id + ".txt");

                try {
                    fileWriter = new FileWriter(billFile, false);
                    fileWriter.write(customer.toString() + "\n");

                    for (Order order : orders) {
                        fileWriter.write(generateBillOrder(order));
                    }

                    fileWriter.write("\nTotal Payment: " + totalPaid);
                    totalPaid = 0;

                    fileWriter.close();
                } catch (IOException ex) {
                    view.showError("Error generating bill for customer " + customer.getLastName() + " " + customer.getFirstName());
                }
            }
        }

        /**
         * Given an order object, it returns a print-ready string representation of the order
         *
         * @param order order object
         * @return string representation of order
         */
        private String generateBillOrder(Order order) {
            StringBuilder stringBuilder = new StringBuilder("Product Name: ");
            Product product = productBLL.get(order.getIdProduct());

            stringBuilder.append(product.toString()).append("Quantity: ");
            stringBuilder.append(order.getQuantity()).append("\t\tPrice: ");
            stringBuilder.append(product.getPrice()).append("\n");

            totalPaid += product.getPrice() * order.getQuantity();

            return stringBuilder.toString();
        }
    }
}
