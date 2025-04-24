package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import org.springframework.stereotype.Service;

public interface UserService {
    void saveUser(User user);
    User findUserById(String userId);
    void updateUserCUD(String userId, CUD cud);

    boolean match(String userId, String userPassword);
}
