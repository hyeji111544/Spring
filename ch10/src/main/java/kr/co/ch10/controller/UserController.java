package kr.co.ch10.controller;

import kr.co.ch10.dto.UserDTO;
import kr.co.ch10.entity.User;
import kr.co.ch10.jwt.JwtProvider;
import kr.co.ch10.security.MyUserDetails;
import kr.co.ch10.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody UserDTO userDTO){
        log.info("log...1 :" + userDTO.toString());

        try {
            // Security 인증 처리
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(userDTO.getUid(), userDTO.getPass());

            // 사용자 DB 조회
            Authentication authentication = authenticationManager.authenticate(authToken);
            log.info("log...2");

            // 인증된 사용자 가져오기
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            // 토큰 발급(엑세스, 리프레쉬)
            String access = jwtProvider.createToken(user, 1);  // 1일
            String refresh = jwtProvider.createToken(user, 7); // 7일

            // 리프레쉬 토큰 DB 저장

            // 엑세스 토큰 클라이언트 전송
            Map<String, Object> map = new HashMap<>();
            map.put("grantType", "Bearer");
            map.put("access", access);
            return ResponseEntity.ok().body(map);

        }catch (Exception e){
            log.info("log...3 :" + e.getMessage());
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }

    private final UserService userService;

    @GetMapping("/user/{uid}")
    public UserDTO user(@PathVariable("uid") String uid){
        log.info("UserController user...");
        UserDTO userDTO = userService.selectUser(uid);
        return userDTO;
    }

    ///@PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('MANAGER') or hasAnyAuthority('ADMIN')")
    @GetMapping("/user")
    public List<UserDTO> list(){
        List<UserDTO> users = userService.selectUsers();
        return users;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){

        userService.insertUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> modify(@RequestBody UserDTO userDTO){

        UserDTO user = userService.updateUser(userDTO);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(user);
    }

    @DeleteMapping("/user/{uid}")
    public ResponseEntity delete(@PathVariable("uid") String uid){
        return userService.deleteUser(uid);
    }

}
