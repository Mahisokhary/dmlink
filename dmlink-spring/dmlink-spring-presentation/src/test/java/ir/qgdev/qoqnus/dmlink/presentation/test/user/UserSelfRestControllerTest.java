package ir.qgdev.qoqnus.dmlink.presentation.test.user;

import ir.qgdev.qoqnus.dmlink.application.session.SessionService;
import ir.qgdev.qoqnus.dmlink.application.user.UserService;
import ir.qgdev.qoqnus.dmlink.domain.session.Session;
import ir.qgdev.qoqnus.dmlink.domain.session.SessionRepository;
import ir.qgdev.qoqnus.dmlink.domain.user.User;
import ir.qgdev.qoqnus.dmlink.domain.user.UserRepository;
import ir.qgdev.qoqnus.dmlink.infrastructure.session.SessionJpaRepository;
import ir.qgdev.qoqnus.dmlink.presentation.security.authenticate.jwt.JwtAuthentication;
import ir.qgdev.qoqnus.dmlink.presentation.test.PresentationTestApplication;
import ir.qgdev.qoqnus.dmlink.presentation.user.UserActions;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserChangePasswordRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static ir.qgdev.qoqnus.dmlink.presentation.test.user.UserTestConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PresentationTestApplication.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class UserSelfRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public UserService userService;

    @Autowired
    public SessionService sessionService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public SessionRepository sessionRepository;

    @Autowired
    private SessionJpaRepository sessionJpaRepository;

    @Test
    public void testGet() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();
        Collection<GrantedAuthority> authorities = Set.of(UserActions.GET_SELF);

        MockHttpServletRequestBuilder requestBuilder = get("/user/@me")
                .with(authentication(JwtAuthentication.fromSession(session, authorities)))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        UserDto userDto = objectMapper.readValue(content, UserDto.class);
        assertEquals("ID differs from passed ID", user.getId(), userDto.id());
        assertEquals("Username differs from passed username", USERNAME, userDto.username());
    }

    @Test
    public void testDelete() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();
        Collection<GrantedAuthority> authorities = Set.of(UserActions.DELETE_SELF);

        MockHttpServletRequestBuilder requestBuilder = delete("/user/@me")
                .with(authentication(JwtAuthentication.fromSession(session, authorities)))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().bytes(new byte[]{}))
                .andReturn();

        assertFalse("User is not deleted", userRepository.findById(user.getId()).isPresent());
    }

    @Test
    public void testModifyPassword() throws Exception {
        User user = userService.create(USERNAME, DISPLAY_NAME, PASSWORD);
        Session session = sessionService.login(USERNAME, PASSWORD).orElseThrow();
        Collection<GrantedAuthority> authorities = Set.of(UserActions.MODIFY_SELF_PASSWORD);
        UserChangePasswordRequestDto req = new UserChangePasswordRequestDto(PASSWORD, PASSWORD2);

        MockHttpServletRequestBuilder requestBuilder = put("/user/@me/password")
                .content(objectMapper.writeValueAsBytes(req))
                .contentType(MediaType.APPLICATION_JSON)
                .with(authentication(JwtAuthentication.fromSession(session, authorities)))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().bytes(new byte[]{}))
                .andReturn();

        Optional<Session> sessionWithOldPassword = sessionService.login(USERNAME, PASSWORD);
        Optional<Session> sessionWithNewPassword = sessionService.login(USERNAME, PASSWORD2);
        assertTrue("Old password is still usable", sessionWithOldPassword.isEmpty());
        assertTrue("New password is not usable", sessionWithNewPassword.isPresent());
    }

}
