package com.springoauth.springoauthclient.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "role_table")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RoleEntity {

    @Id
    @Column(name = "role")
    private String role;
}
