package com.springoauth.springoauthclient.model.mapper;

import com.springoauth.springoauthclient.model.dto.UserDTO;
import com.springoauth.springoauthclient.model.entity.RoleEntity;
import com.springoauth.springoauthclient.model.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public class UserMapper {
    public static UserEntity fromDtoToEntity(UserDTO userDTO, UserEntity userEntity) {
        return userEntity.toBuilder().name(userDTO.getLogin()).email(userDTO.getEmail()).build();
    }

    public static UserDTO fromEntityDto(UserEntity userEntity) {
        RoleEntity userRole = userEntity.getRole();
        return new UserDTO(userEntity.getName(), userEntity.getEmail(), userRole);
    }
}
