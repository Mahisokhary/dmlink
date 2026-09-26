package ir.qgdev.qoqnus.dmlink.presentation.test.session;

import ir.qgdev.qoqnus.dmlink.application.session.SessionService;
import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt.JwtService;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionCreateResponseDto;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionDto;
import ir.qgdev.qoqnus.dmlink.presentation.test.PresentationTestApplication;
import jakarta.transaction.Transactional;
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
import tools.jackson.databind.ObjectMapper;

import static ir.qgdev.qoqnus.dmlink.presentation.test.session.SessionTestConstants.*;
import static ir.qgdev.qoqnus.dmlink.presentation.test.session.SessionTestConstants.PASSWORD;
import static ir.qgdev.qoqnus.dmlink.presentation.test.session.SessionTestConstants.USERNAME;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link ir.qgdev.qoqnus.dmlink.presentation.session.SessionRestController}
 */
@SpringBootTest(classes = PresentationTestApplication.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SessionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SessionService sessionService;

    @Test
    @WithMockUser(roles = "SESSION_CREATE")
    public void testCreate() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);

        SessionCreateRequestDto body = new SessionCreateRequestDto(USERNAME, PASSWORD);
        MockHttpServletRequestBuilder requestBuilder = post("/api/session/")
                .content(objectMapper.writeValueAsBytes(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        SessionCreateResponseDto res = objectMapper.readValue(content, SessionCreateResponseDto.class);
        assertEquals("Incorrect JWT is returned", res.session().id().toString(), jwtService.verifyAndFetchId(res.token()));
        assertEquals("Incorrect user is owner oof the session", user.getId(), res.session().user());

        SessionCreateRequestDto wrongBody = new SessionCreateRequestDto(USERNAME, PASSWORD + "WRONG PASSWORD");
        MockHttpServletRequestBuilder wrongRequestBuilder = post("/api/session/")
                .content(objectMapper.writeValueAsBytes(wrongBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult wrongMvcResult = mockMvc.perform(wrongRequestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "SESSION_GET_ALL")
    public void testGetAll() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();

        MockHttpServletRequestBuilder requestBuilder = get("/api/session/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        SessionDto[] sessionDtos = objectMapper.readValue(content, SessionDto[].class);
        assertEquals("Expected have exactly 1 value", 1, sessionDtos.length);

        SessionDto sessionDto = sessionDtos[0];
        assertEquals("ID differs from saved ID", session.getId(), sessionDto.id());
    }

    @Test
    @WithMockUser(roles = "SESSION_GET")
    public void testGet() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();

        MockHttpServletRequestBuilder requestBuilder = get("/api/session/{id}", session.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        SessionDto sessionDto = objectMapper.readValue(content, SessionDto.class);
        assertEquals("ID differs from saved ID", session.getId(), sessionDto.id());
        assertEquals("User differs from saved user", user.getId(), sessionDto.user());
    }

    @Test
    @WithMockUser(roles = { "SESSION_DELETE", "SESSION_GET" })
    public void testDelete() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();

        MockHttpServletRequestBuilder deleteRequestBuilder = delete("/api/session/{id}", session.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult deleteMvcResult = mockMvc.perform(deleteRequestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();

        MockHttpServletRequestBuilder getRequestBuilder = get("/api/session/{id}", session.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult getMvcResult = mockMvc.perform(getRequestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
