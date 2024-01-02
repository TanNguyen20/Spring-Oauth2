package com.springoauth.springoauthclient.model.dto;

import com.springoauth.springoauthclient.model.entity.RoleEntity;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {
    private String login;
    private String email;
    private RoleEntity role;

}