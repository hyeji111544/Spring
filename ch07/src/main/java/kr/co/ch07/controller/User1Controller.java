package kr.co.ch07.controller;

import kr.co.ch07.dto.User1DTO;
import kr.co.ch07.entity.User1;
import kr.co.ch07.service.User1Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class User1Controller {

    private final User1Service service;

    @GetMapping("/user1/list")
    public String list(Model model){
        List<User1DTO> users = service.seleteUser1s();
        model.addAttribute("users", users);
        return "/user1/list";
    }

    @GetMapping("/user1/register")
    public String register(){
        return "/user1/register";
    }

    @PostMapping("/user1/register")
    public String register(User1DTO user1DTO){
        service.insertUser1(user1DTO);
        log.info(user1DTO.toString());
        return "redirect:/user1/list";
    }

    @GetMapping("/user1/modify")
    public String modify(String uid, Model model){
        User1DTO user1DTO = service.seleteUser1(uid);
        model.addAttribute(user1DTO);
        log.info(user1DTO.toString());
        return "/user1/modify";
    }

    @PostMapping("/user1/modify")
    public String modify(User1DTO user1DTO){
        service.updateUser1(user1DTO);
        log.info(user1DTO.toString());
        return "redirect:/user1/list";
    }

    @GetMapping("/user1/delete")
    public String modify(String uid){
        service.deleteUser1(uid);
        log.info(uid);
        return "redirect:/user1/list";
    }
}
