package Business_Logic;

import Business_Logic.validators.StockValidator;
import Business_Logic.validators.Validator;
import Data_Access.DAO.StockDAO;
import Model.Stock;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class StockBLL {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private StockDAO stockDAO;
    private Validator<Stock> validator;

    public StockBLL() {
        stockDAO = new StockDAO();
        validator = new StockValidator();
    }

    public void init() {
        stockDAO.initId();
    }

    public int insert(Stock stock) {
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

    public int update(Stock stock) {
        try {
            validator.validate(stock);
            return stockDAO.update(stock, stock.getId()).getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void delete(int id) {
        stockDAO.delete(id);
    }

    public void deleteAll() {
        stockDAO.deleteAll();
    }

    public Stock get(int id) throws NoSuchElementException {
        Stock stock = stockDAO.findById(id);

        if (stock == null) {
            throw new NoSuchElementException("The stock with id =" + id + " was not found!");
        }

        return stock;
    }
}
