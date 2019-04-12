package Business_Logic.validators;

public interface Validator<T> {
    void validate(T t) throws IllegalArgumentException;
}
