package gb.ru.orderpizza.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gb.ru.orderpizza.config.Environment;
import gb.ru.orderpizza.requestmodels.AppendProductRequest;
import gb.ru.orderpizza.service.AdminService;
import gb.ru.orderpizza.utils.JWTParser;
import gb.ru.orderpizza.exception.ForbiddenException;

@CrossOrigin(Environment.host)
@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Проверка роли администратора на основе JWT токена.
     *
     * @param token токен авторизации
     * @throws ForbiddenException если пользователь не администратор
     */
    private void checkAdminRole(String token) throws ForbiddenException {
        String admin = JWTParser.extractRole(token);
        if (admin == null || !admin.equals("admin")) {
            throw new ForbiddenException("Вы не администратор");
        }
    }

    /**
     * POST-запрос для добавления продукта администратором.
     *
     * @param token   токен авторизации
     * @param product данные о добавляемом продукте
     * @return ответ с успешным статусом
     * @throws ForbiddenException если пользователь не является администратором
     */
    @PostMapping("/secure/append/product")
public ResponseEntity<Void> postProduct(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody AppendProductRequest product) {
    try {
        checkAdminRole(token);
        adminService.postProduct(product);
        return ResponseEntity.ok().build();  // Возвращаем HTTP статус 200 OK
    } catch (ForbiddenException e) {
        return ResponseEntity.status(403).body(null);  // Возвращаем HTTP статус 403 Forbidden
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);  // Возвращаем HTTP статус 500 Internal Server Error
    }
}

    /**
     * DELETE-запрос для удаления продукта администратором.
     *
     * @param token     токен авторизации
     * @param productId идентификатор удаляемого продукта
     * @return ответ с успешным статусом
     * @throws ForbiddenException если пользователь не является администратором
     */
    @DeleteMapping("/secure/remove/product")
public ResponseEntity<Void> deleteProduct(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) {
    try {
        checkAdminRole(token);
        adminService.deleteProduct(productId);
        return ResponseEntity.ok().build();  // Возвращаем HTTP статус 200 OK
    } catch (ForbiddenException e) {
        return ResponseEntity.status(403).body(null);  // Возвращаем HTTP статус 403 Forbidden
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);  // Возвращаем HTTP статус 500 Internal Server Error
    }
}

    /**
     * PUT-запрос для увеличения количества продукта администратором.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return ответ с успешным статусом
     * @throws ForbiddenException если пользователь не является администратором
     */
    @PutMapping("/secure/count/inc")
public ResponseEntity<Void> incProductCount(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) {
    try {
        checkAdminRole(token);
        adminService.incProductCount(productId);
        return ResponseEntity.ok().build();  // Возвращаем HTTP статус 200 OK
    } catch (ForbiddenException e) {
        return ResponseEntity.status(403).body(null);  // Возвращаем HTTP статус 403 Forbidden
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);  // Возвращаем HTTP статус 500 Internal Server Error
    }
}

    /**
     * PUT-запрос для уменьшения количества продукта администратором.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return ответ с успешным статусом
     * @throws ForbiddenException если пользователь не является администратором
     */
    @PutMapping("/secure/count/dec")
public ResponseEntity<Void> decProductCount(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) {
    try {
        checkAdminRole(token);
        adminService.decProductCount(productId);
        return ResponseEntity.ok().build();  // Возвращаем HTTP статус 200 OK
    } catch (ForbiddenException e) {
        return ResponseEntity.status(403).body(null);  // Возвращаем HTTP статус 403 Forbidden
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);  // Возвращаем HTTP статус 500 Internal Server Error
    }
}
}
