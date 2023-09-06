package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    private Long id;
    private String name;
    private String surname;
    private String secondSurname;
    private String dni;
    private User.RoleType role;
    private Long schoolId;
    private Long groupId;

    public StudentDto() {
    }

    public StudentDto(Long id, String name, String surname, String secondSurname, String dni, User.RoleType role, Long schoolId, Long groupId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.dni = dni;
        this.role = role;
        this.schoolId = schoolId;
        this.groupId = groupId;
    }

    public StudentDto(String name, String surname, String secondSurname, String dni, User.RoleType role, Long schoolId, Long groupId) {
        this.name = name;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.dni = dni;
        this.role = role;
        this.schoolId = schoolId;
        this.groupId = groupId;
    }

    public StudentDto(String name, String surname, String secondSurname, String dni, User.RoleType role, Long schoolId) {
        this.name = name;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.dni = dni;
        this.role = role;
        this.schoolId = schoolId;
    }
}
