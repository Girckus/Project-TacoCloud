package tacos.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tacos.model.TacoOrder;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<TacoOrder, Long> {
	
}