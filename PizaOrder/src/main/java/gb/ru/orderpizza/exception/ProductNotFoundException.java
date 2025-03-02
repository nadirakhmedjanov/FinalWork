package gb.ru.orderpizza.exception;

/**
 * Исключение, которое выбрасывается, когда продукт не найден.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
