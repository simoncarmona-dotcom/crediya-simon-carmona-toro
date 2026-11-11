package co.com.crediya.api;

import co.com.crediya.api.config.CorsConfig;
import co.com.crediya.api.exception.GlobalExceptionHandler;
import co.com.crediya.usecase.solicitud.SolicitudUseCase;
import co.com.crediya.usecase.user.UserUseCase;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "co.com.crediya.api",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = CorsConfig.class
        ))
@Import({ApiRest.class, SolicitudApiRest.class, GlobalExceptionHandler.class})
public class TestConfig {

    @Bean
    public UserUseCase userUseCase() {
        return Mockito.mock(UserUseCase.class);
    }

    @Bean
    public SolicitudUseCase solicitudUseCase() {
        return Mockito.mock(SolicitudUseCase.class);
    }
}
