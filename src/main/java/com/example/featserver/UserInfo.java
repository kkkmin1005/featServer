package com.example.featserver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashMap;
import java.util.Map;

@Entity
public class UserInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String userId;
    public String userPassword;
    public String userName;
    public String userEmail;

    public String profileImageUrl;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("userId", this.userId);
        map.put("userName", this.userName);
        map.put("profile", this.profileImageUrl);
        return map;
    }
}
