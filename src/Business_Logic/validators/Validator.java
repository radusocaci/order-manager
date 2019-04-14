package Business_Logic.validators;

/**
 * Validates T objects
 *
 * @param <T> object passed for validation
 */
public interface Validator<T> {
    /**
     * @param t object to validate
     * @throws IllegalArgumentException thrown if validation fails
     */
    void validate(T t) throws IllegalArgumentException;
}
