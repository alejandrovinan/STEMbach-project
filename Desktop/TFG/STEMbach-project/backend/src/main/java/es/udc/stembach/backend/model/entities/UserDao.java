package es.udc.stembach.backend.model.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	
	boolean existsByUserName(String userName);

	Optional<User> findByUserName(String userName);
	
}
