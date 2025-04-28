package com.nhnacademy.accountapi.domain.dto;

import com.nhnacademy.accountapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserListResponseDTO {
    List<User> userList;
}
