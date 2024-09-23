package com.example.featserver;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FriendController {
    private final FriendRepository friendRepository;

    @PostMapping("/edit/friendrequest")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody Map<String, String> body){

        String fromUserId = body.get("from");
        String toUserId = body.get("to");
        String isFriend = body.get("isFriend");

        boolean isFrinedResult;

        if(isFriend.equals("true")) {
            isFrinedResult = true;
        }
        else{
            isFrinedResult = false;
        }

        List<Friend> result = friendRepository.findByToUserId(toUserId);

        for (Friend friend : result) {
            if(friend.fromUserId.equals(fromUserId)){
                friend.isFriend = isFrinedResult;
                return ResponseEntity.ok("Friend request accepted");
            }
        }

        return ResponseEntity.status(401).body("fail");
    }


}
