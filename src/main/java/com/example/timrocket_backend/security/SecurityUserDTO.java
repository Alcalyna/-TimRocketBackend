package com.example.timrocket_backend.security;

public record SecurityUserDTO(String email, String password, SecurityRole securityRole) {
}
