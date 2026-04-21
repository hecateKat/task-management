package com.kat.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.taskmanagement.dto.project.CreateProjectRequestDto;
import com.kat.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.kat.taskmanagement.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private String token;

    @BeforeEach
    void setup() throws Exception {
        // register user (ignore conflict if already exists)
        UserRegistrationRequestDto reg = new UserRegistrationRequestDto(
                "projuser", "Pass1234!", "Pass1234!", "projuser@test.com", "Proj", "User");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)));

        // login
        String loginBody = "{\"username\":\"projuser\",\"password\":\"Pass1234!\"}";
        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andReturn().getResponse().getContentAsString();
        token = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    @DisplayName("GET /api/projects – returns 401 when not authenticated")
    void getAllProjects_notAuthenticated_returns401() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/projects – returns 200 when authenticated")
    void getAllProjects_authenticated_returns200() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/projects – returns 201 when authenticated with valid data")
    void createProject_authenticated_returns201() throws Exception {
        CreateProjectRequestDto request = new CreateProjectRequestDto(
                "Alpha", "desc", LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31), Status.INITIATED);
        mockMvc.perform(post("/api/projects")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
