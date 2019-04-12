import Business_Logic.CustomerBLL;
import Business_Logic.ProductBLL;
import Model.Customer;
import Model.Product;
import Presentation.View;

public class Main {
    public static void main(String[] args) {
        CustomerBLL customerBLL = new CustomerBLL();
        ProductBLL productBLL = new ProductBLL();

        for (int i = 0; i < 20; i++) {
            customerBLL.insert(new Customer("Socaci", "Radu", "l" + i + "loree@yahoo.com", "caca" + i));
            productBLL.insert(new Product("Lore" + i, 99999), i);
        }

        View view = new View("Order Manager");
        view.setVisible(true);
        view.setCustomerPanel(customerBLL.getAll());
        view.setProductPanel(productBLL.getAll());

        for (int i = 0; i < 10; i++) {
            customerBLL.delete(i + 1);
            productBLL.delete(i + 1);
        }

        view.setCustomerPanel(customerBLL.getAll());
        view.setProductPanel(productBLL.getAll());

        productBLL.deleteAll();
        customerBLL.deleteAll();
    }
}