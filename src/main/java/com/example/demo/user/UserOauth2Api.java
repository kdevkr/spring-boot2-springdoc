package com.example.demo.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/oauth2/v1/users")
public class UserOauth2Api {

    private final UserService userService;

    @GetMapping("/me")
    public User getUser(OAuth2Authentication oauth) {
        return userService.getClientUser(oauth.getName());
    }

    @PutMapping("/me")
    public User updateUser(@RequestBody User updateUser, OAuth2Authentication oauth) {
        User user = userService.getClientUser(oauth.getName());
        user.setName(updateUser.getName());
        return userService.update(user);
    }

}
