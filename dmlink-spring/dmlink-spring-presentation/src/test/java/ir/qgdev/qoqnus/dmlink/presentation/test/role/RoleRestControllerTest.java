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
    @WithMockUser(authorities = "role.create")
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

    @Test
    @WithMockUser(authorities = "role.get.all")
    public void testGetAll() throws Exception {
        Role parent = roleService.create(NAME);
        Role role = roleService.create(NAME2, Set.of(parent));

        MockHttpServletRequestBuilder requestBuilder = get("/api/role/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        RoleDto[] roleDtos = objectMapper.readValue(content, RoleDto[].class);
        assertEquals("There should only be 2 roles", 2, roleDtos.length);
    }

    @Test
    @WithMockUser(authorities = "role.get")
    public void testGet() throws Exception {
        Role role = roleService.create(NAME);

        MockHttpServletRequestBuilder requestBuilder = get("/api/role/{id}", role.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        RoleDto roleDto = objectMapper.readValue(content, RoleDto.class);
        assertEquals("ID is different from stored ID", role.getId(), roleDto.id());
    }

    @Test
    @WithMockUser(authorities = "role.get.name")
    public void testGetByName() throws Exception {
        Role role = roleService.create(NAME);

        MockHttpServletRequestBuilder requestBuilder = get("/api/role/")
                .queryParam("name", NAME)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        RoleDto roleDto = objectMapper.readValue(content, RoleDto.class);
        assertEquals("ID is different from stored ID", role.getId(), roleDto.id());
    }

    @Test
    @WithMockUser(authorities = "role.modify")
    public void testModify() throws Exception {
        Role role = roleService.create(NAME);
        RoleCreateRequestDto req = new RoleCreateRequestDto(NAME2, Set.of());

        MockHttpServletRequestBuilder requestBuilder = put("/api/role/{id}", role.getId())
                .content(objectMapper.writeValueAsBytes(req))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        RoleDto roleDto = objectMapper.readValue(content, RoleDto.class);
        assertEquals("ID is different from stored ID", role.getId(), roleDto.id());
        assertEquals("Name is different from saved name", NAME2, roleDto.name());
    }

    @Test
    @WithMockUser(authorities = "role.delete")
    public void testDelete() throws Exception {
        Role role = roleService.create(NAME);

        MockHttpServletRequestBuilder requestBuilder = delete("/api/role/{id}", role.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().bytes(new byte[]{}))
                .andReturn();

        assertFalse("Role is not deleted", roleRepository.findById(role.getId()).isPresent());
    }

}
