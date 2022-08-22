package com.ada.moviebattle.domain.entity;

import com.ada.moviebattle.domain.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @JsonIgnore
    private UUID id;
    private String username;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }
    @JsonIgnore
    public List<Role> getRoles() {
        return Arrays.asList(this.getRole());
    }
}
