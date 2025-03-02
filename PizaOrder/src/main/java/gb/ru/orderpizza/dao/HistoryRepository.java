package gb.ru.orderpizza.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import gb.ru.orderpizza.entity.History;

/**
 * Интерфейс репозитория для работы с историей заказов.
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * Метод для поиска истории заказов по номеру телефона пользователя.
     *
     * @param userNumber номер телефона пользователя
     * @param pageable параметры страницы
     * @return страница истории заказов
     */
    Page<History> findProductByUserNumber(
        String userNumber,
        Pageable pageable);
}
