package kr.co.ch07.entity.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="shop_customer")
public class Customer {

    @Id
    private String custId;
    private String  name;
    private int age;
    private String hp;
    private String addr;

    @CreationTimestamp
    private LocalDateTime regDate;

}
