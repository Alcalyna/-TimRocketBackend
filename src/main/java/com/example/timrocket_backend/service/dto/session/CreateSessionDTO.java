package com.example.timrocket_backend.service.dto.session;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateSessionDTO(@NotBlank(message = "subject should be provided") String subject,
                               @NotNull(message = "date should be provided")
                               @FutureOrPresent(message = "date should not be in the past") LocalDate date,
                               @NotNull(message = "time should be provided") LocalTime time,
                               @NotBlank(message = "location should be provided") String location,
                               String f2fLocation,
                               String remarks,
                               @NotNull(message = "coachee id should be provided") UUID coachee_id,
                               @NotNull(message = "coach id should be provided") UUID coach_id) {
}
