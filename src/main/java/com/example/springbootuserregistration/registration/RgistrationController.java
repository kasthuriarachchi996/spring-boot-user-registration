package com.example.springbootuserregistration.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/vi")
@AllArgsConstructor
public class RgistrationController {

    private final RegisterService registerService;

    public String register(@RequestBody RegistrationRequest request)
    {
        return registerService.register(request);
    }
}
