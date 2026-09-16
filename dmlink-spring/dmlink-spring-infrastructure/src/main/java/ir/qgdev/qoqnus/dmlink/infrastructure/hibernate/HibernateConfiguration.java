package ir.qgdev.qoqnus.dmlink.infrastructure.hibernate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(@Value("${spring.application.name:dmlink}") String appName) {
        return props -> props.put(
                org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                new PhysicalNamingStrategyImpl(appName)
        );
    }

}
