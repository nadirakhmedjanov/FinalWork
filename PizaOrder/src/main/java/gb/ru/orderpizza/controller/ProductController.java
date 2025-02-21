package gb.ru.orderpizza.controller;

import org.springframework.web.bind.annotation.*;

import gb.ru.orderpizza.config.Environment;
import gb.ru.orderpizza.entity.Product;
import gb.ru.orderpizza.service.ProductService;
import gb.ru.orderpizza.utils.JWTParser;

/**
 * Контроллер для операций с продуктами.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    /**
     * Конструктор контроллера.
     *
     * @param productService сервис для операций с продуктами
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * PUT-запрос для заказа продукта.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return заказанный продукт
     * @throws Exception если заказ не удался
     */
    @PutMapping("/secure/order")
    public Product orderProduct(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) throws Exception {
            String userNumber = JWTParser.extractEmail(token);
            return productService.orderProduct(userNumber, productId, 0);
        }

    /**
     * GET-запрос для проверки, заказан ли продукт пользователем.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return true, если продукт заказан пользователем, в противном случае false
     */
    @GetMapping("/secure/isorder/byuser")
    public Boolean orderProductByUser(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) {
            String userNumber = JWTParser.extractEmail(token);
        return productService.orderProductByUser(userNumber, productId);
    }

    /**
     * GET-запрос для получения текущего количества заказов пользователя.
     *
     * @param token токен авторизации
     * @return текущее количество заказов пользователя
     */
    @GetMapping("/secure/currentorder/count")
    public int currentOrderCount(
        @RequestHeader(value = "Authorization") String token) {
            String userEmail = JWTParser.extractEmail(token);
        return productService.currentOrderCount(userEmail);
    }

    

   

    
}