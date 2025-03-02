package gb.ru.orderpizza.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gb.ru.orderpizza.dao.FeedbackRepository;
import gb.ru.orderpizza.entity.Feedback;
import gb.ru.orderpizza.exception.FeedbackAlreadyExistsException;
import gb.ru.orderpizza.requestmodels.FeedbackRequestModel;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Сервис для работы с отзывами.
 */
@Service
@Transactional
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;

    /**
     * Конструктор класса FeedbackService.
     *
     * @param feedbackRepository репозиторий для работы с отзывами
     */
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Метод для добавления отзыва.
     *
     * @param userNumber номер телефона пользователя
     * @param feedbackRequest модель запроса отзыва
     * @throws FeedbackAlreadyExistsException если отзыв уже существует
     */
    public void postFeedback(
        String userNumber,
        FeedbackRequestModel feedbackRequest) throws FeedbackAlreadyExistsException {
        
        // Проверка существования отзыва пользователя для данного продукта
        Feedback existingFeedback = feedbackRepository.findByUserNumberAndProductId(userNumber, feedbackRequest.getProductId());

        if (existingFeedback != null) {
            throw new FeedbackAlreadyExistsException("Повторная попытка. Отзыв уже был оставлен.");
        }

        // Создание нового отзыва
        Feedback feedback = new Feedback();
        feedback.setProductId(feedbackRequest.getProductId());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setUserNumber(userNumber);

        // Обработка текстового отзыва
        feedbackRequest.getFeedbackDescription()
            .ifPresent(feedback::setFeedbackDescription);

        feedback.setDate(Date.valueOf(LocalDate.now()));
        
        // Сохранение отзыва в репозитории
        feedbackRepository.save(feedback);
    }

    /**
     * Метод для проверки наличия отзыва пользователя о товаре.
     *
     * @param userNumber номер телефона пользователя
     * @param productId идентификатор товара
     * @return true, если отзыв существует, иначе false
     */
    public Boolean userFeedbackListed(String userNumber, Long productId) {
        return feedbackRepository.findByUserNumberAndProductId(userNumber, productId) != null;
    }
}
