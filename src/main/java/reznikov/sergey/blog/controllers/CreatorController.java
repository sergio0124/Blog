package reznikov.sergey.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.controllers.request_bodies.FullPost;
import reznikov.sergey.blog.controllers.request_bodies.ShowPost;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.PostImage;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.repositories.PostImageRepository;
import reznikov.sergey.blog.repositories.PostRepository;
import reznikov.sergey.blog.repositories.UserRepository;

import java.util.*;

@Controller
@RequestMapping("/creator")
public class CreatorController {

    int PAGE_SIZE = 5;

    PostRepository postRepository;
    PostImageRepository postImageRepository;
    UserRepository userRepository;

    public CreatorController(@Autowired PostRepository postRepository,
                             @Autowired PostImageRepository postImageRepository,
                             @Autowired UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    ModelAndView creatorMainPage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                                 @RequestParam(value = "userid") Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        modelAndView.setViewName("creator_home_page");

        int page = pageNumber.orElse(0);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        var data = postRepository.findPostsByUser_IdOrderByDateDesc(userId, pageable);
        List<Post> posts = data.toList();

        var postData = new ArrayList<ShowPost>();
        posts.forEach(rec -> postData.add(new ShowPost(null, rec.getTitle(),
                rec.getDescription(),
                rec.getId(),
                rec.getDate())));
        map.put("posts", postData);

        modelAndView.addAllObjects(map);
        return modelAndView;
    }


    @DeleteMapping("/delete_post")
    ResponseEntity<Object> delete_post(@RequestParam("postid") Long postId,
                                       @RequestParam("userid") Long userId) {
        Post post = postRepository.findPostById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.badRequest().body("Пост с таким id не найден или у вас нет прав доступа");
        } else {
            postRepository.delete(post);
            return ResponseEntity.ok("Пост удалён");
        }
    }


    @PostMapping("/save_post")
    ResponseEntity<Object> creatorNewPage(@RequestBody FullPost fullPost) {
        User user = userRepository.findById(fullPost.getUserId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Создатель с таким id не найден в базе");
        }
        Post post;
        if (fullPost.getPostId() == null) {
            post = new Post();
        } else {
            post = postRepository.findPostById(fullPost.getPostId()).orElse(null);
            if (post == null) {
                return ResponseEntity.badRequest().body("Пост с заданным id не найден");
            }
        }

        post.setDescription(fullPost.getDescription());
        post.setText(fullPost.getText());
        post.setUser(user);
        post.setTitle(fullPost.getTitle());
        post = postRepository.save(post);

        for (var image:fullPost.getPostImages()
             ) {
            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setImage(image.getBytes());
            postImageRepository.save(postImage);
        }

        return ResponseEntity.ok("Изменения сохранены в базе данных");
    }



    @GetMapping("/work_on_post")
    ModelAndView getCreatePage(@RequestParam(required = false) Long postId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post_work_window");
        if (postId == null) {
            return modelAndView;
        }

        FullPost fullPost = createByPost(postRepository.getReferenceById(postId));
        Map<String, Object> map = new HashMap<>();

        map.put("post", fullPost);
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    private FullPost createByPost(Post post) {
        FullPost fullPost = new FullPost();
        fullPost.setDescription(post.getDescription());
        fullPost.setText(post.getText());
        fullPost.setTitle(post.getTitle());

        return fullPost;
    }
}
