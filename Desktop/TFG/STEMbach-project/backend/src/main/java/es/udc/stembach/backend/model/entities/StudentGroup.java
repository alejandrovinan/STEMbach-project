package es.udc.stembach.backend.model.entities;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
public class StudentGroup {

    private Long id;
    private Boolean hasProject;
    private School school;

    public StudentGroup() {
    }

    public StudentGroup(Boolean hasProject, School school) {
        this.hasProject = hasProject;
        this.school = school;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public Boolean getHasProject() {
        return hasProject;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="schoolId")
    public School getSchool() {
        return school;
    }
}
