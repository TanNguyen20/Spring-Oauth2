package com.springoauth.springoauthclient.common;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class Role {

    public static String getHighestRole(Collection<? extends GrantedAuthority> authorities) {
        List roles = authorities.stream().map(grantedAuthority -> {
            return grantedAuthority.getAuthority();
        }).toList();

        if (roles.contains("ROLE_ADMIN")) {
            return "ROLE_ADMIN";
        } else {
            // Check for DEV role
            if (roles.contains("ROLE_DEV")) {
                return "ROLE_DEV";
            } else {
                // Return a default role if neither ADMIN nor DEV is found
                return "ROLE_DEFAULT";
            }
        }
    }
}
