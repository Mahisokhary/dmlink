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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @WithMockUser(roles = "USER_CREATE")
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
    @WithMockUser(roles = "USER_GET_ALL")
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

}
