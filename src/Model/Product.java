package Model;

/**
 * Defines a product as seen in the database
 *
 * @author Socaci Radu Andrei
 */
public class Product {
    private int id;
    private String name;
    private int price;
    private int stockId;

    /**
     * Used for reflection instantiation
     */
    public Product() {
    }

    /**
     * Used for database update
     * @param id product id
     * @param name product name
     * @param price product price
     */
    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Defines a new product
     * @param name product name
     * @param price product price
     */
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * @return product id
     */
    public int getId() {
        return id;
    }

    /**
     * set product id
     * @param id product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return stock id
     */
    public int getStockId() {
        return stockId;
    }

    /**
     * set stock id
     * @param stockId stock id
     */
    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    /**
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * set product name
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return product price
     */
    public int getPrice() {
        return price;
    }

    /**
     * set product price
     * @param price product price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + "\t\t";
    }
}
