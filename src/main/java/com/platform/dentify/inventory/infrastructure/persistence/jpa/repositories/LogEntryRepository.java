package com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories;

import com.platform.dentify.inventory.domain.model.aggregates.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

//    List<LogEntry> findAllByInvoice_User_Id(Long userId);
}
