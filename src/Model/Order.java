package Model;

public class Order {
    private int idProduct;
    private int idCustomer;

    // maybe add client and product ref + tostring method for bill

    public Order(int idProduct, int idCustomer) {
        this.idProduct = idProduct;
        this.idCustomer = idCustomer;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
}
