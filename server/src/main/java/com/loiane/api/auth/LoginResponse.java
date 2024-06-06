package com.loiane.api.auth;

public record LoginResponse(String token, long expiresIn) {

}
