package kr.co.ch08.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.ch08.dto.UserDTO;
import kr.co.ch08.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class User2Controller {

    private final UserService service;

    @GetMapping("/user2/login")
    public String login(){
        return "/user2/login";
    }

    @PostMapping("/user2/login")
    public String login(HttpSession session, UserDTO userDTO){
        UserDTO user = service.selectUser(userDTO);
        log.info(user.toString());

        if(user != null){
            // 회원이 맞을 경우
            log.info(user.toString());
            session.setAttribute("sessUser", user);
            return "redirect:/user2/success";
        }else{
            // 회원이 아닐경우
            return "redirect:/user2/login?success=100";
        }
    }

    @GetMapping("/user2/success")
    public String success(HttpSession session, HttpServletResponse resp){
        UserDTO sessUser = (UserDTO) session.getAttribute("sessUser");

        // 쿠키생성
        Cookie cookie = new Cookie("username", sessUser.getUid());
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        resp.addCookie(cookie);

        return "/user2/success";
    }

    @GetMapping("/user2/result")
    public String result(@CookieValue("JSESSIONID") String jsessionid,
                         @CookieValue("username") String username,
                         Model model){
        //@CookieValue HTTP 쿠키의 값을 받아오는 어노테이션

        log.info(""+username);
        model.addAttribute("jsessionid", jsessionid);
        model.addAttribute("username", username);

        return "/user2/result";
    }

    @GetMapping("/user2/logout")
    public String logout(HttpSession session){
        session.removeAttribute("sessUser");
        session.invalidate();

        return "redirect:/user2/login?success=200";
    }
}
