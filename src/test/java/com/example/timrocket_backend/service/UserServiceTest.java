//package com.example.timrocket_backend.service;
//
//import com.example.timrocket_backend.exception.EditNotAllowedException;
//import com.example.timrocket_backend.security.SecurityRole;
//import com.example.timrocket_backend.service.dto.UserDTO;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserServiceTest {
//
//    @Test
//    void validateCanEditThrowsError() {
//        UserDTO userDTO = new UserDTO(UUID.fromString("7a178586-4a3d-44a1-b166-86f21bd1aed0"), null, null, null, null, SecurityRole.COACHEE.getRoleName(), null);
//        String id = "7a178586-4a3d-44a1-b166-86f21bd1aed1";
//        Assertions.assertThrows(EditNotAllowedException.class, () -> UserService.validateCanEdit(id, userDTO));
//    }
//
//    @Test
//    void validateCanEditDoesNotThrowError() {
//        UserDTO userDTO = new UserDTO(UUID.fromString("7a178586-4a3d-44a1-b166-86f21bd1aed0"), null, null, null, null, SecurityRole.COACHEE.getRoleName(), null);
//        String id = "7a178586-4a3d-44a1-b166-86f21bd1aed0";
//        Assertions.assertDoesNotThrow(() -> UserService.validateCanEdit(id, userDTO));
//    }
//
//    @Test
//    void validateCanEditAsAnAdmin() {
//        UserDTO userDTO = new UserDTO(UUID.fromString("7a178586-4a3d-44a1-b166-86f21bd1aed0"), null, null, null, null, SecurityRole.ADMIN.getRoleName(), null);
//        String id = "7a178586-4a3d-44a1-b166-86f21bd1aed1";
//        Assertions.assertDoesNotThrow(() -> UserService.validateCanEdit(id, userDTO));
//    }
//}