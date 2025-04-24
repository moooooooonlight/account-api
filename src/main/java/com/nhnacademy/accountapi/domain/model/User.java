package com.nhnacademy.accountapi.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    @Setter
    @NotNull
    private String userPassword;

    @Setter
    private String userEmail;

    @Setter
    @NotNull
    private String userName;

    @Setter
    @NotNull
    private CUD userCud;

    public User(String userId, String userPassword, String userEmail, String userName, CUD userCud) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCud = userCud;
    }
}
