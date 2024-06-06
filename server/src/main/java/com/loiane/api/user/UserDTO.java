package com.loiane.api.user;

public record UserDTO(String fullName, String email, String password) {
    public UserDTO {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }
    }
}