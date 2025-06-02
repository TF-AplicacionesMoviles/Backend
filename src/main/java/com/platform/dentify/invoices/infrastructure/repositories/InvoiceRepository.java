package com.platform.dentify.invoices.infrastructure.repositories;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserId(Long userId);
    Optional<Invoice> findByIdAndUserId(Long invoiceId, Long userId);
    Optional<Invoice> findByAppointmentIdAndUserId(Long appointmentId, Long userId);
    Boolean existsByAppointmentIdAndUserId(Long appointmentId, Long userId);
}