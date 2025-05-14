package com.platform.dentify.patientattention.domain.model.entities;


import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.valueobjects.Diagnosis;
import com.platform.dentify.patientattention.domain.model.valueobjects.Medication;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.platform.dentify.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MedicalHistory extends AuditableModel {
    @Size(max = 250)
    private String description;

    @Size(max = 250)
    private String record;

    @Embedded
    private Diagnosis diagnosis;

    @Embedded
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public MedicalHistory() {}
}
