package ir.qgdev.qoqnus.dmlink.presentation.test.role;

import ir.qgdev.qoqnus.dmlink.application.role.RoleService;
import ir.qgdev.qoqnus.dmlink.domain.role.Role;
import ir.qgdev.qoqnus.dmlink.domain.role.RoleRepository;
import ir.qgdev.qoqnus.dmlink.presentation.role.dto.RoleCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.role.dto.RoleDto;
import ir.qgdev.qoqnus.dmlink.presentation.test.PresentationTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;

import static ir.qgdev.qoqnus.dmlink.presentation.test.role.RoleTestConstants.*;
import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @WithMockUser(authorities = "ROLE_ROLE_CREATE")
    public void testCreate() throws Exception {
        Role role = roleService.create(NAME);
        RoleCreateRequestDto req = new RoleCreateRequestDto(NAME2, Set.of(role.getId()));

        MockHttpServletRequestBuilder requestBuilder = post("/api/role/")
                .content(objectMapper.writeValueAsBytes(req))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        RoleDto roleDto = objectMapper.readValue(content, RoleDto.class);
        assertEquals("Name is different from passed name", NAME2, roleDto.name());
        assertEquals("Only one parent is expected for created role", 1, roleDto.parents().size());
        assertTrue("Invalid parent for created role", roleDto.parents().contains(role.getId()));
    }

}
