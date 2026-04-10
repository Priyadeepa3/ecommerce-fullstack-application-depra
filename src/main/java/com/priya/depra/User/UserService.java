package com.priya.depra.User;

import com.priya.depra.User.dto.UserRegistrationRequest;
import com.priya.depra.User.dto.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegistrationRequest request);

    List<UserResponse> getAllUsers();

    void toggleUserBlock(Long id);

    long countAllUsers(); // Add this line
}
