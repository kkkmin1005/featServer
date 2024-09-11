package com.example.featserver;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserAlarmController {
    private final UserAlarmRepository userAlarmRepository;

    @PostMapping("/load/alarm")
    public Map<String, Object> loadAlarm(@RequestBody Map<String, String> body) {

        String userId = body.get("userId");

        Optional<UserAlarm> result = userAlarmRepository.findUserAlarmByUserId(userId);

        if(result.isPresent()) {
            return result.get().toMap();
        }
        return null;
    }

    @PostMapping("/edit/entire/alarm")
    public ResponseEntity<String> editEntireAlarm(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");

        Optional<UserAlarm> result = userAlarmRepository.findUserAlarmByUserId(userId);

        if(result.isPresent()) {

            if (result.get().entireAlarm.equals("on")){
                result.get().entireAlarm = "off";
                return ResponseEntity.status(200).body("ok");
            }
            else{
                result.get().entireAlarm = "on";
                return ResponseEntity.status(200).body("ok");
            }
        }
        return ResponseEntity.status(401).body("bad");
    }

    @PostMapping("/edit/friend/alarm")
    public ResponseEntity<String> editFriendAlarm(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");

        Optional<UserAlarm> result = userAlarmRepository.findUserAlarmByUserId(userId);

        if(result.isPresent()) {

            if (result.get().freindAlarm.equals("on")){
                result.get().freindAlarm = "off";
                return ResponseEntity.status(200).body("ok");
            }
            else{
                result.get().freindAlarm = "on";
                return ResponseEntity.status(200).body("ok");
            }
        }
        return ResponseEntity.status(401).body("bad");
    }

    @PostMapping("/edit/friend/request")
    public ResponseEntity<String> editFriendRequest(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");

        Optional<UserAlarm> result = userAlarmRepository.findUserAlarmByUserId(userId);

        if(result.isPresent()) {

            if (result.get().friendRequest.equals("on")){
                result.get().friendRequest = "off";
                return ResponseEntity.status(200).body("ok");
            }
            else{
                result.get().friendRequest = "on";
                return ResponseEntity.status(200).body("ok");
            }
        }
        return ResponseEntity.status(401).body("bad");
    }





}
