package com.example.investments_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Document all endpoints in the platform package, including auth endpoints
                .apis(RequestHandlerSelectors.basePackage("com.example.investments_manager"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Example Investment Project")
                .description("Demo project for Spring Boot")
                .contact(new Contact("Guy Bernstein", "www.tapitim.com", "guyu669@gmail.com"))
                .license("License of API")
                .licenseUrl("API license URL")
                .build();
    }

    // RestTemplate bean used for OAuth2 token exchange and API calls
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
