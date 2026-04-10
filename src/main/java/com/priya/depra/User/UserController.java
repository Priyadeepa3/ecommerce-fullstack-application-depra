package com.priya.depra.User;




import com.priya.depra.User.dto.UserRegistrationRequest;
import com.priya.depra.User.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        System.out.println("Incoming: " + request);
        return userService.register(request);
    }





}
