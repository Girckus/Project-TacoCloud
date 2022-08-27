package tacos.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.model.TacoOrder;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {
	
}