package com.platform.dentify.patientattention.domain.model.entities;


import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.commands.CreateMedicalHistoryCommand;
import com.platform.dentify.patientattention.domain.model.valueobjects.Diagnosis;
import com.platform.dentify.patientattention.domain.model.valueobjects.Medication;
import com.platform.dentify.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_medicalhistory_patient"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    public MedicalHistory() {

    }
    public MedicalHistory(CreateMedicalHistoryCommand command) {
        description = command.description();
        record = command.record();
        diagnosis = new Diagnosis(command.diagnosis());
        medication = new Medication(command.medication());

    }
}
