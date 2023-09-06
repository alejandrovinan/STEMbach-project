package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.Slice;

public interface CustomizedProjectDao {

    Slice<Project> findProjectListWithFilters(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                              Boolean active, Integer maxGroups, Integer studentsPerGroup, String biennium,
                                              Boolean assigned, int size, int page);
}
