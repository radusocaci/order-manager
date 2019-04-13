package Presentation.Controllers;

import Business_Logic.ProductBLL;
import Business_Logic.StockBLL;
import Model.Product;
import Presentation.Views.ProductPanel;
import Presentation.Views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ProductController {
    private View view;
    private ProductBLL productBLL;

    public ProductController(View view) {
        this.view = view;
        this.productBLL = new ProductBLL();

        view.getProductPanel().setAddProductListener(new AddProductListener());
        view.getProductPanel().setUpdateProductListener(new UpdateProductListener());
        view.getProductPanel().setDeleteProductListener(new DeleteProductListener());
    }

    public ProductBLL getProductBLL() {
        return productBLL;
    }

    public StockBLL getStockBLL() {
        return productBLL.getStockBLL();
    }

    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductPanel productPanel = view.getProductPanel();

            if (productPanel.getNameTextField().isEmpty() || productPanel.getPriceTextField().isEmpty() || productPanel.getStockTextField().isEmpty()) {
                view.showError("Need to specify name, price and stock!");
                return;
            }

            Product product = new Product(productPanel.getNameTextField(), Integer.parseInt(productPanel.getPriceTextField()));

            try {
                productBLL.insert(product, Integer.parseInt(productPanel.getStockTextField()));
                view.updateProductPanel(productBLL.getAll());
                productPanel.resetDataPanel();
            } catch (SQLException | IllegalArgumentException ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    private class UpdateProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductPanel productPanel = view.getProductPanel();

            if (productPanel.getIdTextField().isEmpty()) {
                view.showError("Unspecified id!");
                return;
            }

            if (productPanel.getNameTextField().isEmpty() || productPanel.getPriceTextField().isEmpty()) {
                view.showError("Need to specify new name and price!");
                return;
            }

            Product product = new Product(Integer.parseInt(productPanel.getIdTextField()), productPanel.getNameTextField(),
                    Integer.parseInt(productPanel.getPriceTextField()));

            try {
                product.setStockId(productBLL.get(product.getId()).getStockId());
                productBLL.update(product);
                view.updateProductPanel(productBLL.getAll());
                productPanel.resetDataPanel();
            } catch (NoSuchElementException ex) {
                view.showError("Product with id = " + product.getId() + " doesn't exist!");
            } catch (SQLException | IllegalArgumentException ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    private class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.getProductPanel().getIdTextField().isEmpty()) {
                view.showError("Unspecified id!");
                return;
            }

            try {
                productBLL.delete(Integer.parseInt(view.getProductPanel().getIdTextField()));
                view.updateProductPanel(productBLL.getAll());
                view.getProductPanel().resetDataPanel();
            } catch (NoSuchElementException ex) {
                view.showError("Product with id = " + Integer.parseInt(view.getProductPanel().getIdTextField()) + " doesn't exist!");
            } catch (SQLException ex) {
                view.showError(ex.getMessage());
            }
        }
    }
}
