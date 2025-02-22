package gb.ru.orderpizza.controller;

import org.springframework.web.bind.annotation.*;

import gb.ru.orderpizza.config.Environment;
import gb.ru.orderpizza.entity.Message;
import gb.ru.orderpizza.responsemodels.OperatorAnswer;
import gb.ru.orderpizza.service.MessagesService;
import gb.ru.orderpizza.utils.JWTParser;

/**
 * Контроллер для операций с сообщениями.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("/api/messages")
public class MessagesController {
    
    private MessagesService messagesService;

    /**
     * Конструктор контроллера.
     *
     * @param messagesService сервис для операций с сообщениями
     */
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    /**
     * POST-запрос для отправки сообщения.
     *
     * @param token          токен авторизации
     * @param messageRequest данные сообщения
     */
    @PostMapping("/secure/send/message")
    public void postMessage(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody Message messageRequest) {
            String userEmail = JWTParser.extractEmail(token);
            messagesService.postMessage(messageRequest, userEmail);
        }

    /**
     * PUT-запрос для ответа на сообщение от оператора.
     *
     * @param token  токен авторизации
     * @param answer ответ оператора на сообщение
     * @throws Exception если пользователь не является оператором
     */
    @PutMapping("/secure/operator/message")
    public void putMessage(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody OperatorAnswer answer) throws Exception {
            String email = JWTParser.extractEmail(token);
            String admin = JWTParser.extractRole(token);
            if (admin == null
                || !admin.equals("operator")) {
                throw new Exception("Страница только для админа");
            }
            messagesService.putMessage(answer, email);
        }    
}
