package com.nhnacademy.accountapi.repository;
import com.nhnacademy.accountapi.domain.model.CUD;
import com.nhnacademy.accountapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String>{
    boolean existsByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.userCud = :cud WHERE u.userId = :userId ")
    int updateUserCUD(String userId, CUD cud);

}
