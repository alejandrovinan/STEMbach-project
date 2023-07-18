package es.udc.stembach.backend.rest.dtos;

import lombok.Data;

@Data
public class FacultyDto {
    private Long id;
    private String name;

    public FacultyDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}


