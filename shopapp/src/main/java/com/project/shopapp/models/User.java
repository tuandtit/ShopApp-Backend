package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;

    @Column(name = "fullname", length = 100)
    String fullName;

    @Column(name = "phone_number", length = 10)
    String phoneNumber;

    @Column(name = "address", length = 200)
    String address;

    @Column(name = "password", length = 200)
    String password;

    @Column(name = "is_active")
    Boolean active;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "facebook_account_id")
    Integer facebookAccountId;

    @Column(name = "google_account_id")
    Integer googleAccountId;

    @ManyToMany
    Set<Role> roles;
}
