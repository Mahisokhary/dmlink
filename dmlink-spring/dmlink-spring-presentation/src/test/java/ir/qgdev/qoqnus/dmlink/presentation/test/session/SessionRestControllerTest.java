package ir.qgdev.qoqnus.dmlink.presentation.test.session;

import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.JwtService;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.session.dto.SessionCreateResponseDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        assertEquals("Incorrect user is owner oof the session", user.getId(), res.session().user().id());

        SessionCreateRequestDto wrongBody = new SessionCreateRequestDto(USERNAME, PASSWORD + "WRONG PASSWORD");
        MockHttpServletRequestBuilder wrongRequestBuilder = post("/api/session/")
                .content(objectMapper.writeValueAsBytes(wrongBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult wrongMvcResult = mockMvc.perform(wrongRequestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
