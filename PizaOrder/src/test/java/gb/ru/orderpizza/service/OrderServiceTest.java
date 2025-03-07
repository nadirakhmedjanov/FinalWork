package gb.ru.orderpizza.service;

import gb.ru.orderpizza.entity.Product;
import gb.ru.orderpizza.entity.Order;
import gb.ru.orderpizza.exception.InsufficientStockException;
import gb.ru.orderpizza.exception.ProductAlreadyOrderedException;
import gb.ru.orderpizza.exception.ProductNotFoundException;
import gb.ru.orderpizza.dao.OrderRepository;
import gb.ru.orderpizza.dao.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    
    @InjectMocks
    private OrderService orderService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализируем тестовый продукт
        product = new Product();
        product.setId(1L);
        product.setName("Pizza");
        product.setQuantityAvailable(10);
    }

    @Test
    void orderProduct_Success() throws Exception {
        String userNumber = "12345";
        Long productId = 1L;
        int orderQuantity = 2;

        // Мокаем поведение репозиториев
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.findOrderByUserNumberAndProductId(userNumber, productId)).thenReturn(null); // Заказ не сделан
        when(orderRepository.save(any(Order.class))).thenReturn(new Order()); // Сохраняем заказ

        // Вызов метода
        Product orderedProduct = orderService.orderProduct(userNumber, productId, orderQuantity);

        // Проверка, что заказ был сделан и количество товара уменьшилось
        assertEquals(8, orderedProduct.getQuantityAvailable());
        verify(orderRepository, times(1)).save(any(Order.class)); // Проверка, что заказ был сохранён
    }

    @Test
    void orderProduct_ProductNotFound() {
        String userNumber = "12345";
        Long productId = 1L;
        int orderQuantity = 2;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Ожидаем, что будет выброшено исключение ProductNotFoundException
        assertThrows(ProductNotFoundException.class, () -> {
            orderService.orderProduct(userNumber, productId, orderQuantity);
        });
    }

    @Test
    void orderProduct_AlreadyOrdered() {
        String userNumber = "12345";
        Long productId = 1L;
        int orderQuantity = 2;

        // Мокаем, что заказ уже был сделан
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.findOrderByUserNumberAndProductId(userNumber, productId)).thenReturn(new Order()); // Заказ уже существует

        // Ожидаем, что будет выброшено исключение ProductAlreadyOrderedException
        assertThrows(ProductAlreadyOrderedException.class, () -> {
            orderService.orderProduct(userNumber, productId, orderQuantity);
        });
    }

    @Test
    void orderProduct_InsufficientStock() {
        String userNumber = "12345";
        Long productId = 1L;
        int orderQuantity = 20; // Больше, чем доступно на складе

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Ожидаем, что будет выброшено исключение InsufficientStockException
        assertThrows(InsufficientStockException.class, () -> {
            orderService.orderProduct(userNumber, productId, orderQuantity);
        });
    }

    @Test
    void orderProductByUser() {
        String userNumber = "12345";
        Long productId = 1L;

        // Мокаем, что заказ уже сделан
        when(orderRepository.findOrderByUserNumberAndProductId(userNumber, productId)).thenReturn(new Order());

        // Проверка, что метод вернёт true
        assertTrue(orderService.orderProductByUser(userNumber, productId));
    }

    @Test
    void currentOrderCount() {
        String userNumber = "12345";

        // Мокаем, что у пользователя есть 3 заказа
        when(orderRepository.findOrdersByUserNumber(userNumber)).thenReturn(List.of(new Order(), new Order(), new Order()));

        // Проверка, что метод вернёт количество заказов
        assertEquals(3, orderService.currentOrderCount(userNumber));
    }
}
