package Model;

public class Order {
    private int id;
    private int idProduct;
    private int idCustomer;
    private int quantity;

    public Order() {
    }

    public Order(int idProduct, int idCustomer, int quantity) {
        this.idProduct = idProduct;
        this.idCustomer = idCustomer;
        this.quantity = quantity;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
