package com.smashrating.user.domain;

import com.smashrating.common.BaseEntity;
import com.smashrating.rating.domain.Rating;
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
    @Column(length = 30)
    private String realName;

    @NotNull
    @Column(length = 30)
    private String nickname;

    @NotNull
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Rating rating;

    public static User create(String username, String password, String realName, String nickname, String email, Role role) {
        User user = User.builder()
                .username(username)
                .password(password)
                .realName(realName)
                .nickname(nickname)
                .email(email)
                .role(role)
                .build();

        user.initRating();
        return user;
    }

    public void initRating() {
        this.rating = Rating.create(this);
    }

    public void update(User user) {
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
