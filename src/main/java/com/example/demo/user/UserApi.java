package com.example.demo.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserApi {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public User getUser(Authentication auth) {
        log.info("{}", auth);
        return userService.getUser(auth.getName());
    }

    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    @PutMapping("/{uid}")
    public User updateUser(@PathVariable Long uid,
                           @RequestBody User updateUser, Authentication auth) {
        User user = userService.getUser(uid);
        user.setName(updateUser.getName());
        return userService.update(user);
    }

}
