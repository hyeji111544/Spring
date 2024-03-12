package kr.co.ch07.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="board_user")
public class User {

    @Id
    private String uid;
    private String name;
    private String hp;

    @CreationTimestamp //regDate가 insert 될 때 날짜가 들어옴.
    private LocalDateTime regDate;
}
