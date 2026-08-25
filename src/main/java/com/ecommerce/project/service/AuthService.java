package com.ecommerce.project.service;

import com.ecommerce.project.security.request.LoginRequest;
import com.ecommerce.project.security.request.SignUpRequest;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.response.UserInfoResponse;
import jakarta.validation.Valid;

public interface AuthService {

    UserInfoResponse signIn(@Valid LoginRequest loginRequest);

    MessageResponse signUp(@Valid SignUpRequest signUpRequest);
}
