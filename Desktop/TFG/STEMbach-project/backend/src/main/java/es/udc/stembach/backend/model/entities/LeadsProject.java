package es.udc.stembach.backend.model.entities;

import javax.persistence.*;

@Entity
public class LeadsProject {

    private Long id;
    private Project project;
    private UDCTeacher udcTeacher;

    public LeadsProject() {
    }

    public LeadsProject(Project project, UDCTeacher udcTeacher) {
        this.project = project;
        this.udcTeacher = udcTeacher;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "udcTeacherId")
    public UDCTeacher getUdcTeacher() {
        return udcTeacher;
    }

    public void setUdcTeacher(UDCTeacher udcTeacher) {
        this.udcTeacher = udcTeacher;
    }
}
