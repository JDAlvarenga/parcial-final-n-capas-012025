package com.uca.parcialfinalncapas.controller;


import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.response.TokenResponse;
import com.uca.parcialfinalncapas.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request)
    {
        String rawToken = authService.login(request);
        var token = TokenResponse.builder()
                .accessToken(rawToken).build();

        return ResponseEntity.ok(token);
    }
}
