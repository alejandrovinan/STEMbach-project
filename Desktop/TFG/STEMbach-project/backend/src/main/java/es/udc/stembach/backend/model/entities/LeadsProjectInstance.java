package es.udc.stembach.backend.model.entities;

import javax.persistence.*;

@Entity
public class LeadsProjectInstance {

    private Long id;
    private ProjectInstance projectInstance;
    private UDCTeacher udcTeacher;

    public LeadsProjectInstance() {
    }

    public LeadsProjectInstance(ProjectInstance projectInstance, UDCTeacher udcTeacher) {
        this.projectInstance = projectInstance;
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
    @JoinColumn(name = "projectInstanceId")
    public ProjectInstance getProjectInstance() {
        return projectInstance;
    }

    public void setProjectInstance(ProjectInstance projectInstance) {
        this.projectInstance = projectInstance;
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
