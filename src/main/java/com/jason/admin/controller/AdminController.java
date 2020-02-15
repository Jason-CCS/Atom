package com.jason.admin.controller;

import com.jason.admin.service.AuthorityService;
import com.jason.admin.service.UserService;
import com.jason.admin.domain.Authority;
import com.jason.admin.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Long ROLE_ADMIN_AUTHORITY_ID = 1L;
    private static final Long ROLE_USER_AUTHORITY_ID = 2L;

    @Autowired
    UserService userService;
    @Autowired
    AuthorityService authorityService;

    @RequestMapping
    public String index() {
        return "admin/index";
    }

//    @RequestMapping("/login")
//    public String login() {
//        return "admin/user/login";
//    }

    @PostMapping("/register")
    public String registerUser(User user){
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID).get());// todo: 如何處理這個get，Lambda?
        user.setAuthorities(authorities);
        userService.registerUser(user);
        return "redirect:/login";
    }
}
