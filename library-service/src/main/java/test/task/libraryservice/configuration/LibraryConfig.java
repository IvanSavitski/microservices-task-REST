package test.task.libraryservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("test.task")
@EntityScan("test.task.libraryservice.entity")
@EnableJpaRepositories(basePackages = {"test.task.bookservice.repository", "test.task.libraryservice.repository"})
public class LibraryConfig {

    @Bean
    //@LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
