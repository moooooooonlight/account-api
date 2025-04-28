package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.dto.ResponseDTO;
import com.nhnacademy.accountapi.domain.request.CreateUserRequest;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userService;

    @Operation(summary = "로그인 페이지 요청", description = "유저 로그인 페이지 요청 시 사용되는 api")
    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String userId,
                                      @RequestParam String userPassword){
        if(userService.match(userId,userPassword)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "로그인 요청", description = "유저 로그인 요청 시 사용되는 api")
    @PostMapping("/login")
    public ResponseDTO signup(@RequestBody CreateUserRequest createUserRequest){
        if(Objects.isNull(createUserRequest)){
            throw new IllegalArgumentException();
        }

        User user = createUserRequest.makeUser();
        userService.saveUser(user);
        log.debug("id={}",user.getUserId());
        log.debug("password={}",user.getUserPassword());
        log.debug("email={}",user.getUserEmail());
        log.debug("name={}",user.getUserName());
        log.debug("cud={}",user.getUserCud());

        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "회원가입 성공");
        return response;
    }
}
