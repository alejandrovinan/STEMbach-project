package es.udc.stembach.backend.rest.dtos;

import lombok.Data;

@Data
public class UDCTeacherSummaryDto {

    private Long id;
    private String name;
    private String surname;
    private String secondSurname;
    private String email;
    private String dni;

    public UDCTeacherSummaryDto(Long id, String name, String surname, String secondSurname, String email, String dni) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondSurname = secondSurname;
        this.email = email;
        this.dni = dni;
    }
}
