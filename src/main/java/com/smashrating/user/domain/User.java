package com.smashrating.user.domain;

import com.smashrating.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, length = 50)
    private String username;

    @NotNull
    @Column(length = 200)
    private String password;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public static User create(String username, String password, String name, String email, Role role) {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .role(role)
                .build();
    }

    public void update(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
