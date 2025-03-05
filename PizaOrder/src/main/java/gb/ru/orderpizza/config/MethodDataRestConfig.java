package gb.ru.orderpizza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import gb.ru.orderpizza.entity.Message;
import gb.ru.orderpizza.entity.Product;
import gb.ru.orderpizza.entity.Feedback;

/**
 * Конфигурационный класс для настройки Spring Data REST.
 */
@Configuration
public class MethodDataRestConfig implements RepositoryRestConfigurer {

  private String clientUrl = Environment.host;

  /**
   * Конфигурирует Spring Data REST.
   *
   * @param config конфигурация репозитория Spring Data REST
   * @param cors   CORS-реестр
   */
  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config,
      CorsRegistry cors) {

    HttpMethod[] unsupportedActions = {
         HttpMethod.POST,
         HttpMethod.DELETE,
         HttpMethod.PUT
    };

    config.exposeIdsFor(Product.class);
    config.exposeIdsFor(Feedback.class);
    config.exposeIdsFor(Message.class);

    disableHttpMethods(Product.class, config, unsupportedActions);
    disableHttpMethods(Feedback.class, config, unsupportedActions);
    disableHttpMethods(Message.class, config, unsupportedActions);

    cors.addMapping(config.getBasePath() + "/**")
        .allowedOrigins(clientUrl);
  }

  /**
   * Отключает определенные HTTP-методы для указанного типа ресурса.
   *
   * @param self               тип ресурса
   * @param config             конфигурация репозитория Spring Data REST
   * @param unsupportedActions массив неподдерживаемых HTTP-методов
   */
  @SuppressWarnings("unused")
  private void disableHttpMethods(
      Class<?> self,
      RepositoryRestConfiguration config,
      HttpMethod[] unsupportedActions) {
    config.getExposureConfiguration()
        .forDomainType(self)
        .withItemExposure(
            (metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
        .withCollectionExposure(
            (metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
  }
}
