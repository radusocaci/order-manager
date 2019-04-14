package Business_Logic.validators;

import Model.Stock;

/**
 * Checks for stock integrity
 *
 * @author Socaci Radu Andrei
 */
public class StockValidator implements Validator<Stock> {
    /**
     * Validates stock of given stock object
     *
     * @param stock stock
     * @throws IllegalArgumentException throws in case stock is not valid
     */
    @Override
    public void validate(Stock stock) throws IllegalArgumentException {
        if (stock.getStock() < 0 || stock.getStock() > 1000) {
            throw new IllegalArgumentException("Stock has to be between 0 and 1000!");
        }
    }
}
