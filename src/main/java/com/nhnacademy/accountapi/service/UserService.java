package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    void saveUser(User user);
    User findUserById(String userId);
    boolean match(String userId, String userPassword);

    void updateUser(String userId, User user);
    void deleteUser(String userId);
}
