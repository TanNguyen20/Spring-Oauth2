package com.springoauth.springoauthclient.service.impl;

import com.springoauth.springoauthclient.common.UserRoleEnum;
import com.springoauth.springoauthclient.model.entity.RoleEntity;
import com.springoauth.springoauthclient.model.entity.UserEntity;
import com.springoauth.springoauthclient.model.entity.UserPrincipal;
import com.springoauth.springoauthclient.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        log.info("STARTING LOADING USER FROM USERNAME");
        Optional<UserEntity> user = userRepository.findByEmail(name);
        if(user.isPresent()){
            log.info("FOUND USER");
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(user.get().getRole());
            Collection<SimpleGrantedAuthority> authorities= mapRolesToAuthorities(roles);

            return new UserPrincipal(user.get().getName(), user.get().getPassword(),true, authorities);

        }
        else{
            throw new UsernameNotFoundException("User " + name + " not found");
        }
    }

}
