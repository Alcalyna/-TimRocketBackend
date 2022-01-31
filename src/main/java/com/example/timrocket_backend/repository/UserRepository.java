package com.example.timrocket_backend.repository;

import com.example.timrocket_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

        User findByEmail(String email);
        User getById(UUID id);

}
