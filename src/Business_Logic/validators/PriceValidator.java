package Business_Logic.validators;

import Model.Product;

/**
 * Checks for price integrity
 *
 * @author Socaci Radu Andrei
 */
public class PriceValidator implements Validator<Product> {
    /**
     * Validates price of given product
     *
     * @param product product
     * @throws IllegalArgumentException thrown in case of bad price
     */
    @Override
    public void validate(Product product) throws IllegalArgumentException {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid price!");
        }
    }
}
