package es.udc.stembach.backend.model.entities;

import javax.persistence.*;

@Entity
public class ProjectInstance {

    private Long id;
    private String title;
    private String description;
    private String observations;
    private Project.Modality modality;
    private String url;
    private Project.OfferZone offerZone;
    private Boolean active;
    private Biennium biennium;
    private UDCTeacher createdBy;
    private StudentGroup studentGroup;

    public ProjectInstance() {
    }

    public ProjectInstance(String title, String description, String observations, Project.Modality modality, String url, Project.OfferZone offerZone, Boolean active, Biennium biennium, UDCTeacher createdBy, StudentGroup studentGroup) {
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.active = active;
        this.biennium = biennium;
        this.createdBy = createdBy;
        this.studentGroup = studentGroup;
    }

    public ProjectInstance(Long id, String title, String description, String observations, Project.Modality modality, String url, Project.OfferZone offerZone, Boolean active, Biennium biennium, UDCTeacher createdBy, StudentGroup studentGroup) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.active = active;
        this.biennium = biennium;
        this.createdBy = createdBy;
        this.studentGroup = studentGroup;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Project.Modality getModality() {
        return modality;
    }

    public void setModality(Project.Modality modality) {
        this.modality = modality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Project.OfferZone getOfferZone() {
        return offerZone;
    }

    public void setOfferZone(Project.OfferZone offerZone) {
        this.offerZone = offerZone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "bienniumId")
    public Biennium getBiennium() {
        return biennium;
    }

    public void setBiennium(Biennium biennium) {
        this.biennium = biennium;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "udcTeacherId")
    public UDCTeacher getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UDCTeacher createdBy) {
        this.createdBy = createdBy;
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "studentGroupId")
    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
}
