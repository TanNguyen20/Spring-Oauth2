package com.springoauth.springoauthclient.model.dao;

import com.springoauth.springoauthclient.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(final String username);

    @Transactional
    void removeUserByUsername(String username);
}