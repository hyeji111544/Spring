package kr.co.ch07.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.ch07.dto.User4DTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name="user4")
public class User4 {

    @Id
    private String uid;
    private String name;
    private String gender;
    private int age;;
    private String hp;
    private String addr;

    public User4DTO toDTO(){
        return User4DTO.builder()
                .uid(uid)
                .name(name)
                .gender(gender)
                .age(age)
                .hp(hp)
                .addr(addr).build();
    }
}
