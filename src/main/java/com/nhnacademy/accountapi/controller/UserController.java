package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @ModelAttribute("user")
    public User getUser(@PathVariable("userId") String userId){
        User user = userService.findUserById(userId);
        return user;
    }


    @GetMapping
    public User getUsers(Model model){
        User user = (User) model.getAttribute("user");
        return user;
    }


    @PutMapping
    public HttpStatus updateUserCUD(Model model, HttpServletRequest request){
        User user = (User) model.getAttribute("user");
        CUD userCUD = (CUD) request.getAttribute("userCUD");
        user.setUserCud(userCUD);

        userService.updateUserCUD(user.getUserId(), userCUD);

        return HttpStatus.OK;
    }
}
