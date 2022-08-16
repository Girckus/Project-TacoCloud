package tacos.repository.jdbc;

import java.util.List;
import java.util.Optional;

public interface JdbcRepository<T> {

	List<T> findAll();
	
	Optional<T> findById(String id);
	
	T save(T element);
	
}