package com.example.timrocket_backend.api;

import com.example.timrocket_backend.service.SessionService;
import com.example.timrocket_backend.service.dto.session.CreateSessionDTO;
import com.example.timrocket_backend.service.dto.session.SessionDTO;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final Logger logger;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
        this.logger = LoggerFactory.getLogger(SessionController.class);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDTO createSession(@Valid @RequestBody CreateSessionDTO createSessionDTO) {
        SessionDTO sessionDTO = sessionService.createSession(createSessionDTO);
        return sessionDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.error(errorMessage, exception);
        });
        return errors;
    }
}
