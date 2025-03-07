package gb.ru.orderpizza.controller;

import gb.ru.orderpizza.requestmodels.AppendProductRequest;
import gb.ru.orderpizza.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testPostProduct_Success() throws Exception {
        AppendProductRequest product = new AppendProductRequest();
        product.setName("Pizza Margherita");
        product.setDescription("Classic pizza");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setQuantityAvaillable(20);
        product.setCategory("Italian");
        product.setImg("image_url");

        String token = "valid_token_with_admin_role";

        // Мокаем успешное выполнение сервиса
        doNothing().when(adminService).postProduct(any());

        mockMvc.perform(post("/api/admin/secure/append/product")
                .header("Authorization", token)
                .contentType("application/json")
                .content("{\"name\":\"Pizza Margherita\",\"description\":\"Classic pizza\",\"price\":\"10.0\",\"quantityAvaillable\":20,\"category\":\"Italian\",\"img\":\"image_url\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Используем MockMvcResultMatchers

        // Проверяем, что метод постинга был вызван один раз
        verify(adminService, times(1)).postProduct(any());
    }

    @Test
    void testPostProduct_Forbidden() throws Exception {
        AppendProductRequest product = new AppendProductRequest();
        product.setName("Pizza Margherita");
        product.setDescription("Classic pizza");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setQuantityAvaillable(20);
        product.setCategory("Italian");
        product.setImg("image_url");

        String token = "invalid_token";  // Некорректный токен

        mockMvc.perform(post("/api/admin/secure/append/product")
                .header("Authorization", token)
                .contentType("application/json")
                .content("{\"name\":\"Pizza Margherita\",\"description\":\"Classic pizza\",\"price\":\"10.0\",\"quantityAvaillable\":20,\"category\":\"Italian\",\"img\":\"image_url\"}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // Проверяем статус 403 Forbidden

        // Метод postProduct не должен быть вызван
        verify(adminService, times(0)).postProduct(any());
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        String token = "valid_token_with_admin_role";
        Long productId = 1L;

        // Мокаем успешное выполнение сервиса
        doNothing().when(adminService).deleteProduct(productId);

        mockMvc.perform(delete("/api/admin/secure/remove/product")
                .header("Authorization", token)
                .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Проверяем статус 200 OK

        // Проверяем, что метод удаления был вызван один раз
        verify(adminService, times(1)).deleteProduct(productId);
    }

    @Test
    void testDeleteProduct_Forbidden() throws Exception {
        String token = "invalid_token";  // Некорректный токен
        Long productId = 1L;

        mockMvc.perform(delete("/api/admin/secure/remove/product")
                .header("Authorization", token)
                .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // Проверяем статус 403 Forbidden

        // Метод deleteProduct не должен быть вызван
        verify(adminService, times(0)).deleteProduct(productId);
    }

    @Test
    void testIncProductCount_Success() throws Exception {
        String token = "valid_token_with_admin_role";
        Long productId = 1L;

        // Мокаем успешное выполнение сервиса
        doNothing().when(adminService).incProductCount(productId);

        mockMvc.perform(put("/api/admin/secure/count/inc")
                .header("Authorization", token)
                .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Проверяем статус 200 OK

        // Проверяем, что метод увеличения количества был вызван один раз
        verify(adminService, times(1)).incProductCount(productId);
    }

    @Test
    void testIncProductCount_Forbidden() throws Exception {
        String token = "invalid_token";  // Некорректный токен
        Long productId = 1L;

        mockMvc.perform(put("/api/admin/secure/count/inc")
                .header("Authorization", token)
                .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // Проверяем статус 403 Forbidden

        // Метод incProductCount не должен быть вызван
        verify(adminService, times(0)).incProductCount(productId);
    }

    @Test
    void testDecProductCount_Success() throws Exception {
        String token = "valid_token_with_admin_role";
        Long productId = 1L;

        // Мокаем успешное выполнение сервиса
        doNothing().when(adminService).decProductCount(productId);

        mockMvc.perform(put("/api/admin/secure/count/dec")
                .header("Authorization", token)
                .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Проверяем статус 200 OK

        // Проверяем, что метод уменьшения количества был вызван один раз
        verify(adminService, times(1)).decProductCount(productId);
    }

    @Test
    void testDecProductCount_Forbidden() throws Exception {
        String token = "invalid_token";  // Некорректный токен
        Long productId = 1L;

        mockMvc.perform(put("/api/admin/secure/count/dec")
                .header("Authorization", token)
                .param("productId", String.valueOf(productId)))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // Проверяем статус 403 Forbidden

        // Метод decProductCount не должен быть вызван
        verify(adminService, times(0)).decProductCount(productId);
    }
}
