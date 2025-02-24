package gb.ru.orderpizza.controller;

import org.springframework.web.bind.annotation.*;

import gb.ru.orderpizza.config.Environment;
import gb.ru.orderpizza.requestmodels.AppendProductRequest;
import gb.ru.orderpizza.service.AdminService;
import gb.ru.orderpizza.utils.JWTParser;

/**
 * Контроллер для администраторских операций.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("api/admin")
public class AdminController {

    private AdminService adminService;

    /**
     * Конструктор контроллера.
     *
     * @param adminService сервис для администраторских операций
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * POST-запрос для добавления продукта администратором.
     *
     * @param token   токен авторизации
     * @param product данные о добавляемом продукте
     * @throws Exception если пользователь не является администратором
     */
    @PostMapping("/secure/append/product")
    public void postBook(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody AppendProductRequest product) throws Exception {
        String admin = JWTParser.extractRole(token);
        if (admin == null
            || !admin.equals("admin")) {
            throw new Exception("Вы не администратор");
        }
        adminService.postProduct(product);
    }

    /**
     * DELETE-запрос для удаления продукта администратором.
     *
     * @param token     токен авторизации
     * @param productId идентификатор удаляемого продукта
     * @throws Exception если пользователь не является администратором
     */
    @DeleteMapping("/secure/remove/product")
    public void deleteBook(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam Long productId) throws Exception {
        String admin = JWTParser.extractRole(token);
        if (admin == null
                || !admin.equals("admin")) {
            throw new Exception("Вы не администратор");
        }
        adminService.deleteProduct(productId);
    }

    /**
     * PUT-запрос для увеличения количества продукта администратором.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @throws Exception если пользователь не является администратором
     */
    @PutMapping("/secure/count/inc")
    public void incBookCount(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam Long productId) throws Exception {
        String admin = JWTParser.extractRole(token);
        if (admin == null
                || !admin.equals("admin")) {
            throw new Exception("Вы не администратор");
        }
        adminService.incProductCount(productId);
    }

    /**
     * PUT-запрос для уменьшения количества продукта администратором.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @throws Exception если пользователь не является администратором
     */
    @PutMapping("/secure/count/dec")
    public void decBookCount(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam Long productId) throws Exception {
        String admin = JWTParser.extractRole(token);
        if (admin == null
                || !admin.equals("admin")) {
            throw new Exception("Вы не администратор");
        }
        adminService.decProductCount(productId);
    }
}
