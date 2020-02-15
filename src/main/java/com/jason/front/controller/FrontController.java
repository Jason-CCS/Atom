package com.jason.front.controller;

import com.jason.admin.domain.Post;
import com.jason.admin.repository.PostRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FrontController {
    @Autowired
    PostRepository postRepository;

    @RequestMapping("/")
    public String index(Model model) {
        List<Post> postList = postRepository.findTop5ByOrderByUpdateTimeDesc();
        for (Post p : postList) {
            // 將文章內容的html標籤全部移除，參考https://stackoverflow.com/questions/240546/remove-html-tags-from-a-string
            p.setBody(Jsoup.parse(p.getBody()).text());
        }
        model.addAttribute("postList", postList);
        return "front/index";
    }

    @RequestMapping("/about")
    public String about() {
        return "front/about";
    }

    @RequestMapping("/posts")
    public String post() {
        return "front/posts";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "front/contact";
    }
}
