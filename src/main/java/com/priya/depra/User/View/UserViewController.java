package com.priya.depra.User.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/register") // This is the URL you will type in the browser
    public String showRegistrationForm() {
        return "signup"; // This looks for signup.html in src/main/resources/templates/
    }
}