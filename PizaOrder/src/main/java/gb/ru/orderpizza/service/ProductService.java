package gb.ru.orderpizza.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gb.ru.orderpizza.dao.*;
import gb.ru.orderpizza.entity.*;
import gb.ru.orderpizza.exception.InsufficientStockException;
import gb.ru.orderpizza.exception.ProductAlreadyOrderedException;
import gb.ru.orderpizza.exception.ProductNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Сервис для управления товарами.
 * Осуществляет операции заказа.
 */
@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    /**
     * Конструктор для создания экземпляра сервиса товаров.
     * Осуществляет внедрение зависимостей от репозиториев товаров, заказов и
     * истории.
     *
     * @param productRepository репозиторий товаров
     * @param orderRepository   репозиторий заказов
     * 
     */
    public ProductService(
            ProductRepository productRepository,
            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Метод оформления заказа пицы.
     *
     * @param userNumber номер телефона клиента
     * @param productId  идентификатор товара
     * @return заказанный товар
     * @throws Exception если товар не найден, товар уже заказан или нет готовых пиц
     */

    public Product orderProduct(String userNumber, Long productId, int orderQuantity)
            throws Exception {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (!productOpt.isPresent()) {
            throw new ProductNotFoundException("Продукт не найден.");
        }

        Product product = productOpt.get();

        if (orderProductByUser(userNumber, productId)) {
            throw new ProductAlreadyOrderedException("Вы уже заказали этот продукт.");
        }

        if (product.getQuantityAvailable() < orderQuantity) {
            throw new InsufficientStockException("Недостаточно готовых пиц.");
        }

        // Уменьшаем количество доступных товаров
        product.setQuantityAvailable(product.getQuantityAvailable() - orderQuantity);
        productRepository.save(product);

        // Создаем и сохраняем заказ
        Order order = new Order();
        order.setUserNumber(String.valueOf(userNumber));
        order.setOrderDate(LocalDate.now().toString());
        order.setProductId(product.getId());
        order.setOrderQuantity(orderQuantity);
        orderRepository.save(order);
        return product;
    }

    /**
     * Метод для проверки, заказал ли пользователь товар.
     *
     * @param userNumber номер телефона клиента
     * @param productId  идентификатор товара
     * @return true, если клиента уже заказал товар, иначе - false
     */
    public Boolean orderProductByUser(String userNumber, Long productId) {
        Order validate = orderRepository
                .findOrderByUserNumberAndProductId(userNumber, productId);
        return validate != null;
    }

    /**
     * Метод для получения текущего количества заказанных товаров пользователем.
     *
     * @param userNumber номер телефона клиента
     * @return текущее количество заказанных товаров
     */
    public int currentOrderCount(String userNumber) {
        return orderRepository.findOrdersByUserNumber(userNumber).size();
    }

}
