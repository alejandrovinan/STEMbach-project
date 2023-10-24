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
    private String place;
    private LocalDateTime date;
    private BigDecimal mark;
    private String observations;

    public Defense() {
    }

    public Defense(String place, LocalDateTime date, BigDecimal mark, String observations) {
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
    }
}
