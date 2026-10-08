package ir.qgdev.qoqnus.dmlink.presentation.test.user;

import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.presentation.test.PresentationTestApplication;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
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

import static ir.qgdev.qoqnus.dmlink.presentation.test.user.UserTestConstants.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link ir.qgdev.qoqnus.dmlink.presentation.user.UserRestController}
 */
@SpringBootTest(classes = PresentationTestApplication.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(authorities = "user.create")
    public void testCreate() throws Exception {
        UserCreateRequestDto body = new UserCreateRequestDto(USERNAME, DISPLAY_NAME, PASSWORD);
        MockHttpServletRequestBuilder requestBuilder = post("/api/user/")
                .content(objectMapper.writeValueAsBytes(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        UserDto userDto = objectMapper.readValue(content, UserDto.class);
        assertEquals("Username differs from saved username", USERNAME, userDto.username());
        assertEquals("Display name differs from saved username", DISPLAY_NAME, userDto.displayName());
    }

    @Test
    @WithMockUser(authorities = "user.get.all")
    public void testGetAll() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);

        MockHttpServletRequestBuilder requestBuilder = get("/api/user/")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        UserDto[] userDtoList = objectMapper.readValue(content, UserDto[].class);
        assertEquals("Expected have exactly 1 value", 1, userDtoList.length);

        UserDto userDto = userDtoList[0];
        assertEquals("Username differs from saved username", USERNAME, userDto.username());
        assertEquals("Display name differs from saved username", DISPLAY_NAME, userDto.displayName());
        assertEquals("ID differs from saved ID", user.getId(), userDto.id());
    }

    @Test
    @WithMockUser(authorities = "user.get")
    public void testGet() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);

        MockHttpServletRequestBuilder requestBuilder = get("/api/user/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        UserDto userDto = objectMapper.readValue(content, UserDto.class);
        assertEquals("Username differs from saved username", USERNAME, userDto.username());
        assertEquals("Display name differs from saved username", DISPLAY_NAME, userDto.displayName());
        assertEquals("ID differs from saved ID", user.getId(), userDto.id());
    }

    @Test
    @WithMockUser(authorities = "user.get.username")
    public void testGetByUsername() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);

        MockHttpServletRequestBuilder requestBuilder = get("/api/user/")
                .param("username", USERNAME)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        UserDto userDto = objectMapper.readValue(content, UserDto.class);
        assertEquals("Username differs from saved username", USERNAME, userDto.username());
        assertEquals("Display name differs from saved username", DISPLAY_NAME, userDto.displayName());
        assertEquals("ID differs from saved ID", user.getId(), userDto.id());
    }

    @Test
    @WithMockUser(authorities = "user.modify")
    public void testModify() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);

        UserCreateRequestDto requestDto = new UserCreateRequestDto(USERNAME2, DISPLAY_NAME2, PASSWORD);
        MockHttpServletRequestBuilder requestBuilder = put("/api/user/{id}", user.getId())
                .content(objectMapper.writeValueAsBytes(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        UserDto userDto = objectMapper.readValue(content, UserDto.class);
        assertEquals("Username differs from modified username", USERNAME2, userDto.username());
        assertEquals("Display name differs from modified username", DISPLAY_NAME2, userDto.displayName());
        assertEquals("ID differs from saved ID", user.getId(), userDto.id());
    }

    @Test
    @WithMockUser(authorities = { "user.delete", "user.get" })
    public void testDelete() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);

        MockHttpServletRequestBuilder deleteRequestBuilder = delete("/api/user/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult deleteMvcResult = mockMvc.perform(deleteRequestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();

        MockHttpServletRequestBuilder getRequestBuilder = get("/api/user/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult getMvcResult = mockMvc.perform(getRequestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
