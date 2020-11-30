package ru.job4j.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j.MainSpring;
import ru.job4j.model.ShortcutReq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MainSpring.class)
@AutoConfigureMockMvc
class ConvertControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void shouldConvertReturnCodeByUrl() throws Exception {
        ShortcutReq req = new ShortcutReq();
        req.setUrl("https://job4j.ru/TrackStudio/task/8993?thisframe=true");
        req.setTotal(1L);
        this.mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code")
                        .value("aHR0cHM6Ly9qb2I0ai5ydS9UcmFja1N0dWRpby90YXNrLzg5OTM_dGhpc2ZyYW1lPXRydWU="));
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectOnUrlByCode() throws Exception {
        String code = "aHR0cHM6Ly9qb2I0ai5ydS9UcmFja1N0dWRpby90YXNrLzg5OTM_dGhpc2ZyYW1lPXRydWU=";
        this.mockMvc.perform(get("/redirect/{code}", code))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://job4j.ru/TrackStudio/task/8993?thisframe=true"));
    }

    @Test
    @WithAnonymousUser
    public void shouldGetStatisticByAllUrls() throws Exception {
        this.mockMvc.perform(get("/statistic"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("[0].url").value("https://job4j.ru/TrackStudio/task/8993?thisframe=true"))
                .andExpect(jsonPath("[0].total").isNotEmpty());
    }
}