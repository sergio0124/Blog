package reznikov.sergey.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reznikov.sergey.blog.DTO.*;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.ReportType;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.services.*;

import java.util.*;

@PreAuthorize("hasAnyAuthority('USER')")
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserPostController {
    int PAGE_SIZE = 5;
    int COMMENTS_PER_PAGE = 10;

    private final UserService userService;
    private final MappingUser mappingUser;
    private final SubscribeService subscribeService;
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final PostImageService postImageService;

    private final ReportService reportService;

    @GetMapping
    String getHomePage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                       @AuthenticationPrincipal User user,
                       HashMap<String, Object> model) throws Exception {

        int page = pageNumber.orElse(0);
        PageRequest pageRequest =
                PageRequest.of(page, PAGE_SIZE, Sort.by("date").descending());

        List<SubscribeDTO> subscribeDTOList = subscribeService.getUsersSubscribes(mappingUser.mapToUserDto(user));

        List<PostDTO> posts = postService
                .getPostsBySubscribesByPages(subscribeDTOList, pageRequest);

        model.put("posts", posts);
        model.put("user", mappingUser.mapToUserDto(user));
        model.put("page", page);
        return "user/user_home_page";
    }


    @GetMapping("/search")
    String getSearchPage(@RequestParam("searchRequest") String searchRequest,
                         @RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                         HashMap<String, Object> model) {

        int page = pageNumber.orElse(0);
        PageRequest pageRequest =
                PageRequest.of(page, PAGE_SIZE);

        List<PostDTO> posts = postService.searchPostsByTitle(searchRequest, pageRequest);

        model.put("posts", posts);
        return "user/user_search_page";
    }


    @GetMapping("/read_post")
    String readPost(@RequestParam("postId") Long postId,
                    @AuthenticationPrincipal User user,
                    HashMap<String, Object> model) throws Exception {

        UserDTO userDTO = mappingUser.mapToUserDto(user);
        PostDTO postDTO = postService.findPostById(postId);
        if (postDTO == null) {
            return "user/read_post";
        }

        postDTO.setLikesCount(Math.toIntExact(likeService.getPostLikesCount(postDTO)));
        postDTO.setPostImages(postImageService.findPostImagesByPost(postDTO));

        PageRequest pageRequest =
                PageRequest.of(0, COMMENTS_PER_PAGE);
        var comments = commentService.getCommentsByPostByPage(postDTO, pageRequest);
        postDTO.setComments(comments);

        model.put("liked", likeService.isPostLiked(postDTO, userDTO));
        model.put("user", mappingUser.mapToUserDto(user));
        model.put("post", postDTO);
        return "user/read_post";
    }


    @PostMapping("/load_comments")
    ResponseEntity<Object> getMoreComments(@RequestBody Integer page,
                                           @RequestBody Long postId) throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        PageRequest pageRequest =
                PageRequest.of(page, COMMENTS_PER_PAGE);
        var comments = commentService.getCommentsByPostByPage(postDTO, pageRequest);
        return ResponseEntity.ok(comments);
    }


    @PostMapping("/like")
    ResponseEntity<Object> updateLike(
            @RequestBody PostDTO postDTO,
            @AuthenticationPrincipal User user) {
        try {
            likeService.likeOrUnlike(postDTO, user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Выполнено успешно");
    }


    @GetMapping("check_creator")
    String getUserPage(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
            @AuthenticationPrincipal User user,
            HashMap<String, Object> model
    ) throws Exception {
        int page = pageNumber.orElse(0);
        PageRequest pageRequest =
                PageRequest.of(page, PAGE_SIZE);
        UserDTO userDTO = userService.loadUserById(userId);
        userDTO.setId(userId);

        List<PostDTO> posts = postService.findPostsByUser(userDTO, pageRequest);

        model.put("posts", posts);
        model.put("user", userDTO);
        model.put("page", page);
        model.put("subscribed", subscribeService.isUserSubscribedTo(user, userDTO));
        return "user/check_user";
    }


    @PostMapping("/subscribe")
    ResponseEntity<Object> subscribe(@RequestBody UserDTO userDTO,
                                     @AuthenticationPrincipal User user) {

        try {
            subscribeService.subscribeOrUnsubscribe(userDTO, user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Выполнено успешно");
    }


    @PostMapping("/save_comment")
    ResponseEntity<Object> saveComment(@AuthenticationPrincipal User user,
                                       @RequestBody CommentDTO commentDTO) {

        commentDTO.setUser(mappingUser.mapToUserDto(user));
        try {
            commentService.saveComment(commentDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Комментарий сохранен");

    }


    @PostMapping("/delete_comment")
    ResponseEntity<Object> deleteComment(@AuthenticationPrincipal User user,
                                         @RequestBody CommentDTO commentDTO) {

        commentDTO.setUser(mappingUser.mapToUserDto(user));
        try {
            commentService.deleteComment(commentDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Комментарий удален");

    }


    @GetMapping("/report_post/{postId}")
    private String getRepostPage(@PathVariable Long postId,
                                 Map<String, Object> model) {
        PostDTO postDTO = postService.findPostById(postId);
        if (postDTO == null) {
            model.put("message", "Пост не найден");
        } else {
            model.put("post", postDTO);
            List<String> reportTypes = Arrays
                    .stream(ReportType.values())
                    .map(ReportType::getInRussian)
                    .toList();
            model.put("reportTypes", reportTypes);
        }
        return "user/report_post";
    }


    @PostMapping("/report_post/{postId}")
    private ResponseEntity<Object> saveReport(@RequestBody ReportDTO reportDTO,
                                              @AuthenticationPrincipal User user,
                                              @PathVariable Long postId) {
        reportDTO.setPost(postService.findPostById(postId));
        reportDTO.setUser(mappingUser.mapToUserDto(user));
        if (reportDTO.getPost() == null) {
            return ResponseEntity.badRequest().body("Не найден пост");
        }
        reportService.saveReport(reportDTO);
        return ResponseEntity.ok("Репорт создан");
    }
}
