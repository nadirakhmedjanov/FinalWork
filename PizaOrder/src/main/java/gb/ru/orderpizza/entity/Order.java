package gb.ru.orderpizza.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * Класс, представляющий сущность "Заказ" в базе данных.
 */
@Entity
@Table(name = "pizza_order")
@Data
public class Order {

    /**
     * Пустой конструктор.
     */
    public Order() {
    }

    /**
     * Конструктор с параметрами.
     * @param orderId Номер заказа.
     * @param userNumber Номер телефона пользователя.
     * @param orderDate  Дата заказа.
     * @param productId  Идентификатор продукта.
     * @param orderQuantity Количество заказанного продукта.
     */
    public Order(
        Long orderId,          
        int userNumber,
        String orderDate,        
        Long productId,
        int orderQuantity) {
      this.orderId = orderId;
      this.userNumber = userNumber;
      this.orderDate = orderDate;     
      this.productId = productId;                
    }

    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    /**
     * Номер телефона пользователя, сделавшего заказ.
     */
    @Column(name = "user_number")
    private int userNumber;

    /**
     * Дата заказа.
     */
    @Column(name = "order_date")
    private String orderDate;

    /**
     * Идентификатор продукта, на который сделан заказ.
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * Количество заказанного продукта.
     */
    @Column(name = "order_quantity")
    private int orderQuantity;
  }
