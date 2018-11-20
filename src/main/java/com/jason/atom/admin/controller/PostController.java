package com.jason.atom.admin.controller;

import com.jason.atom.admin.model.Post;
import com.jason.atom.admin.model.User;
import com.jason.atom.admin.repository.PostRepository;
import com.jason.atom.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    // create
    @GetMapping(value = "/cp")
    public String createPost(Model model) {
        model.addAttribute("post", new Post(null, null));
        return "admin/post/createPost";
    }

    @PostMapping("/cp")
    public String createPostSubmit(HttpServletRequest request) throws ParseException {
        User author = userRepository.findUserByName("jason"); // todo:改成從session裡面取
        Post post = new Post(request.getParameter("title"), request.getParameter("body"));
        post.setAuthor(author);
        String createTime = request.getParameter("createTime");
        post.setCreateTime(new SimpleDateFormat("yyyy/MM/dd").parse(createTime));
        post.setUpdateTime(new SimpleDateFormat("yyyy/MM/dd").parse(createTime));
        Post result = postRepository.save(post); // todo: 時間儲存到資料庫後，在資料庫會減一天
        if (result != null) {
            // 儲存成功
            return "redirect:/admin/lp";
        } else {
            // 儲存失敗
            // todo: 重新將寫錯的資料送給前端，怎麼做?
            return "redirect:/admin/cp";
        }
    }

    // read
    @GetMapping("/lp")
    public String listPosts(Model model) {
        List<Post> postList = postRepository.findAll();
        model.addAttribute("postList", postList);
        return "admin/post/listPosts";
    }

    // update
    @GetMapping("/ep/{postId}")
    public String editPost(@PathVariable Long postId, Model model) {
        Post post = postRepository.findById(postId).get();
        model.addAttribute("post", post);
        model.addAttribute("updateTime", new SimpleDateFormat("yyyy/MM/dd").format(post.getUpdateTime()));
        return "admin/post/editPost";
    }

    @PostMapping("/ep/{postId}")
    public String editPostSubmit(@PathVariable Long postId, HttpServletRequest request) throws ParseException {
        Post post = postRepository.findById(postId).get();
        post.setTitle(request.getParameter("title"));
        post.setUpdateTime(new SimpleDateFormat("yyyy/MM/dd").parse(request.getParameter("updateTime")));
        post.setBody(request.getParameter("body"));
        postRepository.save(post);
        return "redirect:/admin/lp";
    }

    // delete


    // editor image upload
    @PostMapping(value = "/image/upload")
    @ResponseBody
    public Map<String, Object> uploadImage(MultipartFile image, HttpServletRequest request) throws IOException {
        // 如果送過來的parameter名字跟方法參數的名字不一樣，則要使用@RequestPara("image") MultipartFile file
        Map<String, Object> response = new HashMap<>();
        if (null == image) {
            response.put("msg", "fail");
        } else {
            // image存在
            this.saveUploadedFile(image);
            response.put("msg", "success");
            response.put("url", request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/images/upload/" + image.getOriginalFilename());

        }
        return response;
    }

    // editor image delete
    @PostMapping("/image/delete")
    @ResponseBody
    public Map<String, Object> deleteImage(HttpServletRequest request, String filename) throws IOException {
        Map<String, Object> response = new HashMap<>();
        if (null == filename) {
            response.put("msg", "fail");
        } else {
            // 欲刪除的檔名存在
            String imagePathStr = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "static/images/upload/" + filename, "utf-8"); // 有中文字時，先解碼
            Files.delete(Paths.get(imagePathStr));
            response.put("msg", "success");
        }
        return response;
    }

    //save file
    private void saveUploadedFile(MultipartFile file) throws IOException {

        String classpath = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
        File uploadDir = new File(classpath, "/static/images/upload/");
        if (!uploadDir.exists()) {
            // 如果資料夾不存在
            uploadDir.mkdirs(); // todo: 修改成Files，設定資料夾權限
        }

        Files.write(Paths.get(uploadDir.getAbsolutePath() + "/" + file.getOriginalFilename()), file.getBytes());
    }

}
