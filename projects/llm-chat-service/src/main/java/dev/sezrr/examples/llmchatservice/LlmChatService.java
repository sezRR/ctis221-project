package dev.sezrr.examples.llmchatservice;

import dev.sezrr.examples.llmchatservice.product.Product;
import dev.sezrr.examples.llmchatservice.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LlmChatService {
    private static final Logger log = LoggerFactory.getLogger(LlmChatService.class);

    public static void main(String[] args) {
        SpringApplication.run(LlmChatService.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
          // Persist 1 product
            if (productRepository.count() == 0) {
                var product = new Product(1, "Apple");
                productRepository.save(product);
                log.info("Product created: {}", product.getProductName());
            }
        };
    }
}
