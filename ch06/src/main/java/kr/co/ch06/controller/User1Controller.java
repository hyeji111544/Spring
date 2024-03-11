package kr.co.ch06.controller;

import kr.co.ch06.dto.User1DTO;
import kr.co.ch06.service.User1Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.rmi.registry.Registry;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class User1Controller {

    private final User1Service service;

    @GetMapping("/user1/list")
    public String list(Model model){
        List<User1DTO> users = service.selectUser1s();
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

        return "redirect:/user1/list";
    }

    @GetMapping("/user1/modify")
    public String modify(Model model, String uid){
        User1DTO user1DTO = service.selectUser1(uid);
        model.addAttribute(user1DTO);

        return "/user1/modify";
    }

    @PostMapping("/user1/modify")
    public String modify(User1DTO user1DTO){
        service.updateUser1(user1DTO);

        return "redirect:/user1/list";
    }

    @GetMapping("/user1/delete")
    public String delete(String uid){
        service.deleteUser1(uid);

        return "redirect:/user1/list";
    }
}
