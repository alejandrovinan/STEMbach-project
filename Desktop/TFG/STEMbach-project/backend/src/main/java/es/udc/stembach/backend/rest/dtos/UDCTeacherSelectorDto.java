package es.udc.stembach.backend.rest.dtos;

import lombok.Data;

@Data
public class UDCTeacherSelectorDto {

    private Long id;
    private String name;
    private String surname;
    private String secondSurname;

    public UDCTeacherSelectorDto() {
    }

    public UDCTeacherSelectorDto(Long id, String name, String surname, String secondSurname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondSurname = secondSurname;
    }
}
