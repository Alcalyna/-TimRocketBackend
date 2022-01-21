package com.example.timrocket_backend.service.dto;

import java.util.UUID;

public record MemberDTO(UUID id,
                        String firstName,
                        String lastName,
                        String email,
                        String company) {

}

