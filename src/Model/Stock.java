package Model;

public class Stock {
    private int id;
    private int stock;

    public Stock() {
    }

    public Stock(int stock) {
        this.stock = stock;
    }

    public Stock(int id, int stock) {
        this.id = id;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
