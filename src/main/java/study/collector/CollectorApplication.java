package study.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.User;
import study.collector.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class CollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectorApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }

}

