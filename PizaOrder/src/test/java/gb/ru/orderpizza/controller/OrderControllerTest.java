package gb.ru.orderpizza.controller;

import static org.mockito.Mockito.*;


import gb.ru.orderpizza.service.OrderService;
import gb.ru.orderpizza.utils.JWTParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCurrentOrderCount() throws Exception {
        // Мокаем статический метод JWTParser.extractNumber
        try (MockedStatic<JWTParser> mocked = mockStatic(JWTParser.class)) {
            String token = "Bearer header.payload.signature";  // Пример токена
            String userNumber = "123";
            int orderCount = 5;

            // Мокаем поведение
            mocked.when(() -> JWTParser.extractNumber(token)).thenReturn(userNumber);

            // Мокаем OrderService
            when(orderService.currentOrderCount(userNumber)).thenReturn(orderCount);

            // Выполняем тестируемый запрос
            mockMvc.perform(get("/api/products/secure/currentorder/count")
                    .header("Authorization", token))
                    .andExpect(status().isOk())
                    .andExpect(content().string(String.valueOf(orderCount)));

            // Проверяем, что сервис был вызван
            verify(orderService, times(1)).currentOrderCount(userNumber);
        }
    }
}
