package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class CenterSTEMCoordinator extends User{

    private String email;
    private String password;

    public CenterSTEMCoordinator() {
    }

    public CenterSTEMCoordinator(String name, String surname, String secondSurname, RoleType role, String email, String password) {
        super(name, surname, secondSurname, role);
        this.email = email;
        this.password = password;
    }

    public CenterSTEMCoordinator(Long id, String name, String surname, String secondSurname, RoleType role, String email, String password) {
        super(id, name, surname, secondSurname, role);
        this.email = email;
        this.password = password;
    }
}
