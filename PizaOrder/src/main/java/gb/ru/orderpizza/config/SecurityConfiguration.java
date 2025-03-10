package gb.ru.orderpizza.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

/**
 * Конфигурация безопасности приложения.
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Создает цепочку фильтров безопасности для HTTP-запросов.
     *
     * @param http объект конфигурации HTTP-безопасности
     * @return цепочка фильтров безопасности
     * @throws Exception если возникает ошибка при создании цепочки фильтров
     */
    @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf().disable();

    http.authorizeRequests(configurer -> configurer
        .antMatchers(
            "/api/products/secure/**",
            "/api/reviews/secure/**",
            "/api/messages/secure/**")
        .authenticated())
        .oauth2ResourceServer()
        .jwt();

    http.cors();

    http.setSharedObject(ContentNegotiationStrategy.class,
        new HeaderContentNegotiationStrategy());

    Okta.configureResourceServer401ResponseBody(http);

    return http.build();
  }
}