package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRevision {

    private Long id;

    public ProjectRevision() {
    }

    public ProjectRevision(Long id) {
        this.id = id;
    }
}
