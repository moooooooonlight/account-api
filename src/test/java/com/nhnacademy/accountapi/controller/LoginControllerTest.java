package com.nhnacademy.accountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.domain.request.CreateUserRequest;
import com.nhnacademy.accountapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin_OK() throws Exception {
        given(userService.match("testId", "testPassword")).willReturn(true);

        mockMvc.perform(get("/login")
                        .param("userId", "testId")
                        .param("userPassword", "testPassword"))
                .andExpect(status().isOk());
    }

    @Test
    void testLogin_NotFound() throws Exception {
        given(userService.match("wrongId", "wrongPassword")).willReturn(false);

        mockMvc.perform(get("/login")
                        .param("userId", "wrongId")
                        .param("userPassword", "wrongPassword"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSignUp_OK() throws Exception {
        CreateUserRequest request = new CreateUserRequest("testId", "testPassword", "test@email.com", "testName");

        Mockito.doNothing().when(userService).saveUser(any(User.class));

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("회원가입 성공"));
    }

    @Test
    void testSignUp_IllegalArg() throws Exception {
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }
}
