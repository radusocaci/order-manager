package Business_Logic.validators;

import Model.Product;

public class PriceValidator implements Validator<Product> {
    @Override
    public void validate(Product product) throws IllegalArgumentException {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid price!");
        }
    }
}
