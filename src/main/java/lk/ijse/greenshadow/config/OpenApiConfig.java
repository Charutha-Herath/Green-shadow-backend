package lk.ijse.greenshadow.config;


import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Green Shadow Crop Monitoring API")
                        .version("1.0")
                        .description("API documentation for Green Shadow Crop Monitoring System"));
    }
}
