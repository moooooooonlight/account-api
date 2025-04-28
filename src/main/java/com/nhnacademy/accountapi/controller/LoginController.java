package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.dto.ResponseDTO;
import com.nhnacademy.accountapi.domain.request.CreateUserRequest;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.service.UserService;
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


    @GetMapping("/login")
    public HttpStatus login(@RequestParam String userId,
                            @RequestParam String userPassword){
        if(userService.match(userId,userPassword)){
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

//    @GetMapping("/login")
//    public ResponseEntity<Void> login(@RequestParam String userId,
//                                      @RequestParam String userPassword){
//        if(userService.match(userId,userPassword)){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }

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
