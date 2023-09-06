package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomizedProjectDaoImpl implements CustomizedProjectDao{

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public Slice<Project> findProjectListWithFilters(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                                     Boolean active, Integer maxGroups, Integer studentsPerGroup, String biennium,
                                                     Boolean assigned, int size, int page) {

        String queryString = "SELECT p FROM Project p " +
                             "WHERE p.revised = :revised " +
                             "AND p.active = :active " +
                             "AND p.assigned = :assigned";

        if(modality != null){
            queryString += "AND p.modality = :modality ";
        }

        if(offerZone != null){
            queryString += "AND p.offerZone = :offerZone ";
        }

        if(maxGroups != null && maxGroups > 0){
            queryString += "AND p.maxGroups <= :maxGroups ";
        }

        if(studentsPerGroup != null && studentsPerGroup > 0){
            queryString += "AND p.studentsPerGroups >= :studentsPerGroups ";
        }

        if(biennium != null){
            queryString += "AND p.biennium = :biennium";
        }

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);
        query.setParameter("revised", revised);
        query.setParameter("active", active);
        query.setParameter("assigned", assigned);

        if (modality != null) {
            query.setParameter("modality", modality.toString());
        }

        if(offerZone != null){
            query.setParameter("offerZone", offerZone.toString());
        }

        if(maxGroups != null && maxGroups > 0){
            query.setParameter("maxGroups", maxGroups);
        }

        if(studentsPerGroup != null && studentsPerGroup > 0){
            query.setParameter("studentsPerGroup", studentsPerGroup);
        }

        if(biennium != null){
            query.setParameter("biennium", biennium);
        }

        List<Project> projects = query.getResultList();
        boolean hasNext = projects.size() == (size + 1);

        if(hasNext){
            projects.remove(projects.size() - 1);
        }

        return new SliceImpl<>(projects, PageRequest.of(page, size), hasNext);
    }
}
