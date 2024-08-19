package com.msoft.security.jwt.dto;

public record LoginResponse(String token, Long expiresIn) {

}
