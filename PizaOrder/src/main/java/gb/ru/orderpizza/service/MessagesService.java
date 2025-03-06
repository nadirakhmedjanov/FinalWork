package gb.ru.orderpizza.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gb.ru.orderpizza.dao.MessageRepository;
import gb.ru.orderpizza.entity.Message;
import gb.ru.orderpizza.responsemodels.OperatorAnswer;
import gb.ru.orderpizza.exception.MessageNotFoundException;



/**
 * Сервис для управления сообщениями.
 * Осуществляет операции создания и обновления сообщений в хранилище данных.
 */
@Service
@Transactional
public class MessagesService {
    
    private final MessageRepository messageRepository;

    /**
     * Конструктор для создания экземпляра сервиса сообщений.
     * Осуществляет внедрение зависимости от репозитория сообщений.
     *
     * @param messageRepository репозиторий сообщений
     */
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Метод для создания нового сообщения в системе.
     *
     * @param messageRequest запрос на создание сообщения
     * @param userNumber     номер телефона пользователя, создавшего сообщение
     */
    public void postMessage(Message messageRequest, String userNumber) {
        Message message = new Message(
            messageRequest.getTitle(),
            messageRequest.getUserText());
        message.setUserNumber(userNumber);
        messageRepository.save(message);    
    }

    /**
     * Метод для обновления сообщения оператором (ответ Оператора).
     *
     * @param operatorAnswer запрос на обновление сообщения оператором
     * @param adminEmail     адрес электронной почты оператора
     * @throws MessageNotFoundException если сообщение не найдено
     */
    public void putMessage(
        OperatorAnswer operatorAnswer,
        String adminEmail) throws MessageNotFoundException {
        
        Message message = messageRepository.findById(operatorAnswer.getId())
            .orElseThrow(() -> new MessageNotFoundException("Сообщение не найдено"));

        message.setOperatorEmail(adminEmail);
        message.setOperatorText(operatorAnswer.getText());
        message.setClosed(true);
        messageRepository.save(message);
    }
}
