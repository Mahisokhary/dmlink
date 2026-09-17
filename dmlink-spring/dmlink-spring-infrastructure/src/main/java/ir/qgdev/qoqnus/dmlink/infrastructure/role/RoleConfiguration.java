package ir.qgdev.qoqnus.dmlink.infrastructure.role;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.application.role.RoleServiceImpl;
import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfiguration {
    @Bean
    public RoleService roleService(IdGenerator idGenerator, RoleRepository roleRepository) {
        return new RoleServiceImpl(idGenerator, roleRepository);
    }

}
