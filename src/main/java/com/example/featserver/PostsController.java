package com.example.featserver;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class PostsController {

    private final PostsRepository postsRepository;
    private final S3Service s3Service;
    private final FriendRepository friendRepository;
    private final AlarmsRepository alarmsRepository;

    @PostMapping("/load/dates") // 만약 친구 캘린더를 요청하는 상황이면 userId에 친구 아이디 넣기
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

    @PostMapping("/load/post/bydate")
    public String loadPostsByDate(@RequestBody Map<String, String> body) {
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

    @PostMapping("/upload/post")
    String getURL(@RequestBody Map<String, String> body) {
        String fileName = body.get("fileName");
        String userId = body.get("userId");
        String date = body.get("date");

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String finalFileExtension;

        LocalDate new_date = LocalDate.parse(date);

        if(fileExtension.equals("jpg")){
            finalFileExtension = "image/" + "jpeg";
        }
        else{
            finalFileExtension = "image/" + fileExtension;
        }

        var result = s3Service.createPresignedUrl("test/" + fileName, finalFileExtension);

        var friend = friendRepository.findByFromUserIdAndIsFriendTrue(userId);

        List<String> toUserIds = new ArrayList<>();

        for(Friend f : friend) {
            toUserIds.add(f.getToUserId());
        }

        var newPost = new Posts();

        newPost.userId = userId;
        newPost.image = result;
        newPost.Date = new_date;

        postsRepository.save(newPost);

        for (String toUserId : toUserIds) {
            var newAlarm = new Alarms();

            newAlarm.toUserId = toUserId;
            newAlarm.fromUserId = userId;
            newAlarm.type = "newPosts";

            alarmsRepository.save(newAlarm);
        }

        return result;
    }

    @PostMapping("/load/posts/home")
    List<String> loadPostsHome(@RequestBody Map<String, String> body) {
        System.out.println("요청 들어옴");

        String userId = body.get("userId");

        List<String> HomeImages = new ArrayList<>();

        List<Posts> result = postsRepository.findByUserId(userId);
        for(Posts p : result) {
            HomeImages.add(p.image);
        }

        return HomeImages;
    }




}
