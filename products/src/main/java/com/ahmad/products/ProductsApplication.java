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
// Comment this annotation temporarily for testing repository
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
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
//        Product product = new Product(1L, "iphone", "decription", new BigDecimal("12.00"), Currency.EUR, AvailabilityStatus.IN_STOCK, "electronics", "apple");
//        var p2 = Product.builder()
//                .id(2L)
//                .description("some text")
//                .name("macbook")
//                .currency(Currency.EUR)
//                .availabilityStatus(AvailabilityStatus.IN_STOCK)
//                .price(BigDecimal.valueOf(1400))
//                .brand("apple")
//                .category("electronics")
//                .build();
//        System.out.println(p2);

    }

}
