package gb.ru.orderpizza.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import gb.ru.orderpizza.entity.Message;

/**
 * Интерфейс репозитория для работы с сообщениями.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Метод для поиска сообщений по номеру телефона.
     *
     * @param userNumber адрес электронной почты пользователя
     * @param pageable  параметры страницы
     * @return страница сообщений
     */
    Page<Message> findByUserNumber(
        String userNumber,
        Pageable pageable);

    /**
     * Метод для поиска сообщений по признаку закрытости.
     *
     * @param closed   признак закрытости сообщений
     * @param pageable параметры страницы
     * @return страница сообщений
     */
    Page<Message> findByClosed(
        boolean closed,
        Pageable pageable);    
}
