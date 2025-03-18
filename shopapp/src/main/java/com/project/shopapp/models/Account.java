package com.project.shopapp.models;

import com.project.shopapp.common.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        name = "account",
        indexes = {
                @Index(
                        columnList = "phone_number",
                        unique = true
                )
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends AbstractAuditing<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    String username;

    @Column(name = "avatar", columnDefinition = "text")
    String avatar;

    @Column(name = "password", length = 200)
    String passwordHash;

    @Column(name = "is_active")
    Boolean active;

    @Column(name = "auth_provider")
    @Enumerated(EnumType.STRING)
    AuthProvider authProvider = AuthProvider.PHONENUMBER_AND_PASSWORD;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public void addRole(Role role) {
        if (ObjectUtils.isEmpty(roles)) {
            this.roles = new ArrayList<>() {
            };
        }
        roles.add(role);
    }

}
