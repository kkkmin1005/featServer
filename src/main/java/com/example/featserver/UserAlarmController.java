package com.example.featserver;

import lombok.RequiredArgsConstructor;
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

}
