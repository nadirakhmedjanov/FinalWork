package gb.ru.orderpizza.requestmodels;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Класс, представляющий запрос на добавление продукта.
 */
@Data
public class AppendProductRequest {
    private  String name;
    private String description;
    private int quantityAvaillable;
    private BigDecimal price;
    private String category;
    private String img;
}
