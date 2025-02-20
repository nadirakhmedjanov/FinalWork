package gb.ru.orderpizza.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import gb.ru.orderpizza.entity.Product;

import java.util.List;

/**
 * Интерфейс репозитория для работы с продуктами.(пицей)
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Метод для поиска продуктов по части названия.
     *
     * @param name часть (отрывок) из названия пицы
     * @param pageable объект для пагинации результатов
     * @return страница с продутками (азваниям пиц), удовлетворяющих условию поиска
     */
    Page<Product> findByNameContaining(
      @RequestParam("name") String name,
      Pageable pageable);

    /**
     * Метод для поиска продуктов по категории.
     *
     * @param category категория пицқ
     * @param pageable объект для пагинации результатов
     * @return страница продуктов, удовлетворяющих условию поиска
     */
    Page<Product> findByCategory(
      @RequestParam("category") String category,
      Pageable pageable);

    /**
     * Метод для поиска продуктов по идентификаторам.
     *
     * @param productId список идентификаторов продуктов
     * @return список продуктов
     */
    @Query("select product from Product product where product.id in :product_ids")
  List<Product> findProductByProductIds(@Param("product_ids") List<Long> productId);    
}