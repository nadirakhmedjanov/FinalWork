package gb.ru.orderpizza.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gb.ru.orderpizza.dao.FeedbackRepository;
import gb.ru.orderpizza.entity.Feedback;
import gb.ru.orderpizza.requestmodels.FeedbackRequestModel;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Сервис для работы с отзывами.
 */
@Service
@Transactional
public class FeedbackService {
    
    private FeedbackRepository feedbackRepository;

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
     * @throws Exception если отзыв уже существует
     */
    public void postFeedback(
        String userNumber,
        FeedbackRequestModel feedbackRequest) throws Exception {
            Feedback validateFeedback = feedbackRepository.findByUserNumberAndProductId(
                userNumber, feedbackRequest.getProductId());

        if (validateFeedback != null) {
            throw new Exception("Повторная попытка");
        }                

        Feedback feedback = new Feedback();
        feedback.setProductId(feedbackRequest.getProductId());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setUserNumber(userNumber);
        if (feedbackRequest.getFeedbackDescription().isPresent()) {
            feedback.setFeedbackDescription(
                feedbackRequest
                .getFeedbackDescription()
                .map(t -> t.toString())
                .orElse(null));
        }
        feedback.setDate(Date.valueOf(LocalDate.now()));
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
        return feedbackRepository
        .findByUserNumberAndProductId(userNumber, productId) != null;
    }        
}