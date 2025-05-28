package com.platform.dentify.patientattention.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Getter
@Setter
public class Appointment extends AuditableAbstractAggregateRoot<Appointment>  {

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    @Size(min = 1, max = 200)
    private String reason;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime duration;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment(CreateAppointmentCommand command) {
        appointmentDate = command._appointmentDate();
        reason = command._reason();
        completed = false;
        duration = command._duration();


    }

    public Appointment(){}

}
