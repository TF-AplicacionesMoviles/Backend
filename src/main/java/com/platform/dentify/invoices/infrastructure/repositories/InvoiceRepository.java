package com.platform.dentify.invoices.infrastructure.repositories;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findInvoiceByAppointmentId(Long appointmentId);
}
