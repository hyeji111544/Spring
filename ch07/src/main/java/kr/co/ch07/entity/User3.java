package kr.co.ch07.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.ch07.dto.User3DTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name="user3")
public class User3 {

    @Id
    private String uid;
    private String name;
    private String birth;
    private String hp;
    private String addr;

    public User3DTO toDTO(){
        return User3DTO.builder()
                .uid(uid)
                .name(name)
                .birth(birth)
                .hp(hp)
                .addr(addr).build();
    }
}
