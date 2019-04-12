package Business_Logic.validators;

import Model.Stock;

public class StockValidator implements Validator<Stock> {
    @Override
    public void validate(Stock stock) throws IllegalArgumentException {
        if (stock.getStock() < 0 || stock.getStock() > 1000) {
            throw new IllegalArgumentException("Stock has to be between 0 and 1000!");
        }
    }
}
