package com.nhnacademy.accountapi.repository;
import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String>{
    boolean existsByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.userName = :userName, u.userPassword = :userPassword, u.userEmail = :userEmail, u.userCud = :userCud WHERE u.userId = :userId")
    void updateUser(String userId, String userName, String userPassword, String userEmail, CUD userCud);
}
