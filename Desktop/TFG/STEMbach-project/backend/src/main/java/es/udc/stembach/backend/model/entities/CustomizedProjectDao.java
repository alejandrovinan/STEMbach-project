package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomizedProjectDao {

    Slice<Project> findProjectListWithFilters(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                              Boolean active, Integer maxGroups, Integer studentsPerGroup, String biennium,
                                              Boolean assigned, List<Long> teachers, String title, int size, int page);
}
