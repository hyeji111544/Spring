package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.sboard.dto.TermsDTO;
import kr.co.sboard.dto.UserDTO;
import kr.co.sboard.config.AppInfo;
import kr.co.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final AppInfo appInfo;

    @GetMapping("/user/login")
    public String login(@ModelAttribute("success") String success, Model model){
        // 매개변수 success에 @ModelAttribute 선언으로 View 참조할 수 있음

        return "/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){

        TermsDTO termsDTO = userService.selectTerms();
        model.addAttribute("terms", termsDTO);

        return "/user/terms";
    }

    @GetMapping("/user/register")
    public String register(@ModelAttribute("sms") String sms){
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(HttpServletRequest req, UserDTO userDTO){

        String regip = req.getRemoteAddr();
        userDTO.setRegip(regip);

        log.info(userDTO.toString());

        userService.insertUser(userDTO);

        return "redirect:/user/login?success=200";
    }

    @ResponseBody
    @GetMapping("/user/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                       @PathVariable("type")  String type,
                                       @PathVariable("value") String value){

        int count = userService.selectCountUser(type, value);
        log.info("count : " + count);
        log.info("type : " + type);


        // 중복 없으면 이메일 인증코드 발송
        if(count == 0 && type.equals("email")){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);
    }


    @GetMapping("/user/{email}")
    public ResponseEntity<?> sendEmailForFindUser(HttpSession session,
                                                  @PathVariable("email") String email){

        try {
            userService.sendEmailCode(session, email);
            // 이메일 성공적으로 보내짐을 클라이언트에게 응답
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("success", true);
            return ResponseEntity.ok().body(resultMap);
        } catch (Exception e) {
            // 이메일 전송 실패시 클라이언트에게 오류 응답
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("success", false);
            errorMap.put("message", "이메일 전송에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }


    // 인증 코드 검사
    @ResponseBody
    @GetMapping("/email/{code}")
    public ResponseEntity<?> checkEmail(HttpSession session, @PathVariable("code")  String code){

        String sessionCode = (String) session.getAttribute("code");

        if(sessionCode.equals(code)){
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }
    }

    @GetMapping("/user/findId")
    public String findId(){
        return "/user/findId";
    }
    @PostMapping("/user/findId")
    public ResponseEntity<?> findId(@RequestBody UserDTO userDTO){
        String uid =userDTO.getUid();
        String email =userDTO.getEmail();
        return userService.findIdAndEmail(uid, email);

    }

    @GetMapping("/user/findIdResult")
    public String findIdResult(@PathVariable("uid") String uid, Model model){

        UserDTO userDTO = userService.selectUser(uid);
        model.addAttribute(userDTO);
        log.info(userDTO.toString());

        return "/user/findIdResult";
    }

    @GetMapping("/user/findPassword")
    public String findPassword(){
        return "/user/findPassword";
    }

    @GetMapping("/user/findPasswordChange")
    public String findPasswordChange(){
        return "/user/findPasswordChange";
    }



}