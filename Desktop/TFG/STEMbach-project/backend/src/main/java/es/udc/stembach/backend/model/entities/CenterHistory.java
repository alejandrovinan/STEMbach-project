package es.udc.stembach.backend.model.entities;

import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
public class CenterHistory {

    private Long id;
    private CenterSTEMCoordinator centerSTEMCoordinator;
    private School school;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CenterHistory() {
    }

    public CenterHistory(CenterSTEMCoordinator centerSTEMCoordinator, School school, LocalDateTime startDate, LocalDateTime endDate) {
        this.centerSTEMCoordinator = centerSTEMCoordinator;
        this.school = school;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "centerSTEMCoordinatorId")
    public CenterSTEMCoordinator getCenterSTEMCoordinator() {
        return centerSTEMCoordinator;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "schoolId")
    public School getSchool() {
        return school;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
