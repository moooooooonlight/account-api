package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testExistsByUserId_Success() {
        User user = new User("testId", "testPassword", "test@email.com", "testName", CUD.ACTIVE);
        userRepository.save(user);

        boolean exists = userRepository.existsByUserId("testId");
        assertThat(exists).isTrue();
    }

    @Test
    void testUpdateUser() {
        User user = new User("testId", "testPassword", "test@email.com", "testName", CUD.ACTIVE);
        userRepository.save(user);

        userRepository.updateUser(
                "testId",
                "newName",
                "newPassword",
                "new@email.com",
                CUD.DORMANT
        );
        userRepository.flush();

        Optional<User> updatedUser = userRepository.findById("testId");
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUserName()).isEqualTo("newName");
        assertThat(updatedUser.get().getUserPassword()).isEqualTo("newPassword");
        assertThat(updatedUser.get().getUserEmail()).isEqualTo("new@email.com");
        assertThat(updatedUser.get().getUserCud()).isEqualTo(CUD.DORMANT);
    }
}