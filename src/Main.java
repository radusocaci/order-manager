import Presentation.Controllers.CustomerController;
import Presentation.Controllers.OrderController;
import Presentation.Controllers.ProductController;
import Presentation.Views.View;

public class Main {
    public static void main(String[] args) {
        View view = new View("Order Manager");

        CustomerController customerController = new CustomerController(view);
        ProductController productController = new ProductController(view);
        OrderController orderController = new OrderController(view, customerController.getCustomerBLL(),
                productController.getProductBLL(), productController.getStockBLL());

        view.setVisible(true);
    }
}