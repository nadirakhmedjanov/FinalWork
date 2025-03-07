package gb.ru.orderpizza.dao;

import gb.ru.orderpizza.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        // Создание продуктов для теста
        product1 = new Product();
        product1.setName("Pizza Margherita");
        product1.setCategory("Italian");
        productRepository.save(product1);

        product2 = new Product();
        product2.setName("Pizza Pepperoni");
        product2.setCategory("Italian");
        productRepository.save(product2);

        product3 = new Product();
        product3.setName("Vegetarian Pizza");
        product3.setCategory("Vegetarian");
        productRepository.save(product3);
    }

    @Test
    void testFindByNameContaining() {
        // Пагинация с размером страницы 2
        Page<Product> page = productRepository.findByNameContaining("Pizza", PageRequest.of(0, 2));

        // Проверка, что на странице 2 продукта
        assertEquals(2, page.getSize());
        assertTrue(page.getContent().stream().anyMatch(p -> p.getName().contains("Pizza")));
    }

    @Test
    void testFindByNameContainingNoResults() {
        // Поиск по части названия, которая не существует
        Page<Product> page = productRepository.findByNameContaining("NonExistent", PageRequest.of(0, 2));

        // Проверка, что не было найдено продуктов
        assertEquals(0, page.getTotalElements());
    }
    @Test
    void testFindByCategoryContaining() {
        // Пагинация с размером страницы 2
        Page<Product> page = productRepository.findByCategoryContaining("Italian", PageRequest.of(0, 2));

        // Проверка, что на странице 2 продукта
        assertEquals(2, page.getSize());
        assertTrue(page.getContent().stream().anyMatch(p -> p.getCategory().contains("Italian")));
    }

    @Test
    void testFindByCategoryContainingNoResults() {
        // Поиск по категории, которая не существует
        Page<Product> page = productRepository.findByCategoryContaining("NonExistent", PageRequest.of(0, 2));

        // Проверка, что не было найдено продуктов
        assertEquals(0, page.getTotalElements());
    }

    @Test
    void testFindProductByProductIds() {
        // Создание списка идентификаторов продуктов
        List<Long> productIds = List.of(product1.getId(), product2.getId());

        // Поиск продуктов по идентификаторам
        List<Product> products = productRepository.findProductByProductIds(productIds);

        // Проверка, что найдено два продукта
        assertEquals(2, products.size());
        assertTrue(products.stream().anyMatch(p -> p.getId().equals(product1.getId())));
        assertTrue(products.stream().anyMatch(p -> p.getId().equals(product2.getId())));
    }

    @Test
    void testFindProductByProductIdsNoResults() {
        // Создание списка идентификаторов продуктов, которых нет в базе
        List<Long> productIds = List.of(999L, 1000L);

        // Поиск продуктов по несуществующим идентификаторам
        List<Product> products = productRepository.findProductByProductIds(productIds);

        // Проверка, что не было найдено продуктов
        assertTrue(products.isEmpty());
    }
}


