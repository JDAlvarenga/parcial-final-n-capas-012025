package com.uca.parcialfinalncapas.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";
}
