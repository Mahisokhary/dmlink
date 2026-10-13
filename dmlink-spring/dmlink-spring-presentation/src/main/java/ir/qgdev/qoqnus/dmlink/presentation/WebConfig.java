package ir.qgdev.qoqnus.dmlink.presentation;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureApiVersioning(@NonNull ApiVersionConfigurer configurer) {
        configurer
                .addSupportedVersions("1.0")
                .setDefaultVersion("1.0")
                .useRequestHeader("X-API-Version");
    }

}
