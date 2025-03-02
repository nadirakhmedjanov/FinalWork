package gb.ru.orderpizza.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import gb.ru.orderpizza.entity.Feedback;

/**
 * Интерфейс репозитория для работы с отзывами.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

  /**
   * Метод для поиска отзывов по идентификатору продукта.
   *
   * @param productId идентификатор продукта
   * @param pageable  объект для пагинации результатов
   * @return страница отзывов, относящихся к продукту
   */
  Page<Feedback> findByProductId(Long productId, Pageable pageable);

  /**
   * Метод для поиска отзыва пользователя по идентификатору продукта.
   *
   * @param userNumber номер телефона клиента 
   * @param productId идентификатор продукта
   * @return отзыв пользователя о продукте
   */
  Feedback findByUserNumberAndProductId(String userNumber, Long productId);

  /**
   * Метод для удаления всех отзывов по идентификатору продукта.
   *
   * @param productId идентификатор продукта
   */
  @Modifying
  @Query("delete from Feedback where product_id in :product_id")
  void deleteAllByProductId(@Param("product_id") Long productId);
}
