package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Judges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "udcTeacherId")
    private UDCTeacher udcTeacher;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "defenseId")
    private Defense defense;

    public Judges() {
    }

    public Judges(UDCTeacher udcTeacher, Defense defense) {
        this.udcTeacher = udcTeacher;
        this.defense = defense;
    }
}
