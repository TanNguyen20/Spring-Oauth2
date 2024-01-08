package com.springoauth.springoauthclient.model.dao;

import com.springoauth.springoauthclient.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findByName(String name);
}