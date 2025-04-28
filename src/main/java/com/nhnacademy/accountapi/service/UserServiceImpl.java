package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.exception.UserAlreadyExistsException;
import com.nhnacademy.accountapi.domain.exception.UserNotFoundException;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers(){
        List<User> all = userRepository.findAll();
        return all;
    }

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

    @Override
    public void updateUser(String userId, User user) {
        if(Objects.isNull(user)) {
            throw new IllegalArgumentException();
        }

        if(!userRepository.existsByUserId(userId)){
            throw new UserNotFoundException();
        }

        userRepository.updateUser(userId, user.getUserName(), user.getUserPassword(), user.getUserEmail(), user.getUserCud());
    }

    @Override
    public void deleteUser(String userId) {
        if(!userRepository.existsByUserId(userId)){
            throw new UserNotFoundException();
        }

        userRepository.deleteById(userId);
    }

}
