package com.example.timrocket_backend.repository;

import com.example.timrocket_backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

        @Transactional
        Member findByEmail(String email);
}
