package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class STEMCoordinator extends User{

    private String email;
    private String password;

    public STEMCoordinator() {
    }

    public STEMCoordinator(String name, String surname, String secondSurname, RoleType role, String email, String password) {
        super(name, surname, secondSurname, role);
        this.email = email;
        this.password = password;
    }

    public STEMCoordinator(Long id, String name, String surname, String secondSurname, RoleType role, String email, String password) {
        super(id, name, surname, secondSurname, role);
        this.email = email;
        this.password = password;
    }
}
