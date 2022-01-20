package com.example.timrocket_backend.repository;

import com.example.timrocket_backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
//    List<Member> findById(UUID id);
}