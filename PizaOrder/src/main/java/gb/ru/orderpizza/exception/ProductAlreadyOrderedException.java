package gb.ru.orderpizza.exception;

public class ProductAlreadyOrderedException extends Exception {
    public ProductAlreadyOrderedException(String message) {
        super(message);
    }
}
