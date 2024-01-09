package com.springoauth.springoauthclient.setup;

import com.springoauth.springoauthclient.model.dao.OrganizationRepository;
import com.springoauth.springoauthclient.model.dao.PrivilegeRepository;
import com.springoauth.springoauthclient.model.entity.Organization;
import com.springoauth.springoauthclient.model.entity.Privilege;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupData {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @PostConstruct
    public void init() {
        initOrganizations();
        initPrivileges();
    }

    private void initOrganizations() {
        final Organization org1 = new Organization("FirstOrg");
        organizationRepository.save(org1);

        final Organization org2 = new Organization("SecondOrg");
        organizationRepository.save(org2);
    }

    private void initPrivileges() {
        final Privilege privilege1 = new Privilege("FOO_READ_PRIVILEGE");
        privilegeRepository.save(privilege1);

        final Privilege privilege2 = new Privilege("FOO_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);
    }
}