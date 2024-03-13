package kr.co.ch07.service;

import kr.co.ch07.dto.User5DTO;
import kr.co.ch07.entity.User5;
import kr.co.ch07.repository.User5Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class User5Service {

    private final User5Repository repository;

    public void insertUser5(User5DTO user5DTO){
         User5 user5 = user5DTO.toEntity();

         repository.save(user5);
    }

    public User5DTO selectUser5(int seq){
        User5 user5 = repository.findById(String.valueOf(seq)).get();
        return user5.toDTO();
    }

    public List<User5DTO> selectUser5s(){
        List<User5> user5s = repository.findAll();

        List<User5DTO> user5DTOs = user5s.stream()
                .map(entity-> User5DTO.builder()
                        .seq(entity.getSeq())
                        .name(entity.getName())
                        .gender(entity.getGender())
                        .age(entity.getAge())
                        .addr(entity.getAddr())
                .build())
                .collect(Collectors.toList());

        return user5DTOs;
    }

    public void updateUser5(User5DTO user5DTO){
        User5 user5 = user5DTO.toEntity();

        repository.save(user5);

    }

    public void deleteUser5(int seq){

        repository.deleteById(String.valueOf(seq));

    }

}
