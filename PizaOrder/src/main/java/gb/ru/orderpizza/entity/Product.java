package gb.ru.orderpizza.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Открываем Класс, представляющий сущность "Продукт" (для разновидностей Пица) 
 */
@Entity
@Table(name = "product")
@Data
public class Product {

  /**
   * Уникальный идентификатор продукта
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * Название продукта (Пицы).
   */
  @Column(name = "name")
  private String name;

 /**
   * Цена Пицы в долларах (Пицы).
   */
  @Column(name = "price")

  private int pirice;
  /**
   * Описание пицы.
   */
  @Column(name = "description")
  private String description;

  
  /**
   * Количество готовых к продаже пиц.
   */
  @Column(name = "quantity_available")
  private int quantityAvailable;

  /**
   * Категория Пицы.
   */
  @Column(name = "category")
  private String category;

  /**
   * Путь к изображению Пицы.
   */
  @Column(name = "img")
  private String img;
}