package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    private String name;

    private String description;

    @ManyToMany
    private Set<Permission> permissions;
}
