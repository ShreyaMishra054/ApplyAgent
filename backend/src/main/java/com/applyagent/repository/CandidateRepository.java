package com.applyagent.repository;

import com.applyagent.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {
    List<CandidateEntity> findByNameContainingIgnoreCase(String name);
    List<CandidateEntity> findAllByOrderByUpdatedAtDesc();
}
