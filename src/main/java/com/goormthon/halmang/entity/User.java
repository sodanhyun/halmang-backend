package com.goormthon.halmang.entity;

import com.goormthon.halmang.auditing.BaseTimeEntity;
import com.goormthon.halmang.constant.enumVal.UserRole;
import com.goormthon.halmang.constant.enumVal.UserType;
import com.goormthon.halmang.domain.auth.requestDto.UserFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
@Getter @Setter @ToString
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    private String id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    @Convert(converter =  UserType.Converter.class)
    @Column(nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Convert(converter =  UserRole.Converter.class)
    @Column(nullable = false)
    private UserRole userRole;

    @Builder
    public User(String id, String password, String name, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }

    public static User createByOwn(UserFormDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        String password = passwordEncoder.encode(dto.getPassword());
        user.setPassword(password);
        user.setUserRole(UserRole.ADMIN);
        if(dto.getType().equals("parent")) {
            user.setUserType(UserType.PARENT);
        }else if(dto.getType().equals("child")) {
            user.setUserType(UserType.CHILD);
        }
        return user;
    }

}
