package gb.ru.orderpizza.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gb.ru.orderpizza.entity.Order;

import java.util.List;

/**
 * Интерфейс репозитория для работы с заказами.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Метод для поиска заказа по номеру телефона клиента и идентификатору продукта. При этом будет возвращён первый соотвтствующий заказ, 
     * даже если критериям поиска соответствуют более одного заказа
     *
     * @param userNumber номер телефона клиента
     * @param productId идентификатор продукта
     * @return заказ order
     */
    Order findOrderByUserNumberAndProductId(String userNumber, Long productId);

    /**
     * Метод для поиска всех заказов клиента по его номеру телефона.
     *
     * @param userNumber номер телефона пользователя
     * @return список заказов
     */
    List<Order> findOrdersByUserNumber(String userNumber);

    /**
     * Метод для удаления всех заказов по идентификатору продукта.
     *
     * @param productId идентификатор продукта
     */
    @Modifying
    @Query("delete from Order where product_id in :product_id")
    void deleteAllByProductId(@Param("productId") Long productId);

}