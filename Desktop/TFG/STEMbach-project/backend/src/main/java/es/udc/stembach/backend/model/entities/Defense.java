package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Defense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "projectInstanceId")
    private ProjectInstance projectInstance;
    private String place;
    private LocalDateTime date;
    private BigDecimal mark;
    private String observations;

    public Defense() {
    }

    public Defense(ProjectInstance projectInstance, String place, LocalDateTime date, BigDecimal mark, String observations) {
        this.projectInstance = projectInstance;
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
    }

    public Defense(Long id, String place, LocalDateTime date, BigDecimal mark, String observations) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
    }
}
