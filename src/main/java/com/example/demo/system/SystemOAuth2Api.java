package com.example.demo.system;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "System API")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/oauth2/v1/system")
public class SystemOAuth2Api {

    @PreAuthorize("@auth.isSystem()")
    @GetMapping()
    public Boolean isSystem() {
        return true;
    }
}
