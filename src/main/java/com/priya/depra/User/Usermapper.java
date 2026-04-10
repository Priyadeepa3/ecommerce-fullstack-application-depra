package com.priya.depra.User;


import com.priya.depra.User.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class Usermapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .active(user.isActive())
                .build();
    }
}
