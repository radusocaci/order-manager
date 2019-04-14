package Model;

/**
 * Defines a stock as seen in the database
 *
 * @author Socaci Radu Andrei
 */
public class Stock {
    private int id;
    private int stock;

    /**
     * Used for reflection instantiation
     */
    public Stock() {
    }

    /**
     * Defines a new stock
     *
     * @param stock stock
     */
    public Stock(int stock) {
        this.stock = stock;
    }

    /**
     * Used for update in database
     *
     * @param id    stock id
     * @param stock stock
     */
    public Stock(int id, int stock) {
        this.id = id;
        this.stock = stock;
    }

    /**
     * @return stock id
     */
    public int getId() {
        return id;
    }

    /**
     * set stock id
     *
     * @param id stock id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * set stock
     *
     * @param stock stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}
