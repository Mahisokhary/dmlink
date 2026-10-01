package ir.qgdev.qoqnus.dmlink.presentation.test.role;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import ir.qgdev.qoqnus.dmlink.presentation.test.PresentationTestApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

/**
 * Tests for {@link ir.qgdev.qoqnus.dmlink.presentation.role.RoleRestController}
 */
@SpringBootTest(classes = PresentationTestApplication.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class RoleRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

}
