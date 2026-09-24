package ir.qgdev.qoqnus.dmlink.presentation.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Let tests access components, repositories, entities, etc...
 */
@EnableJpaRepositories(basePackages = "ir.qgdev.qoqnus.dmlink")
@EntityScan(basePackages = "ir.qgdev.qoqnus.dmlink")
@SpringBootApplication(scanBasePackages = "ir.qgdev.qoqnus.dmlink")
public class PresentationTestApplication {
}
