package com.example.timrocket_backend.service;

import com.example.timrocket_backend.domain.Session;
import com.example.timrocket_backend.repository.SessionRepository;
import com.example.timrocket_backend.service.dto.session.CreateSessionDTO;
import com.example.timrocket_backend.service.dto.session.SessionDTO;
import com.example.timrocket_backend.service.mapper.SessionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }

    public SessionDTO createSession(CreateSessionDTO createSessionDTO) {
        Session session = sessionMapper.createSessionDtoToSession(createSessionDTO);
        sessionRepository.save(session);
        SessionDTO sessionDTO = sessionMapper.sessionToSessionDto(session);
        return sessionDTO;
    }

    public List<SessionDTO> getAll() {
        return sessionRepository.findAll().stream()
                .map(s -> sessionMapper.sessionToSessionDto(s))
                .collect(Collectors.toList());
    }
}
