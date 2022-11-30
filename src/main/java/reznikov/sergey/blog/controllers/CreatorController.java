package reznikov.sergey.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import java.util.*;

@Controller
@RequestMapping("/creator")
public class CreatorController {

    int PAGE_SIZE = 5;

    PostRepository postRepository;
    PostImageRepository postImageRepository;

    public CreatorController(@Autowired PostRepository postRepository,
                             @Autowired PostImageRepository postImageRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
    }

    @GetMapping("/")
    ModelAndView creatorMainPage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                                 @AuthenticationPrincipal User curUser) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        modelAndView.setViewName("creator_home");
        int page = pageNumber.orElse(0);

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        var data = postRepository.findPostsByUser_IdOrderByDateDesc(curUser.getId(), pageable);
        List<Post> posts = data.toList();

        var postData = new ArrayList<ShowPost>();
        posts.forEach(rec -> postData.add(new ShowPost(rec.getTitle(),
                rec.getDescription(),
                rec.getId(),
                rec.getDate())));
        map.put("posts", postData);

        modelAndView.addAllObjects(map);
        return modelAndView;
    }


    @PostMapping("/work_on_post")
    ResponseEntity<Object> creatorNewPage(@RequestBody FullPost newPost,
                                          @AuthenticationPrincipal User curUser) {
        Post post = new Post();
        post.setDescription(newPost.getDescription());
        post.setText(newPost.getText());
        post.setUser(curUser);
        post.setTitle(newPost.getTitle());

        var createdPost = postRepository.save(post);
        newPost.getPostImages().forEach(rec -> {
            PostImage img = new PostImage();
            img.setImage(rec.getBytes());
            img.setPost(createdPost);
            postImageRepository.save(img);
        });
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/work_on_post")
    ModelAndView getCreatePage(@RequestParam(required = false) Integer id) {
        if (id == null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("post_work_window");
            return modelAndView;
        }

        FullPost fullPost = createByPost(postRepository.getReferenceById(id.longValue()));
        Map<String, Object> map = new HashMap<>();

        map.put("post", fullPost);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    private FullPost createByPost(Post post) {
        FullPost fullPost = new FullPost();
        fullPost.setDescription(post.getDescription());
        fullPost.setText(post.getText());
        fullPost.setTitle(post.getTitle());

        var postsInBase64 = new ArrayList<String>();
        for (var img : post.getPostImageSet()
        ) {
            postsInBase64.add("data:image/png;base64," +
                    Base64.getEncoder().encodeToString(img.getImage()));
        }
        fullPost.setPostImages(postsInBase64);
        return fullPost;
    }
}
