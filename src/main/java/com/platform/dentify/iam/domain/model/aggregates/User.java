package com.platform.dentify.iam.domain.model.aggregates;

import com.platform.dentify.iam.domain.model.commands.SignUpCommand;
import com.platform.dentify.iam.domain.model.valueobjects.EmailAddress;
import com.platform.dentify.iam.domain.model.valueobjects.PersonName;
import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends AuditableAbstractAggregateRoot<User> implements UserDetails {
    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 128)
    private String password;

    @Embedded
    private PersonName name;

    @Embedded
    //Aquí se está indicando que el atributo "address" de la clase EmailAddress debe ser mapeado
    // a una columna llamada "email" en la tabla de la entidad principal.
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))})
    private EmailAddress email;
    private String companyName;
    private Boolean trial;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    public User(){}


    public User(String username, String password, PersonName name, EmailAddress email, String companyName, Boolean trial) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.trial = trial;
    }

    public User(String username, String password, String name, String lastName, String email, String companyName, Boolean trial) {
        this.username = username;
        this.password = password;
        this.name = new PersonName(name, lastName);
        this.email = new EmailAddress(email);
        this.companyName = companyName;
        this.trial = trial;

    }

    public String fullName() { return name.FullName();}
    public String email() { return email.address();}

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmail(String email) {
        this.email = new EmailAddress(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    //JWT USER DETAILS

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    public User(SignUpCommand command, String encryptedPassword){
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.companyName = command.companyName();
        this.username = command.username();
        this.password = encryptedPassword;
        this.trial = command.trial();
    }

}
