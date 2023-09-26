package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomizedRequestDaoImpl implements CustomizedRequestDao{

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public Slice<Request> findAllByProjectId(Long projectId, int page, int size) {

        String queryString = "SELECT r FROM Request r " +
                             "WHERE r.project.id = :projectId " +
                             "ORDER BY r.requestDate";

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);
        query.setParameter("projectId", projectId);

        List<Request> requests = query.getResultList();
        boolean hasNext = requests.size() == (size + 1);

        if(hasNext){
            requests.remove(requests.size() - 1);
        }
        return new SliceImpl<>(requests, PageRequest.of(page, size), hasNext);
    }
}
