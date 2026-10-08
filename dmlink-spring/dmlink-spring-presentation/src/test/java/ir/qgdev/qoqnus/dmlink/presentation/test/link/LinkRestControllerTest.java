package ir.qgdev.qoqnus.dmlink.presentation.test.link;

import ir.qgdev.qoqnus.dmlink.application.link.LinkService;
import ir.qgdev.qoqnus.dmlink.domain.link.Link;
import ir.qgdev.qoqnus.dmlink.domain.link.LinkRepository;
import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import ir.qgdev.qoqnus.dmlink.presentation.link.dto.LinkCreateRequestDto;
import ir.qgdev.qoqnus.dmlink.presentation.link.dto.LinkDto;
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

import static ir.qgdev.qoqnus.dmlink.presentation.test.link.LinkTestConstants.*;
import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for {@link ir.qgdev.qoqnus.dmlink.presentation.link.LinkRestController}
 */
@SpringBootTest(classes = PresentationTestApplication.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class LinkRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @WithMockUser(authorities = "link.create")
    public void testCreate() throws Exception {
        LinkCreateRequestDto req = new LinkCreateRequestDto(NAME, TARGET);

        MockHttpServletRequestBuilder requestBuilder = post("/api/link/")
                .content(objectMapper.writeValueAsBytes(req))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        LinkDto linkDto = objectMapper.readValue(content, LinkDto.class);
        assertNull("No owner is set for the link, but owner is not null", linkDto.owner());
        assertEquals("Target is different from saved target", TARGET, HttpUrl.fromString(linkDto.target()));
        assertEquals("Name is different from saved name", NAME, linkDto.name());
    }

    @Test
    @WithMockUser(authorities = "link.get.all")
    public void testGetAll() throws Exception {
        Link link = linkService.create(NAME, TARGET, null);

        MockHttpServletRequestBuilder requestBuilder = get("/api/link/")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        LinkDto[] linkDtos = objectMapper.readValue(content, LinkDto[].class);
        assertEquals("There should only be one item", 1, linkDtos.length);

        LinkDto linkDto = linkDtos[0];
        assertNull("No owner is set for the link, but owner is not null", linkDto.owner());
        assertEquals("Target is different from saved target", TARGET, HttpUrl.fromString(linkDto.target()));
        assertEquals("Name is different from saved name", NAME, linkDto.name());
        assertEquals("ID is different from saved ID", link.getId(), linkDto.id());
    }

    @Test
    @WithMockUser(authorities = "link.get")
    public void testGet() throws Exception {
        Link link = linkService.create(NAME, TARGET, null);

        MockHttpServletRequestBuilder requestBuilder = get("/api/link/{id}", link.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        LinkDto linkDto = objectMapper.readValue(content, LinkDto.class);
        assertNull("No owner is set for the link, but owner is not null", linkDto.owner());
        assertEquals("Target is different from saved target", TARGET, HttpUrl.fromString(linkDto.target()));
        assertEquals("Name is different from saved name", NAME, linkDto.name());
        assertEquals("ID is different from saved ID", link.getId(), linkDto.id());
    }

    @Test
    @WithMockUser(authorities = "link.get.name")
    public void testGetByName() throws Exception {
        Link link = linkService.create(NAME, TARGET, null);

        MockHttpServletRequestBuilder requestBuilder = get("/api/link/")
                .queryParam("name", NAME)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        LinkDto linkDto = objectMapper.readValue(content, LinkDto.class);
        assertNull("No owner is set for the link, but owner is not null", linkDto.owner());
        assertEquals("Target is different from saved target", TARGET, HttpUrl.fromString(linkDto.target()));
        assertEquals("Name is different from saved name", NAME, linkDto.name());
        assertEquals("ID is different from saved ID", link.getId(), linkDto.id());
    }

    @Test
    @WithMockUser(authorities = "link.modify")
    public void testModify() throws Exception {
        Link link = linkService.create(NAME, TARGET, null);
        LinkCreateRequestDto req = new LinkCreateRequestDto(NAME2, TARGET2);

        MockHttpServletRequestBuilder requestBuilder = put("/api/link/{id}", link.getId())
                .content(objectMapper.writeValueAsBytes(req))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        byte[] content = mvcResult.getResponse().getContentAsByteArray();
        assertNotNull("No content is returned", content);

        LinkDto linkDto = objectMapper.readValue(content, LinkDto.class);
        assertNull("No owner is set for the link, but owner is not null", linkDto.owner());
        assertEquals("Target is different from saved target", TARGET2, HttpUrl.fromString(linkDto.target()));
        assertEquals("Name is different from saved name", NAME2, linkDto.name());
        assertEquals("ID is different from saved ID", link.getId(), linkDto.id());
    }

    @Test
    @WithMockUser(authorities = "link.delete")
    public void testDelete() throws Exception {
        Link link = linkService.create(NAME, TARGET, null);

        MockHttpServletRequestBuilder requestBuilder = delete("/api/link/{id}", link.getId())
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andExpect(content().bytes(new byte[]{}))
                .andReturn();

        assertFalse("Link is not deleted from repository", linkRepository.findById(link.getId()).isPresent());
    }

}
