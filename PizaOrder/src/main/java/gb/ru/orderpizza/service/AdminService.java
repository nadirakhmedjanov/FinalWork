package gb.ru.orderpizza.service;

import org.springframework.stereotype.Service;

import gb.ru.orderpizza.dao.OrderRepository;
import gb.ru.orderpizza.dao.ProductRepository;
import gb.ru.orderpizza.dao.FeedbackRepository;
import gb.ru.orderpizza.entity.Product;
import gb.ru.orderpizza.requestmodels.AppendProductRequest;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Сервис для администратора, предоставляющий методы для управления товарами.
 */
@Service
@Transactional
public class AdminService {

    private ProductRepository productRepository;
    private FeedbackRepository feedbackRepository;
    private OrderRepository orderRepository;

    /**
     * Конструктор для создания экземпляра сервиса администратора.
     * Осуществляет внедрение зависимостей репозиториев продуктов, отзывов и заказов.
     *
     * @param productRepository репозиторий продуктов
     * @param feedbackRepository  репозиторий отзывов
     * @param orderRepository   репозиторий заказов
     */
    public AdminService(
            ProductRepository productRepository,
            FeedbackRepository feedbackRepository,
            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.feedbackRepository = feedbackRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Метод для добавления нового продукта в базу данных.
     *
     * @param product данные о продукте для добавления
     */
    public void postProduct(AppendProductRequest product) {
        Product item = new Product();
        item.setName(product.getName());
        item.setDescription(product.getDescription());
        item.setPrice(product.getPrice());
        item.setQuantityAvailable(product.getQuantityAvaillable());
        item.setCategory(product.getCategory());
        item.setImg(product.getImg());
        productRepository.save(item);
    }

    /**
     * Метод для удаления продукта из базы данных.
     *
     * @param productId идентификатор продукта для удаления
     * @throws Exception если продукт не найден
     */
    public void deleteProduct(Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()) {
            throw new Exception("Товар отсутствует");
        }

        productRepository.delete(product.get());
        orderRepository.deleteAllByProductId(productId);
        feedbackRepository.deleteAllByProductId(productId);
    }

    /**
     * Метод для увеличения количества доступного количества пицы на 1.
     *
     * @param productId идентификатор продукта
     * @throws Exception если продукт не найден
     */
    public void incProductCount(Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new Exception("Товар отсутствует");
        }

        product.get().setQuantityAvailable(product.get().getQuantityAvailable() + 1);
        productRepository.save(product.get());
    }

    /**
     * Метод для уменьшения количества доступных копий продукта.
     *
     * @param productId идентификатор продукта
     * @throws Exception если продукт не найден или количество копий равно нулю
     */
    public void decProductCount(Long productId) throws  Exception {
        Optional<Product> product = productRepository.findById(productId);
        int count = product.get().getQuantityAvailable();
        if (count == 0)
            return;;
        if (!product.isPresent()
        || product.get().getQuantityAvailable() <= 0) {
            throw new Exception("Товар отсутствует");
        }
        product.get().setQuantityAvailable(product.get().getQuantityAvailable() - 1);
        productRepository.save(product.get());
    }
}