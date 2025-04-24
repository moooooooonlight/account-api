package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.exception.UserAlreadyExistsException;
import com.nhnacademy.accountapi.domain.exception.UserNotFoundException;
import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            throw new UserAlreadyExistsException();
        }

        userRepository.save(user);
    }

    @Override
    public User findUserById(String userId) {
        if(Objects.isNull(userId) || userId.isEmpty()){
            throw new IllegalArgumentException();
        }

        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()){
            throw new UserNotFoundException();
        }

        return findUser.get();
    }

    @Override
    public void updateUserCUD(String userId,CUD cud) {
        if(Objects.isNull(cud)){
            throw new IllegalArgumentException();
        }

        userRepository.updateUserCUD(userId,cud);
    }


    @Override
    public boolean match(String userId, String userPassword) {
        if(Objects.isNull(userId) || Objects.isNull(userPassword)){
            throw new IllegalArgumentException();
        }

        if(!userRepository.existsByUserId(userId)){
            throw new UserNotFoundException();
        }

        User user = findUserById(userId);

        return user.getUserPassword().equals(userPassword);
    }
}
