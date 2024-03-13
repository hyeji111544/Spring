package kr.co.ch07.dto;

import kr.co.ch07.entity.User4;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User4DTO {
    private String uid;
    private String name;
    private String gender;
    private int age;
    private String hp;
    private String addr;

    public User4 toEntity(){
        return User4.builder()
                .uid(uid)
                .name(name)
                .gender(gender)
                .age(age)
                .hp(hp)
                .addr(addr).build();
    }
}
