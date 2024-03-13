package kr.co.ch08.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.ch08.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String uid;
    private String pass;
    private String name;
    private int age;
    private String hp;
    private String role;
    private LocalDateTime regDate;

    public User toEntity(){
        return User.builder()
                .uid(uid)
                .pass(pass)
                .name(name)
                .age(age)
                .hp(hp)
                .role(role)
                .regDate(regDate)
                .build();
    }

}