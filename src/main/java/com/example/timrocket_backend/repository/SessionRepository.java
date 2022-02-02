package com.example.timrocket_backend.repository;

import com.example.timrocket_backend.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

}
