package com.nhnacademy.accountapi.domain.dto;


import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserRequest {
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userName;


    public User makeUser(){
        User user = new User(this.userId,
                this.userPassword,
                this.userEmail,
                this.userName,
                CUD.ACTIVE);

        return user;
    }
}
