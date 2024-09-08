package com.example.featserver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsRepository postsRepository;

    @PostMapping("/load/posts/date")
    public List<Posts> loadPostsDate(@RequestBody Map<String, String> body) {

        System.out.println("포스트 겟 요청 들어옴");

        String userId = body.get("userId");

        List<Posts> result = postsRepository.findByUserId(userId);

        System.out.println(result);

        if(result.isEmpty()){
            return result;
        }
        else{
            return result;
        }
    }
}
