package com.example.timrocket_backend.service.mapper;

import com.example.timrocket_backend.domain.Session;
import com.example.timrocket_backend.repository.UserRepository;
import com.example.timrocket_backend.service.dto.session.CreateSessionDTO;
import com.example.timrocket_backend.service.dto.session.SessionDTO;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {

    private final UserRepository userRepository;

    public SessionMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Session createSessionDtoToSession(CreateSessionDTO createSessionDTO){
        Session session = new Session(
                createSessionDTO.subject(),
                createSessionDTO.date(),
                createSessionDTO.time(),
                createSessionDTO.location(),
                createSessionDTO.f2fLocation(),
                createSessionDTO.remarks(),
                userRepository.getById(createSessionDTO.coachee_id()),
                userRepository.getById(createSessionDTO.coach_id()));
        return session;
    }
     //    //public record SessionDTO(UUID sessionId, String subject, LocalDate date, LocalTime time,
     //    //                         String location, String f2fLocation, String remarks, User coachee, User coach) {
    public SessionDTO sessionToSessionDto(Session session){
        SessionDTO sessionDTO = new SessionDTO(session.getSessionId(), session.getSubject(), session.getDate(),
                session.getTime(), session.getLocation(), session.getF2fLocation(), session.getRemarks()
                , session.getCoachee(), session.getCoach());
        return sessionDTO;
    }
}
