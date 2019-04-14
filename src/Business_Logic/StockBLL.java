package Business_Logic;

import Business_Logic.validators.StockValidator;
import Business_Logic.validators.Validator;
import Data_Access.DAO.StockDAO;
import Model.Stock;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides interaction with the stock table of the database
 *
 * @author Socaci Radu Andrei
 */
public class StockBLL {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private StockDAO stockDAO;
    private Validator<Stock> validator;

    public StockBLL() {
        stockDAO = new StockDAO();
        validator = new StockValidator();
    }

    /**
     * Initializes the autoincrement ID
     *
     * @throws SQLException throws for bad init
     */
    public void init() throws SQLException {
        stockDAO.initId();
    }

    /**
     * Inserts a stock into the database
     *
     * @param stock stock
     * @return index of newly inserted stock
     * @throws SQLException throws for bad insertion
     */
    public int insert(Stock stock) throws SQLException {
        try {
            validator.validate(stock);
            stockDAO.insert(stock);

            stock.setId(idGenerator.getAndIncrement());
            return stock.getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates a stock into the database
     *
     * @param stock stock
     * @return index of newly inserted stock
     * @throws SQLException throws for bad update
     */
    public int update(Stock stock) throws SQLException {
        try {
            validator.validate(stock);
            return stockDAO.update(stock, stock.getId()).getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a stock from the database
     *
     * @param id id
     * @throws SQLException throws for bad delete
     */
    public void delete(int id) throws SQLException {
        stockDAO.delete(id);
    }

    /**
     * Deletes all stocks from the database
     *
     * @throws SQLException throws for bad delete
     */
    public void deleteAll() throws SQLException {
        stockDAO.deleteAll();
    }

    /**
     * Retrieves the stock with given id
     *
     * @param id id
     * @return stock
     * @throws NoSuchElementException thrown if stock to retrieve does not exist
     */
    public Stock get(int id) throws NoSuchElementException {
        Stock stock = stockDAO.findById(id);

        if (stock == null) {
            throw new NoSuchElementException("The stock with id =" + id + " was not found!");
        }

        return stock;
    }
}
