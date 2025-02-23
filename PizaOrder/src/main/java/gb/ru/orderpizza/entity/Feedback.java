package gb.ru.orderpizza.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Date;

/**
 * Класс, представляющий сущность "Фидбек" в базе данных, т.е. мнения, предложения, комментарии, отзывы.
 */
@Entity
@Table(name = "feedback")
@Data
public class Feedback {

  /**
   * Уникальный идентификатор фидбека.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * Номер телефона пользователя, оставившего отзыв.
   */
  @Column(name = "user_number")
  private String userNumber;

  /**
   * Дата создания отзыва.
   */
  @Column(name = "date")
  @CreationTimestamp
  private Date date;

  /**
   * Рейтинг, присвоенный продукту в отзыве.
   */
  @Column(name = "rating")
  private double rating;

  /**
   * Идентификатор продукта, к которому относится отзыв.
   */
  @Column(name = "product_id")
  private Long productId;

  /**
   * Текст отзыва.
   */
  @Column(name = "review_text")
  private String feedbackDescription;

}
