package gb.ru.orderpizza.requestmodels;

import lombok.Data;

/**
 * Класс, представляющий запрос на добавление продукта.
 */
@Data
public class AppendProductRequest {
    private  String name;
    private String description;
    private int quantityAvaillable;
    private int price;
    private String category;
    private String img;
}
