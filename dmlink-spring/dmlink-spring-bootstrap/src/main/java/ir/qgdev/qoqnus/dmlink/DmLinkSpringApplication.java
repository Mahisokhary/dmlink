package ir.qgdev.qoqnus.dmlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class DmLinkSpringApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DmLinkSpringApplication.class, args);
    }
}
