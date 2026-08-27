package com.genit.customersupport.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genit.customersupport.entity.SupportTicket;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}