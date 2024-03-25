package kr.co.sboard.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.entity.User;
import kr.co.sboard.mapper.UserMapper;
import kr.co.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    // JavaMailSender 주입
    private final JavaMailSender javaMailSender;

    public TermsDTO selectTerms(){
        return userMapper.selectTerms();
    }

    public void insertUser(UserDTO userDTO){

        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        userMapper.insertUser(userDTO);
    }

    public int selectCountUser(String type, String value) {
        return userMapper.selectCountUser(type, value);
    }

    public UserDTO selectUser(String uid){
        Optional<User> optUser = userRepository.findById(uid);
        log.info("findById ...1 "+ optUser);

        UserDTO userDTO = null;
        if (optUser.isPresent()){
            User user = optUser.get();
            log.info("findById ...2 " + user.toString());

            userDTO = modelMapper.map(user, UserDTO.class);
            log.info("findById ...3 " + userDTO);
        }

        return userDTO;

    }

    public ResponseEntity<?> findIdAndEmail(String uid, String email){
        log.info("uid :" + uid);
        log.info("email :" + email);
        Optional<User> optUser = userRepository.findUserByUidAndEmail(uid, email);

        log.info("findUser..." + optUser);

        if (optUser.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(optUser);
        }else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }

    /*
        - build.gradle 파일에 spring-boot-starter-mail 의존성 추가 할것
        - application.yml 파일 spring email 설정 추가
     */
    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmailCode(HttpSession session, String receiver){
        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));

        String title = "sboard 인증코드입니다.";
        String content = "<h1>인증코드는" + code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);
        }catch(Exception e){
            log.error("sendEmailConde : " + e.getMessage());
        }
    }

}