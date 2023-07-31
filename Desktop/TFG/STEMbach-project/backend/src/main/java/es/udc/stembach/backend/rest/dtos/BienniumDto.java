package es.udc.stembach.backend.rest.dtos;

import lombok.Data;

@Data
public class BienniumDto {

    private Long id;
    private String name;

    public BienniumDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
