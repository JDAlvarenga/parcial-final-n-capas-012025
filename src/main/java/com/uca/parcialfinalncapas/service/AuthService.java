package com.uca.parcialfinalncapas.service;


import com.uca.parcialfinalncapas.dto.request.LoginRequest;

public interface AuthService {
    String login (LoginRequest loginRequest);
}
