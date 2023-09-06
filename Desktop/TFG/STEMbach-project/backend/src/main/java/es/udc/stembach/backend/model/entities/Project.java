package es.udc.stembach.backend.model.entities;

import javax.persistence.*;

@Entity
public class Project {

    public enum Modality {PRESENCIAL, DISTANCIA, PRESENCIAL_DISTANCIA};
    public enum OfferZone {COR, FERR, GAL};

    private Long id;
    private String title;
    private String description;
    private String observations;
    private Modality modality;
    private String url;
    private OfferZone offerZone;
    private Boolean revised;
    private Boolean active;
    private Integer maxGroups;
    private Integer studentsPerGroup;
    private Biennium biennium;
    private UDCTeacher createdBy;
    private Boolean assigned;

    public Project() {
    }

    public Project(String title, String description, String observations, Modality modality, String url,
                   OfferZone offerZone, Boolean revised, Boolean active, Integer maxGroups, Integer studentsPerGroup, Biennium biennium, UDCTeacher createdBy, Boolean assigned) {
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.revised = revised;
        this.active = active;
        this.maxGroups = maxGroups;
        this.studentsPerGroup = studentsPerGroup;
        this.biennium = biennium;
        this.createdBy = createdBy;
        this.assigned = assigned;
    }

    public Project(Long id, String title, String description, String observations, Modality modality, String url, OfferZone offerZone, Boolean revised, Boolean active, Integer maxGroups, Integer studentsPerGroup, Biennium biennium, UDCTeacher createdBy, Boolean assigned) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.revised = revised;
        this.active = active;
        this.maxGroups = maxGroups;
        this.studentsPerGroup = studentsPerGroup;
        this.biennium = biennium;
        this.createdBy = createdBy;
        this.assigned = assigned;
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

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OfferZone getOfferZone() {
        return offerZone;
    }

    public void setOfferZone(OfferZone offerZone) {
        this.offerZone = offerZone;
    }

    public Boolean getRevised() {
        return revised;
    }

    public void setRevised(Boolean revised) {
        this.revised = revised;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getMaxGroups() {
        return maxGroups;
    }

    public void setMaxGroups(Integer maxGroups) {
        this.maxGroups = maxGroups;
    }

    public Integer getStudentsPerGroup() {
        return studentsPerGroup;
    }

    public void setStudentsPerGroup(Integer studentsPerGroup) {
        this.studentsPerGroup = studentsPerGroup;
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

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }
}
