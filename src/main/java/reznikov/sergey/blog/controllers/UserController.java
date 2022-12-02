package reznikov.sergey.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.controllers.request_bodies.FullPost;
import reznikov.sergey.blog.controllers.request_bodies.ShowPost;
import reznikov.sergey.blog.controllers.request_bodies.ShowPostComment;
import reznikov.sergey.blog.entities.*;
import reznikov.sergey.blog.entities.enums.ReportType;
import reznikov.sergey.blog.repositories.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    int PAGE_SIZE = 5;
    int COMMENTS_PER_PAGE = 10;

    UserRepository userRepository;
    SubscribeRepository subscribeRepository;
    PostRepository postRepository;
    LikeRepository likeRepository;
    CommentRepository commentRepository;
    ReportRepository reportRepository;


    public UserController(@Autowired UserRepository userRepository,
                          @Autowired SubscribeRepository subscribeRepository,
                          @Autowired PostRepository postRepository,
                          @Autowired LikeRepository likeRepository,
                          @Autowired CommentRepository commentRepository,
                          @Autowired ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.subscribeRepository = subscribeRepository;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.reportRepository = reportRepository;
    }


    @GetMapping("/")
    ModelAndView getHomePage(@RequestParam("userid") Long userId,
                             @RequestParam(value = "page", required = false) Optional<Integer> pageNumber) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        modelAndView.setViewName("user_home_page");

        User user = userRepository.findUserById(userId).orElse(null);
        int page = pageNumber.orElse(0);
        PageRequest pageRequest =
                PageRequest.of(page, PAGE_SIZE, Sort.by("date").descending());
        var subscribes = subscribeRepository.findSubscribesByUser(user);

        var posts = postRepository.findPostByUserIn(
                subscribes
                        .stream()
                        .map(Subscribe::getInfluencer)
                        .collect(Collectors.toList()),
                pageRequest);

        map.put("posts", posts.stream().map(this::getShowPost).collect(Collectors.toList()));
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    private ShowPost getShowPost(Post post) {
        return new ShowPost(post.getUser().getUsername(),
                post.getTitle(),
                post.getDescription(),
                post.getId(),
                post.getDate());
    }


    @GetMapping("/search")
    ModelAndView getSearchPage(@RequestParam("string") String searchRequest,
                               @RequestParam(value = "page", required = false) Optional<Integer> pageNumber) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        modelAndView.setViewName("user_search_page");

        int page = pageNumber.orElse(0);
        PageRequest pageRequest =
                PageRequest.of(page, PAGE_SIZE);
        var posts = postRepository.findPostsByTitleContainingIgnoreCase(searchRequest, pageRequest).toList();

        map.put("posts", posts.stream().map(this::getShowPost).collect(Collectors.toList()));
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    @PostMapping("/subscribe")
    ResponseEntity<Object> subscribe(HttpServletRequest request) {

        User subscriber = userRepository
                .findUserById(Long.valueOf(request.getParameter("subscriberid")))
                .orElse(null);
        User influencer = userRepository
                .findUserById(Long.valueOf(request.getParameter("influencerid")))
                .orElse(null);

        if (subscriber == null || influencer == null) {
            return ResponseEntity.badRequest().body("Один из пользователей не найден");
        }

        Subscribe subscribe = new Subscribe();
        subscribe.setSubscriber(subscriber);
        subscribe.setInfluencer(influencer);

        subscribeRepository.save(subscribe);
        return ResponseEntity.ok("Подписка оформлена");
    }

    @GetMapping("/read_post")
    ModelAndView readPost(@RequestParam("postid") Long postId) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        modelAndView.setViewName("user_search_page");

        if (postId == null) {
            return modelAndView;
        }

        Post post = postRepository.findPostById(postId).orElse(null);
        if (post == null) {
            return modelAndView;
        }
        FullPost fullPost = createByPost(post);
        fullPost.setLikesCount(likeRepository.countByPost(post));
        map.put("post", fullPost);

        PageRequest pageRequest =
                PageRequest.of(0, COMMENTS_PER_PAGE);
        var comments = commentRepository.findCommentsByPost(post, pageRequest).toList();
        map.put("comments", comments
                .stream()
                .map(rec -> new ShowPostComment(rec.getUser().getUsername(), rec.getText()))
                .collect(Collectors.toList())
        );

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


    @PostMapping("/create_comment")
    ResponseEntity<Object> saveComment(HttpServletRequest request) {

        User user = userRepository
                .findUserById(Long.valueOf(request.getParameter("userid")))
                .orElse(null);
        Post post = postRepository
                .findPostById(Long.valueOf(request.getParameter("postid")))
                .orElse(null);
        String text = request.getParameter("text");

        if (user == null || post == null) {
            return ResponseEntity.badRequest().body("Произошла ошибка в получении данных");
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(text);

        commentRepository.save(comment);
        return ResponseEntity.ok("Комментарий сохранен");

    }


    @PostMapping("/create_report")
    ResponseEntity<Object> saveReport(HttpServletRequest request) {

        User user = userRepository
                .findUserById(Long.valueOf(request.getParameter("user_id")))
                .orElse(null);
        Post post = postRepository
                .findPostById(Long.valueOf(request.getParameter("post_id")))
                .orElse(null);
        String text = request.getParameter("repost_text");

        if (user == null || post == null) {
            return ResponseEntity.badRequest().body("Произошла ошибка в получении данных");
        }

        Report report = new Report();
        report.setUser(user);
        report.setPost(post);
        report.setReportType(ReportType.fromRussian(text));

        reportRepository.save(report);
        return ResponseEntity.ok("Репорт сохранен");

    }
}
