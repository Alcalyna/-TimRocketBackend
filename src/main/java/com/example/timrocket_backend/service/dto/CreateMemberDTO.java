package com.example.timrocket_backend.service.dto;

public record CreateMemberDTO(String firstName,
                              String lastName,
                              String email,
                              String password,
                              String company) {
}
