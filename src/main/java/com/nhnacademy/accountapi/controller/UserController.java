package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.dto.UserListResponseDTO;
import com.nhnacademy.accountapi.domain.request.CreateUserRequest;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.domain.dto.UserResponseDTO;
import com.nhnacademy.accountapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserListResponseDTO> getUserList(){
        List<User> users = userService.getUsers();
        UserListResponseDTO userListResponseDTO = new UserListResponseDTO(users);

        return ResponseEntity.status(HttpStatus.OK).body(userListResponseDTO);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable String userId) {
        User user = userService.findUserById(userId);

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUserId(),
                user.getUserPassword(),
                user.getUserEmail(),
                user.getUserName(),
                user.getUserCud()
        );

        return userResponseDTO;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable String userId, @RequestBody CreateUserRequest createUserRequest) {
        userService.updateUser(userId, createUserRequest.makeUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}