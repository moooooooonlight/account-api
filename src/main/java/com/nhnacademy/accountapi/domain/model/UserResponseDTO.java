package com.nhnacademy.accountapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;

    private String userPassword;

    private String userEmail;

    private String userName;

    private CUD userCud;
}
