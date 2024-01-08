package com.springoauth.springoauthclient.model.dao;

import com.springoauth.springoauthclient.model.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);
}