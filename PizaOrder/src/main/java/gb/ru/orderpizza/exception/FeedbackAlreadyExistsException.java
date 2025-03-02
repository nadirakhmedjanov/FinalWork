package gb.ru.orderpizza.exception;

/**
 * Исключение, выбрасываемое, если отзыв пользователя уже существует.
 */
public class FeedbackAlreadyExistsException extends RuntimeException {

    public FeedbackAlreadyExistsException(String message) {
        super(message);
    }
}

