package reznikov.sergey.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.ReportType;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.services.PostImageService;
import reznikov.sergey.blog.services.PostService;
import reznikov.sergey.blog.services.ReportService;
import reznikov.sergey.blog.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private static final int PAGE_SIZE = 10;

    private final MappingUser mappingUser;
    private final UserService userService;
    private final PostImageService postImageService;
    private final PostService postService;
    private final ReportService reportService;

    @GetMapping("/admin/")
    private String getHomePage(Map<String, Object> model,
                               @RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                               @AuthenticationPrincipal User user) {
        int page = pageNumber.orElse(0);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<PostDTO> posts = postService.getMostReportedPosts(pageable);
        model.put("posts", posts);
        model.put("page", page);
        model.put("user", mappingUser.mapToUserDto(user));
        return "admin_home_page";
    }

    @GetMapping("/admin/check_post")
    private String getCheckPostImage(Map<String, Object> model,
                                     @RequestParam Long postId,
                                     @AuthenticationPrincipal User user) {
        PostDTO postDTO = postService.findPostById(postId);
        if (postDTO == null) {
            model.put("message", "Пост с таким id не найден");
            return "check_post";
        }
        postDTO.setReportCount(reportService.getReportCount(postId));
        postDTO.setReportType(ReportType.getInRussian(reportService.getReportType(postId)));
        postDTO.setPostImages(postImageService.findPostImagesByPost(postDTO));
        model.put("post", postDTO);
        model.put("user", mappingUser.mapToUserDto(user));
        return "check_post";
    }


    @PostMapping("/admin/delete_post/{postId}")
    private ResponseEntity<Object> deletePost(@PathVariable Long postId,
                                              @AuthenticationPrincipal User user) {
        PostDTO postDTO = postService.findPostById(postId);
        if (postDTO == null) {
            return ResponseEntity.badRequest().body("Пост с таким id не найден");
        }
        try {
            postService.deletePost(postDTO, mappingUser.mapToUserDto(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Удаление прошло успешно");
    }


    @PostMapping("/admin/justify_post/{postId}")
    private ResponseEntity<Object> justifyPost(@PathVariable Long postId) {
        if (postService.findPostById(postId) == null) {
            return ResponseEntity.badRequest().body("Пост с таким id не найден");
        }
        reportService.deleteReportsByPostId(postId);
        return ResponseEntity.ok("Изменения внесены");
    }


    @PostMapping("/admin/block_user/{postId}")
    private ResponseEntity<Object> blockUser(@PathVariable Long postId) {
        PostDTO postDTO = postService.findPostById(postId);
        if (postDTO == null) {
            return ResponseEntity.badRequest().body("Пост с таким id не найден");
        }
        userService.blockUser(postDTO.getUser());
        return ResponseEntity.ok("Изменения внесены");
    }
}
