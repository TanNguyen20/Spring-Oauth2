package com.springoauth.springoauthclient.model.entity;

import com.springoauth.springoauthclient.common.RegistrationSourceEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_table")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
public class UserEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;

    private String email;

    private String password;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private RegistrationSourceEnum source;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_name")
    private RoleEntity role;
}