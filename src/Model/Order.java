package Model;

/**
 * Defines an order as seen in the database
 *
 * @author Socaci Radu Andrei
 */
public class Order {
    private int id;
    private int idProduct;
    private int idCustomer;
    private int quantity;

    /**
     * Used for reflection instantiation
     */
    public Order() {
    }

    /**
     * Defines an order
     *
     * @param idProduct  product id
     * @param idCustomer customer id
     * @param quantity   product quantity
     */
    public Order(int idProduct, int idCustomer, int quantity) {
        this.idProduct = idProduct;
        this.idCustomer = idCustomer;
        this.quantity = quantity;
    }

    /**
     * @return product id
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * set product id
     *
     * @param idProduct product id
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * @return customer id
     */
    public int getIdCustomer() {
        return idCustomer;
    }

    /**
     * set customer id
     *
     * @param idCustomer customer id
     */
    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    /**
     * @return order id
     */
    public int getId() {
        return id;
    }

    /**
     * set order id
     *
     * @param id order id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * set quantity
     *
     * @param quantity quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
