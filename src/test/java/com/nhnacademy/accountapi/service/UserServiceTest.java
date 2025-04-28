package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.exception.UserAlreadyExistsException;
import com.nhnacademy.accountapi.domain.exception.UserNotFoundException;
import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import com.nhnacademy.accountapi.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User testId = new User("testId", "testPassword", "test@email.com", "testName", CUD.ACTIVE);
        userRepository.save(testId);
    }

    @Test
    void testGetUsers() {
        User user = new User("testId2", "testPassword2", "test2@email.com", "testName2", CUD.ACTIVE);
        userRepository.save(user);
        List<User> users = userService.getUsers();

        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUserId()).isEqualTo("testId");
        assertThat(users.get(1).getUserId()).isEqualTo("testId2");
    }

    @Test
    void testFindUserById_Success() {
        User foundUser = userService.findUserById("testId");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo("testName");
    }

    @Test
    void testFindUserById_NotFound() {
        assertThatThrownBy(() -> userService.findUserById("NotExistsUser"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testFindUserById_IllegalArg_isEmpty() {
        assertThatThrownBy(() -> userService.findUserById(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testFindUserById_IllegalArg_isNull() {
        assertThatThrownBy(() -> userService.findUserById(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testSaveUser_Success() {
        User newUser = new User("newUser", "newPassword", "new@email.com", "newName", CUD.ACTIVE);
        userService.saveUser(newUser);
        User savedUser = userRepository.findById("newUser").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserName()).isEqualTo("newName");
    }

    @Test
    void testSaveUser_AlreadyExists() {
        User duplicateUser = new User("testId", "dupPassword", "dup@email.com", "dupName", CUD.ACTIVE);
        assertThatThrownBy(() -> userService.saveUser(duplicateUser))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void testMatch_IllegalArg_userId() {
        assertThatThrownBy(() -> userService.match(null, "testPassword"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testMatch_IllegalArg_password() {
        assertThatThrownBy(() -> userService.match("testId", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testMatch_Success() {
        boolean result = userService.match("testId", "testPassword");
        assertThat(result).isTrue();
    }

    @Test
    void testMatch_Failure() {
        boolean result = userService.match("testId", "wrongPassword");
        assertThat(result).isFalse();
    }

    @Test
    void testMatch_NotFound() {
        assertThatThrownBy(() -> userService.match("wrongUser", "wrongPassword"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testUpdateUser_Success() {
        User user = new User("testId", "updatePassword", "update@email.com", "updateName", CUD.DORMANT);
        userService.updateUser("testId", user);

        User updatedUser = userService.findUserById("testId");
        assertThat(updatedUser.getUserPassword()).isEqualTo("updatePassword");
        assertThat(updatedUser.getUserCud()).isEqualTo(CUD.DORMANT);
    }

    @Test
    void testUpdateUser_IllegalArg() {
        assertThatThrownBy(() -> userService.updateUser("testId", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testUpdateUser_NotFound() {
        User newUser = new User("newUser", "newPassword", "new@email.com", "newName", CUD.ACTIVE);
        assertThatThrownBy(() -> userService.updateUser("wrongUser", newUser))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testDeleteUser_Success() {
        userService.deleteUser("testId");
        assertThat(userRepository.existsByUserId("testId")).isFalse();
    }

    @Test
    void testDeleteUser_NotFound() {
        assertThatThrownBy(() -> userService.deleteUser("NotExistsUser"))
                .isInstanceOf(UserNotFoundException.class);
    }
}

