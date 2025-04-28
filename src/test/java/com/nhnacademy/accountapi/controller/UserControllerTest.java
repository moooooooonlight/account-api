package com.nhnacademy.accountapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.domain.request.CreateUserRequest;
import com.nhnacademy.accountapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUserList() throws Exception {
        List<User> mockUsers = List.of(
                new User("user1", "password1", "user1@email.com", "name1", CUD.ACTIVE),
                new User("user2", "password2", "user2@email.com", "name2", CUD.ACTIVE)
        );

        given(userService.getUsers()).willReturn(mockUsers);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userList").isArray())
                .andExpect(jsonPath("$.userList.length()").value(2))
                .andExpect(jsonPath("$.userList[0].userId").value("user1"))
                .andExpect(jsonPath("$.userList[1].userId").value("user2"));
    }

    @Test
    void testGetUser() throws Exception {
        User mockUser = new User("user1", "password1", "user1@email.com", "name1", CUD.ACTIVE);

        given(userService.findUserById("user1")).willReturn(mockUser);

        mockMvc.perform(get("/users/user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.userPassword").value("password1"))
                .andExpect(jsonPath("$.userEmail").value("user1@email.com"))
                .andExpect(jsonPath("$.userName").value("name1"))
                .andExpect(jsonPath("$.userCud").value(String.valueOf(CUD.ACTIVE)));
    }

    @Test
    void testUpdateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest("testId", "testPassword", "test@email.com", "testName");

        mockMvc.perform(put("/users/testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/testId"))
                .andExpect(status().isOk());
    }
}
