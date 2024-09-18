package com.example.featserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsRepository postsRepository;
    private final S3Service s3Service;

    @PostMapping("/load/dates")
    public List<LocalDate> loadPDates(@RequestBody Map<String, String> body) {

        List<Posts> result;
        System.out.println("포스트 겟 요청 들어옴");

        String userId = body.get("userId");

        result = postsRepository.findByUserId(userId);

        List<LocalDate> dates = new ArrayList<>();

        for(Posts p : result) {
            dates.add(p.Date);
        }

        return dates;
    }

    @PostMapping("/load/post")
    public String loadPosts(@RequestBody Map<String, String> body) {
        String date = body.get("date");
        String userId = body.get("userId");

        List<Posts> result = postsRepository.findByUserId(userId);

        for(Posts p : result) {
            if(p.Date.toString().equals(date)) {
                return p.image;
            }
        }
        return null;
    }

    @PostMapping("/presigned-url")
    String getURL(@RequestBody Map<String, String> body) {
        String fileName = body.get("fileName");
        String userId = body.get("userId");

        var result = s3Service.createPresignedUrl("test/" + fileName);

        var newPost = new Posts();

        newPost.userId = userId;
        newPost.image = result;
        newPost.Date = localDate;

        return result;
    }
}
