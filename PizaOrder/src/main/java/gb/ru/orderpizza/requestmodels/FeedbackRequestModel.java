package gb.ru.orderpizza.requestmodels;

import lombok.Data;

import java.util.Optional;

/**
 * Класс, представляющий запрос на добавление отзыва.
 */
@Data
public class FeedbackRequestModel {
    private double rating;
    private Long productId;
    private Optional<String> feedbackDescription;
}
