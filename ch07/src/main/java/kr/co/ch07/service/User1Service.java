package kr.co.ch07.service;

import jakarta.persistence.Entity;
import kr.co.ch07.dto.User1DTO;
import kr.co.ch07.entity.User1;
import kr.co.ch07.repository.User1Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class User1Service {

    //생성자 주입
    private final User1Repository repository;

    public void insertUser1(User1DTO user1DTO){
        // DTO를 Entity로 변환
        User1 user1 = user1DTO.toEntity();

        // Entity 저장(데이터 베이스 Insert)
        repository.save(user1);
    }
    public User1DTO seleteUser1(String uid){
        /*
             Optional
                - null 값 검증 처리를 손쉽게 활용하는 구조체
                - Spring JPA 조회 결과 타입
         */
        //조회
        Optional<User1> result = repository.findById(uid);
        User1 user1 = result.get();
        
        // Entity를 DTO 로 변환
        return user1.toDTO();
    }
    public List<User1DTO> seleteUser1s(){
        List<User1> user1s = repository.findAll();

        // for(외부반복자)을 이용한 Entity 리스트를 DTO 리스트로 변환
        /*
        List<User1DTO> user1DTOs = new ArrayList<>();

        for(User1 user1 : user1s){
            user1DTOs.add(user1.toDTO());
        }
        */
        // 스트림(내부반복자)를 이용한 Entity 리스트를 DTO 리스트로 변환
        List<User1DTO> user1DTOs = user1s.stream()
                        .map(entity-> User1DTO.builder()
                        .uid(entity.getUid())
                        .name(entity.getName())
                        .birth(entity.getBirth())
                        .hp(entity.getHp())
                        .age(entity.getAge())
                        .build())
        .collect(Collectors.toList());

        return user1DTOs;
    }
    public void updateUser1(User1DTO user1DTO){
        // DTO를 Entity로 변환
        User1 user1 = user1DTO.toEntity();

        repository.save(user1);
    }
    public void deleteUser1(String uid){
        //Entity 삭제(데이터베이스 delete)
        repository.deleteById(uid);
    }

}
