package com.example.timrocket_backend.service.dto.session;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.service.dto.CoachDTO;
import com.example.timrocket_backend.service.dto.UserDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record SessionDTO(UUID sessionId, String subject, LocalDate date, LocalTime time,
                         String location, String f2fLocation, String remarks, UserDTO coachee, CoachDTO coach) {
}
