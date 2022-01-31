package com.example.timrocket_backend.service.dto.session;

import java.util.UUID;

public record SessionDTO(UUID sessionId, String subject) {
}
