package com.FutureConnect.FutureConnect.Auth;

import com.FutureConnect.FutureConnect.Auth.DTO.SignupRequest;
import com.FutureConnect.FutureConnect.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // raw, will be encoded in service
        return ResponseEntity.ok(userService.signup(user));
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }
}
