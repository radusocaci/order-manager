import Data_Access.DAO.CustomerDAO;
import Model.Customer;

public class Main {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.insert(new Customer("Socaci", "Radu", "radusocaci@gmail.com", "Venus 22"));
    }
}
