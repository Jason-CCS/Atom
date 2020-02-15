package com.jason.admin.controller;

import com.jason.admin.repository.UserRepository;
import com.jason.admin.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserRepository userRepository;

    // create
    @GetMapping(value = "/cu")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/createUser";
    }

    @PostMapping("/cu")
    public String createUserSubmit(@ModelAttribute User user) {
        User result = userRepository.save(user);
        if (result != null) {
            return "redirect:/admin/lu";
        } else {
            // todo: 錯誤時將表單資料送回
            return "redirect:/admin/cu";
        }
    }

    // read
    @GetMapping("/lu")
    public String listUsers(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "admin/user/listUsers";
    }

    // update

    // delete
}
