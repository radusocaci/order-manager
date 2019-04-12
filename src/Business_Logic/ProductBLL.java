package Business_Logic;

import Business_Logic.validators.PriceValidator;
import Business_Logic.validators.Validator;
import Data_Access.DAO.ProductDAO;
import Model.Product;
import Model.Stock;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductBLL {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private ProductDAO productDAO;
    private StockBLL stockBLL;
    private Validator<Product> validator;

    public ProductBLL() {
        this.productDAO = new ProductDAO();
        this.stockBLL = new StockBLL();
        this.validator = new PriceValidator();
        productDAO.initId();
        stockBLL.init();
    }

    public int insert(Product product, int stock) {
        try {
            validator.validate(product);

            int stockId = stockBLL.insert(new Stock(stock));
            product.setStockId(stockId);
            productDAO.insert(product);

            product.setId(idGenerator.getAndIncrement());

            try {
                get(product.getId());
            } catch (Exception e) {
                stockBLL.delete(stockId);
            }

            return product.getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(Product product) throws NoSuchElementException {
        try {
            get(product.getId());
            validator.validate(product);
            return productDAO.update(product, product.getId()).getId();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void delete(int id) {
        stockBLL.delete(get(id).getStockId());
        productDAO.delete(id);
    }

    public void deleteAll() {
        stockBLL.deleteAll();
        productDAO.deleteAll();
    }

    public List<Product> getAll() {
        return productDAO.findAll();
    }

    public Product get(int id) {
        Product product = productDAO.findById(id);

        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }

        return product;
    }
}