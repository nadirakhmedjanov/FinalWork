package gb.ru.orderpizza.responsemodels;

import lombok.Data;

/**
 * Класс, представляющий ответ администратора.
 */
@Data
public class OperatorAnswer {
    private Long id;
    private String text;
}
