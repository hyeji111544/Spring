package kr.co.ch10.service;

import kr.co.ch10.dto.UserDTO;
import kr.co.ch10.entity.User;
import kr.co.ch10.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void insertUser(UserDTO userDTO){
        // 비밀번호 평문을 시큐리티 인코더로 암호화
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        User user = userDTO.toEntity();
        repository.save(user);
    }

    public UserDTO selectUser(String uid){
        User user = repository.findById(uid).get();
        return user.toDTO();
    }

    public List<UserDTO> selectUsers(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(entity -> UserDTO.builder()
                        .uid(entity.getUid())
                        .age(entity.getAge())
                        .hp(entity.getHp())
                        .name(entity.getName())
                        .regDate(entity.getRegDate())
                        .role(entity.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(UserDTO userDTO){
        //데이터 수정
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        repository.save(userDTO.toEntity());

        //수정한 사용자 조회/반환
        Optional<User> result = repository.findById(userDTO.getUid());
        return result.get().toDTO();
    }

    public ResponseEntity deleteUser(String uid){
        // 삭제 전 삭제할 사용자 조회
        Optional<User> optUser = repository.findById(uid);

        if(optUser.isPresent()){
            // 사용자가 존재하면 삭제 후 삭제한 사용자 정보 ResponseEntity로 반환
            repository.deleteById(uid);
            return ResponseEntity
                    .ok()
                    .body(optUser.get());
        }else{
            // 사용자가 존재하지 않으면 NOT_FOUND 응답데이터와 user not found 메세지
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("user not found");
        }

    }
}
