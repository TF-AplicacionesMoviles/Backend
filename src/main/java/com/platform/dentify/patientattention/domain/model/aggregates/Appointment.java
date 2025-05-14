package com.platform.dentify.patientattention.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@Entity
@Getter
@Setter
public class Appointment extends AuditableAbstractAggregateRoot<Appointment>  {

    @NotBlank
    @Column(nullable = false)
    private Date appointmentDate;

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

    public Appointment(){}

    public Appointment(CreateAppointmentCommand command) {
        this.appointmentDate = command._appointmentDate();
        this.reason = command._reason();
        this.completed = command._completed();
        this.duration = command._duration();

    }
}
