package com.genit.customersupport.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genit.customersupport.entity.TicketAiAnalysis;

public interface TicketAiAnalysisRepository extends JpaRepository<TicketAiAnalysis, Long> {

	Optional<TicketAiAnalysis> findByTicketId(Long ticketId);
}
