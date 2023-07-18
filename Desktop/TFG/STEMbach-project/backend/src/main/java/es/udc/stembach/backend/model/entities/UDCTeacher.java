package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class UDCTeacher extends User{

    private String email;
    private String dni;
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="facultyId")
    private Faculty faculty;

    public UDCTeacher() {
    }

    public UDCTeacher(String name, String surname, String secondSurname, RoleType role, String email, String dni, String password, Faculty faculty) {
        super(name, surname, secondSurname, role);
        this.email = email;
        this.dni = dni;
        this.password = password;
        this.faculty = faculty;
    }

    public UDCTeacher(Long id, String name, String surname, String secondSurname, RoleType role, String email, String dni, String password, Faculty faculty) {
        super(id, name, surname, secondSurname, role);
        this.email = email;
        this.dni = dni;
        this.password = password;
        this.faculty = faculty;
    }
}
