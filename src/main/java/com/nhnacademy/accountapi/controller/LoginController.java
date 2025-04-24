package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.ResponseDTO;
import com.nhnacademy.accountapi.domain.dto.CreateUserRequest;
import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userService;


    @GetMapping("/login")
    public HttpStatus login(@RequestParam String userId,
                            @RequestParam String userPassword){
        if(userService.match(userId,userPassword)){
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody CreateUserRequest createUserRequest){
        if(Objects.isNull(createUserRequest)){
            throw new IllegalArgumentException();
        }

        User user = createUserRequest.makeUser();
        userService.saveUser(user);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "로그인 성공");
        return response;
    }
}
