package com.uca.parcialfinalncapas.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
