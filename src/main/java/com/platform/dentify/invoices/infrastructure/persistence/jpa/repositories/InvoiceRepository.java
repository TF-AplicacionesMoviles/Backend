package com.platform.dentify.invoices.infrastructure.persistence.jpa.repositories;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findInvoiceByAppointmentId(Long appointmentId);
}
