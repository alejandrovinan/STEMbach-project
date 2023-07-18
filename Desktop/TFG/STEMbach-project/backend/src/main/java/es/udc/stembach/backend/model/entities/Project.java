package es.udc.stembach.backend.model.entities;

public class Project {

    public enum Modality {PRESENCIAL, DISTANCIA, PRESENCIAL_DISTANCIA};
    public enum OfferZone {COR, FERR};

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
    private String biennium;


}
