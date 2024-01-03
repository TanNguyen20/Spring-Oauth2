package com.springoauth.springoauthclient.service;

import com.springoauth.springoauthclient.model.dto.UserDTO;
import com.springoauth.springoauthclient.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    UserDTO findByName(String username);

    UserDTO createUser(UserEntity user);
}
