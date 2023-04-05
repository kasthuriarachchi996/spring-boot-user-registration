package com.learn.security.drmo;

import com.learn.security.controller.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Api/V1/Demo_controllr")

public class DemoController {
    @GetMapping()
    public ResponseEntity<String> sayhello()
    {
        return ResponseEntity.ok("hello from secured endpoint");
    }

}
