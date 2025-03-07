package gb.ru.orderpizza.dao;

import gb.ru.orderpizza.entity.Order;
import gb.ru.orderpizza.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)

public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        // Создаем и сохраняем продукт для тестов
        product = new Product();
        product.setName("Pizza Margherita");
        product.setQuantityAvailable(10);
        product = productRepository.save(product);
    }

    @Test
    void testFindOrderByUserNumberAndProductId() {
        // Создаем заказ для теста
        Order order = new Order();
        order.setUserNumber("1234567890");
        order.setProductId(product.getId());
        order.setOrderQuantity(2);
        orderRepository.save(order);

        // Проверяем, что заказ найден по номеру телефона и продукту
        Order foundOrder = orderRepository.findOrderByUserNumberAndProductId("1234567890", product.getId());
        assertNotNull(foundOrder);
        assertEquals("1234567890", foundOrder.getUserNumber());
        assertEquals(product.getId(), foundOrder.getProductId());
    }

    @Test
    void testFindOrdersByUserNumber() {
        // Создаем заказы для теста
        Order order1 = new Order();
        order1.setUserNumber("1234567890");
        order1.setProductId(product.getId());
        order1.setOrderQuantity(2);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setUserNumber("1234567890");
        order2.setProductId(product.getId());
        order2.setOrderQuantity(1);
        orderRepository.save(order2);

        // Проверяем, что все заказы для указанного пользователя найдены
        List<Order> orders = orderRepository.findOrdersByUserNumber("1234567890");
        assertEquals(2, orders.size());
    }

    @Test
    void testDeleteAllByProductId() {
        // Создаем и сохраняем заказ для теста
        Order order1 = new Order();
        order1.setUserNumber("1234567890");
        order1.setProductId(product.getId());
        order1.setOrderQuantity(2);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setUserNumber("0987654321");
        order2.setProductId(product.getId());
        order2.setOrderQuantity(3);
        orderRepository.save(order2);

        // Проверяем, что заказы добавлены
        List<Order> ordersBeforeDeletion = orderRepository.findOrdersByUserNumber("1234567890");
        assertEquals(1, ordersBeforeDeletion.size());

        // Удаляем заказы по productId
        orderRepository.deleteAllByProductId(product.getId());

        // Проверяем, что заказы для данного продукта удалены
        List<Order> ordersAfterDeletion = orderRepository.findOrdersByUserNumber("1234567890");
        assertEquals(0, ordersAfterDeletion.size());
    }
}
