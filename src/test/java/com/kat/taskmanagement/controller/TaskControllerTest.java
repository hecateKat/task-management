package com.kat.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private String token;

    @BeforeEach
    void setup() throws Exception {
        UserRegistrationRequestDto reg = new UserRegistrationRequestDto(
                "taskuser", "Pass1234!", "Pass1234!", "taskuser@test.com", "Task", "User");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)));

        String loginBody = "{\"username\":\"taskuser\",\"password\":\"Pass1234!\"}";
        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andReturn().getResponse().getContentAsString();
        token = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    @DisplayName("GET /api/tasks – returns 401 when not authenticated")
    void getTasks_notAuthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/tasks").param("projectId", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/tasks – returns 200 when authenticated")
    void getTasks_authenticated_returns200() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .param("projectId", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }
}
