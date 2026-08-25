package com.ecommerce.project.controller;

import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.request.LoginRequest;
import com.ecommerce.project.security.request.SignUpRequest;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<UserInfoResponse> signIn(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signIn(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

}
