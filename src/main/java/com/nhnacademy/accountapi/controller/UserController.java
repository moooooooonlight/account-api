package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.domain.model.UserResponseDTO;
import com.nhnacademy.accountapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable String userId) {
        User user = userService.findUserById(userId);

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getUserId(),
                user.getUserPassword(),
                user.getUserEmail(),
                user.getUserName()
                , user.getUserCud()
        );

        return userResponseDTO;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserCUD(@PathVariable String userId, @RequestParam CUD userCUD) {
        userService.updateUserCUD(userId, userCUD);
        return ResponseEntity.ok().build();
    }
}