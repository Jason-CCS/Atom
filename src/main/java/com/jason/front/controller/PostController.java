package com.jason.front.controller;

import com.jason.admin.domain.Post;
import com.jason.admin.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller(value = "front") // "front" string should be set for name conflict purpose
public class PostController {
    @Autowired
    PostRepository postRepository;

    @GetMapping("/post/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        Post post = postRepository.getOne(postId);
        if (null != post) {
            model.addAttribute("post", post);
        }
        return "front/post";
    }
}
