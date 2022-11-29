package reznikov.sergey.blog.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.repositories.PostRepository;

import java.awt.image.BufferedImage;
import java.util.*;

@Controller
@RequestMapping("/creator")
public class CreatorController {

    int PAGE_SIZE = 5;

    PostRepository postRepository;

    public CreatorController(@Autowired PostRepository postRepository) {
        this.postRepository = postRepository;
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

        @Data
        class ShowPost {
            final String title;
            final String description;
            final Long id;
            final Date date;

        }

        var postData = new ArrayList<ShowPost>();
        posts.forEach(rec -> postData.add(new ShowPost(rec.getTitle(),
                rec.getDescription(),
                rec.getId(),
                rec.getDate())));

        map.put("posts", postData);

        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    @GetMapping("/new")
    ModelAndView creatorNewPage(){

    }
}
