package com.ahmad.products;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Products microservice documentation",
                description = "e-commerce products microservice",
                version = "v1",
                contact = @Contact(
                        name = "Ahmad Fayaz",
                        email = "ahmad@example.com",
                        url = "www.example.com"
                ),
                license = @License(
                        name = "Apache2.0",
                        url = "www.example.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "some description",
                url = "www.externaldoc.com"
        )
)
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

}
