package reznikov.sergey.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.PostImageDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.services.PostImageService;
import reznikov.sergey.blog.services.PostService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CreatorPostController {

    private static final int PAGE_SIZE = 5;
    private final PostService postService;
    private final MappingUser mappingUser;
    private final PostImageService postImageService;


    @GetMapping("creator/")
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


    @GetMapping("creator/work_on_post")
    String getCreatePage(@RequestParam(required = false) Long postId,
                         HashMap<String, Object> model,
                         @AuthenticationPrincipal User user) {
        if (postId == null) {
            return "creator/post_work_window";
        }

        PostDTO postDTO = postService.findPostById(postId);
        if (postDTO == null) {
            return "creator/post_work_window";
        }

        postDTO.setPostImages(postImageService.findPostImagesByPost(postDTO));
        model.put("post", postDTO);
        model.put("user", mappingUser.mapToUserDto(user));

        return "creator/post_work_window";
    }


    @PostMapping("creator/save_post")
    ResponseEntity<Object> creatorNewPage(@RequestBody PostDTO postDTO,
                                          @AuthenticationPrincipal User user) {
        postDTO.setUser(mappingUser.mapToUserDto(user));
        List<PostImageDTO> images = postDTO.getPostImages();
        try {
            postDTO = postService.savePost(postDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        PostDTO finalPostDTO = postDTO;
        images.forEach(rec -> rec.setPost(finalPostDTO));
        try {
            postImageService.savePostImagesByPost(images, postDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Изменения сохранены в базе данных");
    }


    @DeleteMapping("creator/delete_post")
    ResponseEntity<Object> delete_post(@AuthenticationPrincipal User user,
                                       @RequestBody PostDTO postDTO) {
        UserDTO userDTO = mappingUser.mapToUserDto(user);
        try {
            postService.deletePost(postDTO, userDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Удаление прошло успешно");
    }

}
