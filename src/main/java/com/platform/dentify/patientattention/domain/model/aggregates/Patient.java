package com.platform.dentify.patientattention.domain.model.aggregates;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.UpdatePatientCommand;
import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;
import com.platform.dentify.patientattention.domain.model.valueobjects.*;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Entity
@Getter
@Setter
public class Patient extends AuditableAbstractAggregateRoot<Patient> {

    @Embedded
    private Dni dni;

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))})
    private EmailAddress email;

    @Embedded
    private Birthday birthday;

    @Embedded
    private HomeAddress homeAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalHistory> medicalHistories;

    public Patient() {}

    public int GetAge(){
        return Period.between(birthday.birthday(), LocalDate.now()).getYears();
    }

    public Patient (CreatePatientCommand command) {
        this.dni = new Dni(command.dni());
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.birthday = new Birthday(command.birthday());
        this.homeAddress = new HomeAddress(command.homeAddress());

    }

    public void update(UpdatePatientCommand command) {
        this.dni = new Dni(command.dni());
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.birthday = new Birthday(command.birthday());
        this.homeAddress = new HomeAddress(command.homeAddress());
    }








}
