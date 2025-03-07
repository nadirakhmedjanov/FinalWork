package gb.ru.orderpizza.service;

import gb.ru.orderpizza.dao.OrderRepository;
import gb.ru.orderpizza.dao.ProductRepository;
import gb.ru.orderpizza.dao.FeedbackRepository;
import gb.ru.orderpizza.entity.Product;
import gb.ru.orderpizza.requestmodels.AppendProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private AdminService adminService;

    private Product product;

    @BeforeEach
    void setUp() {
        // Создаем тестовый продукт
        product = new Product();
        product.setId(1L);
        product.setName("Pizza Margherita");
        product.setDescription("Classic pizza");
        product.setPrice(BigDecimal.valueOf(12.0));
        product.setQuantityAvailable(10);
        product.setCategory("Italian");
        product.setImg("image_url");
    }

    @Test
    void testPostProduct() {
        // Создаем данные для добавления нового продукта
        AppendProductRequest request = new AppendProductRequest();
        request.setName("Pizza Pepperoni");
        request.setDescription("Delicious pepperoni pizza");
        request.setPrice(BigDecimal.valueOf(12.0));
        request.setQuantityAvaillable(20);
        request.setCategory("Italian");
        request.setImg("image_url");

        // Выполняем метод добавления
        adminService.postProduct(request);

        // Проверяем, что метод save был вызван один раз
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        // Мокаем отсутствие продукта в базе
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверяем, что будет выброшено исключение
        Exception exception = assertThrows(Exception.class, () -> {
            adminService.deleteProduct(1L);
        });

        assertEquals("Товар отсутствует", exception.getMessage());
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Мокаем существование продукта
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Выполняем метод удаления
        adminService.deleteProduct(1L);

        // Проверяем, что были вызваны методы удаления
        verify(productRepository, times(1)).delete(product);
        verify(orderRepository, times(1)).deleteAllByProductId(1L);
        verify(feedbackRepository, times(1)).deleteAllByProductId(1L);
    }

    @Test
    void testIncProductCount_ProductNotFound() {
        // Мокаем отсутствие продукта в базе
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверяем, что будет выброшено исключение
        Exception exception = assertThrows(Exception.class, () -> {
            adminService.incProductCount(1L);
        });

        assertEquals("Товар отсутствует", exception.getMessage());
    }

    @Test
    void testIncProductCount() throws Exception {
        // Мокаем существование продукта
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Выполняем метод увеличения количества
        adminService.incProductCount(1L);

        // Проверяем, что количество продукта увеличилось на 1
        assertEquals(11, product.getQuantityAvailable());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDecProductCount_ProductNotFound() {
        // Мокаем отсутствие продукта в базе
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверяем, что будет выброшено исключение
        Exception exception = assertThrows(Exception.class, () -> {
            adminService.decProductCount(1L);
        });

        assertEquals("Товар отсутствует", exception.getMessage());
    }

    @Test
    void testDecProductCount_ProductOutOfStock() throws Exception {
        // Мокаем продукт с количеством 0
        product.setQuantityAvailable(0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Выполняем метод уменьшения количества
        adminService.decProductCount(1L);

        // Проверяем, что количество не изменилось
        assertEquals(0, product.getQuantityAvailable());
        verify(productRepository, times(0)).save(product); // Метод save не должен быть вызван
    }

    @Test
    void testDecProductCount() throws Exception {
        // Мокаем существование продукта
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Выполняем метод уменьшения количества
        adminService.decProductCount(1L);

        // Проверяем, что количество продукта уменьшилось на 1
        assertEquals(9, product.getQuantityAvailable());
        verify(productRepository, times(1)).save(product);
    }
}
