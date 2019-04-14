package Business_Logic;

import Business_Logic.validators.PriceValidator;
import Business_Logic.validators.Validator;
import Data_Access.DAO.OrderDAO;
import Data_Access.DAO.ProductDAO;
import Model.Order;
import Model.Product;
import Model.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Provides interaction with the product table of the database
 *
 * @author Socaci Radu Andrei
 */
public class ProductBLL {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private ProductDAO productDAO;
    private StockBLL stockBLL;
    private OrderDAO orderDAO;
    private Validator<Product> validator;

    public ProductBLL() {
        this.productDAO = new ProductDAO();
        this.stockBLL = new StockBLL();
        this.validator = new PriceValidator();
        this.orderDAO = new OrderDAO();
    }

    /**
     * Inserts a product into the database
     *
     * @param product product
     * @param stock   stock
     * @return index of newly inserted product
     * @throws SQLException             throws for bad insertion
     * @throws IllegalArgumentException thrown for bad price/stock
     */
    public int insert(Product product, int stock) throws SQLException, IllegalArgumentException {
        int stockId = 0;

        try {
            validator.validate(product);

            stockId = stockBLL.insert(new Stock(stock));
            product.setStockId(stockId);
            productDAO.insert(product);
            product.setId(idGenerator.getAndIncrement());

            return product.getId();
        } catch (SQLException ex) {
            try {
                get(product.getId());
            } catch (NoSuchElementException e) {
                stockBLL.delete(stockId);
            }

            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * Updates a product in the database
     *
     * @param product product
     * @return index of newly updated product
     * @throws NoSuchElementException   throws if product to update is not in the database
     * @throws SQLException             throws for bad update
     * @throws IllegalArgumentException thrown for bad price
     */
    public int update(Product product) throws NoSuchElementException, SQLException, IllegalArgumentException {
        get(product.getId());
        validator.validate(product);
        return productDAO.update(product, product.getId()).getId();
    }

    /**
     * Deletes a product from the database
     *
     * @param id product id to delete
     * @throws NoSuchElementException throws if product to delete is not in the database
     * @throws SQLException           throws for bad delete
     */
    public void delete(int id) throws NoSuchElementException, SQLException {
        List<Order> toDelete = orderDAO.findAll().stream().filter((order) -> (order.getIdProduct() == id)).collect(Collectors.toList());
        for (Order order : toDelete) {
            orderDAO.delete(order.getId());
        }

        stockBLL.delete(get(id).getStockId());

        productDAO.delete(id);
    }

    /**
     * Deletes all products from the database
     *
     * @throws SQLException throws for bad delete
     */
    public void deleteAll() throws SQLException {
        stockBLL.deleteAll();
        productDAO.deleteAll();

        productDAO.initId();
        stockBLL.init();
    }

    /**
     * Retrieves all products in the database
     *
     * @return product list
     */
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    /**
     * Retrieves product with given id from the database
     *
     * @param id id
     * @return product
     * @throws NoSuchElementException throws if product to retrieve is not in the database
     */
    public Product get(int id) throws NoSuchElementException {
        Product product = productDAO.findById(id);

        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }

        return product;
    }

    public StockBLL getStockBLL() {
        return stockBLL;
    }
}