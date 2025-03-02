package gb.ru.orderpizza.entity;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * Класс, представляющий сущность "История" в базе данных.
 */
@Entity
@Table(name = "history")
@Data
public class History {

    /**
     * Конструктор с параметрами.
     *
     * @param userNumber     Номер телефона пользователя.
     * @param orderDate      Дата заказа.
     * @param name           Название пицы.
     * @param orderQuantity  Количество пицы.
     * @param description    Описание продукта.
     * @param img            Путь к изображению продукта.
     */
    public History(
            String userNumber, 
            LocalDateTime orderDate, 
            String name, 
            int orderQuantity,
            String description, 
            String img) {
        this.userNumber = userNumber;
        this.orderDate = orderDate;
        this.name = name;
        this.orderQuantity = orderQuantity;
        this.description = description;
        this.img = img;
    }

    /**
     * Конструктор без параметров.
     */
    public History() {
    }

    /**
     * Уникальный идентификатор записи истории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Номер телефона клиента.
     */
    @Column(name = "user_number")
    private String userNumber;

    /**
     * Дата заказа.
     */
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    /**
     * Название продукта.
     */
    @Column(name = "name")
    private String name;
    
    /**
     * Количество заказанного продукта.
     */
    @Column(name = "order_quantity")
    private int orderQuantity;
  
      /**
     * Описание продукта.
     */
    @Column(name = "description")
    private String description;

    /**
     * Путь к изображению продукта.
     */
    @Column(name = "img")
    private String img;
}