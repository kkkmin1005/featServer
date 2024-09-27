package com.example.featserver;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendRepository friendRepository;
    private final AlarmsRepository alarmsRepository;

    @PostMapping("/edit/friendRequest")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody Map<String, String> body){

        String fromUserId = body.get("userId");
        String toUserId = body.get("toUserId");
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

                friendRepository.save(friend);
                return ResponseEntity.ok("Friend request accepted");
            }
        }

        return ResponseEntity.status(401).body("fail");
    }

    @PostMapping("/request/friend")
    public ResponseEntity<String> requestFriend(@RequestBody Map<String, String> body){

        String fromUserId = body.get("userId");
        String toUserId = body.get("toUserId");

        var newFriendRequest = new Friend();
        var newFriendRequestAlarm = new Alarms();

        newFriendRequest.fromUserId = fromUserId;
        newFriendRequest.toUserId = toUserId;
        newFriendRequest.isFriend = false;

        newFriendRequestAlarm.fromUserId = fromUserId;
        newFriendRequestAlarm.toUserId = toUserId;
        newFriendRequestAlarm.type = "friendRequest";

        friendRepository.save(newFriendRequest);
        alarmsRepository.save(newFriendRequestAlarm);

        return ResponseEntity.ok("Friend request request accepted");
    }

}
