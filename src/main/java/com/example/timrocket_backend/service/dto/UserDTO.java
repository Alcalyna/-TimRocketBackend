package com.example.timrocket_backend.service.dto;

import java.util.UUID;

public record UserDTO(UUID userId,
                      String firstName,
                      String lastName,
                      String email,
                      String company,
                      String role,
                      String pictureUrl) {
}

