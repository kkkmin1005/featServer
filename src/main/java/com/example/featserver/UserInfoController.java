package com.example.featserver;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoRepository userInfoRepository;
    private final S3Service s3Service;

    @PostMapping("/edit/profile")
    public ResponseEntity<String> editProfile(@RequestBody Map<String, String> body){
        String userId = body.get("userId");
        String fileName = body.get("fileName");

        // 유저 정보 조회
        Optional<UserInfo> result = userInfoRepository.findByUserId(userId);

        if (result.isPresent()) {
            UserInfo userInfo = result.get();

            // S3 URL 생성
            String imageUrl = s3Service.createPresignedUrl("test/" + fileName);

            // 프로필 이미지 URL 수정
            userInfo.profileImageUrl = imageUrl;

            // 변경사항 저장
            userInfoRepository.save(userInfo);

            return ResponseEntity.ok(userInfo.toString());
        } else {
            return ResponseEntity.status(401).body("User not found");
        }
    }



}
