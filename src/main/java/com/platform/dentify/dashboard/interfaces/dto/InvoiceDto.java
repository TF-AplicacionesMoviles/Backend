package com.platform.dentify.dashboard.interfaces.dto;

import java.time.LocalDateTime;

public record InvoiceDto(Long id, Double amount, LocalDateTime createdAt) {}
