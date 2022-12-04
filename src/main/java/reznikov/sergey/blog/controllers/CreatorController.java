package reznikov.sergey.blog.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.services.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/creator")
public class CreatorController {

    int PAGE_SIZE = 5;
    PostService postService;
    MappingUser mappingUser;

    public CreatorController(PostService postService, MappingUser mappingUser) {
        this.postService = postService;
        this.mappingUser = mappingUser;
    }

    @GetMapping
    String creatorMainPage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                           @AuthenticationPrincipal User user,
                           HashMap<String, Object> model) {

        int page = pageNumber.orElse(0);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<PostDTO> posts = postService.findPostsByUser(mappingUser.mapToUserDto(user), pageable);
        model.put("posts", posts);
        model.put("page", page);
        model.put("user", mappingUser.mapToUserDto(user));

        return "creator/creator_home_page";
    }
//
//
//    @DeleteMapping("/delete_post")
//    ResponseEntity<Object> delete_post(@RequestParam("postid") Long postId,
//                                       @RequestParam("userid") Long userId) {
//        Post post = postRepository.findPostById(postId).orElse(null);
//        if (post == null) {
//            return ResponseEntity.badRequest().body("Пост с таким id не найден или у вас нет прав доступа");
//        } else {
//            postRepository.delete(post);
//            return ResponseEntity.ok("Пост удалён");
//        }
//    }
//
//
//    @PostMapping("/save_post")
//    ResponseEntity<Object> creatorNewPage(@RequestBody FullPost fullPost) {
//        User user = userRepository.findById(fullPost.getUserId()).orElse(null);
//        if (user == null) {
//            return ResponseEntity.badRequest().body("Создатель с таким id не найден в базе");
//        }
//        Post post;
//        if (fullPost.getPostId() == null) {
//            post = new Post();
//        } else {
//            post = postRepository.findPostById(fullPost.getPostId()).orElse(null);
//            if (post == null) {
//                return ResponseEntity.badRequest().body("Пост с заданным id не найден");
//            }
//        }
//
//        post.setDescription(fullPost.getDescription());
//        post.setText(fullPost.getText());
//        post.setUser(user);
//        post.setTitle(fullPost.getTitle());
//        post = postRepository.save(post);
//
//        for (var image : fullPost.getPostImages()
//        ) {
//            PostImage postImage = new PostImage();
//            postImage.setPost(post);
//            postImage.setImage(image.getBytes());
//            postImageRepository.save(postImage);
//        }
//
//        return ResponseEntity.ok("Изменения сохранены в базе данных");
//    }
//
//
//    @GetMapping("/work_on_post")
//    ModelAndView getCreatePage(@RequestParam(required = false) Long postId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("post_work_window");
//        if (postId == null) {
//            return modelAndView;
//        }
//
//        FullPost fullPost = createByPost(postRepository.getReferenceById(postId));
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("post", fullPost);
//        modelAndView.addAllObjects(map);
//        return modelAndView;
//    }
//
//    private FullPost createByPost(Post post) {
//        FullPost fullPost = new FullPost();
//        fullPost.setDescription(post.getDescription());
//        fullPost.setText(post.getText());
//        fullPost.setTitle(post.getTitle());
//
//        return fullPost;
//    }
}
