package es.udc.stembach.backend.rest.dtos;

import lombok.Data;

@Data
public class SchoolDto {
    private Long id;
    private String name;
    private String location;

    public SchoolDto(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
