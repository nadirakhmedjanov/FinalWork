package gb.ru.orderpizza.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import gb.ru.orderpizza.entity.Product;

import java.util.List;

/**
 * Интерфейс репозитория для работы с продуктами (пиццами).
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Метод для поиска продуктов по части названия.
     *
     * @param name часть (отрывок) из названия пицы.
     * @param pageable объект для пагинации результатов.
     * @return страница с продуктами (названиями пиц), удовлетворяющими условию поиска.
     */
    Page<Product> findByNameContaining(String name, Pageable pageable);

    /**
     * Метод для поиска продуктов по категории.
     *
     * @param category категория пиццы.
     * @param pageable объект для пагинации результатов.
     * @return страница продуктов, удовлетворяющих условию поиска.
     */
    Page<Product> findByCategoryContaining(String category, Pageable pageable);

    /**
     * Метод для поиска продуктов по идентификаторам.
     *
     * @param productIds список идентификаторов продуктов.
     * @return список продуктов.
     */
    @Query("select p from Product p where p.id in :productIds")
    List<Product> findProductByProductIds(List<Long> productIds);    
}
