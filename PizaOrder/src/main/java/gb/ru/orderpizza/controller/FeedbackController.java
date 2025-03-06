package gb.ru.orderpizza.controller;

import org.springframework.web.bind.annotation.*;

import gb.ru.orderpizza.config.Environment;
import gb.ru.orderpizza.requestmodels.FeedbackRequestModel;
import gb.ru.orderpizza.service.FeedbackService;
import gb.ru.orderpizza.utils.JWTParser;

/**
 * Контроллер для операций с отзывами.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("/api/reviews")
public class FeedbackController {

    private FeedbackService feedbackService;

    /**
     * Конструктор контроллера.
     *
     * @param feedbackService сервис для операций с отзывами
     */
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * GET-запрос для проверки, оставлял ли пользователь отзыв о продукте.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
          * @param userNumber 
          * @return true, если пользователь оставлял отзыв о продукте, в противном случае false
          * @throws Exception если не удалось проверить отзыв
          */
         @GetMapping("/secure/user/product")
         public Boolean reviewProductByUser(
             @RequestHeader(value = "Authorization") String token,
             @RequestParam Long productId, String userNumber) throws Exception {
                 String userMail = JWTParser.extractNumber(token);
                 if (userMail == null) {
                     throw new Exception("Ошибка пользователя");
                 }
                 return feedbackService.userFeedbackListed(userNumber, productId);
    }

    /**
     * POST-запрос для добавления отзыва.
     *
     * @param token         токен авторизации
     * @param reviewRequest модель запроса отзыва
     * @throws Exception если не удалось добавить отзыв
     */
    @PostMapping("/secure")
    public void postReview(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody FeedbackRequestModel reviewRequest) throws Exception {
            String userNumber = JWTParser.extractNumber(token);
            if (userNumber == null) {
                throw new Exception("Ошибка пользователя");
            }
            feedbackService.postFeedback(userNumber, reviewRequest);
    }    
}
