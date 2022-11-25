package tacos.web;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.model.TacoOrder;
import tacos.repository.OrderRepository;

@RestController
@RequestMapping(path="/api/tacos", produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
public class RestOrderController {

	private OrderRepository orderRepo;
	
	public RestOrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	@GetMapping(params="recent")
	public List<TacoOrder> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return orderRepo.findAll(page.first()).getContent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TacoOrder> orderById(@PathVariable String id) {
		Optional<TacoOrder> order = orderRepo.findById(id);
		
		if (order.isPresent()) {
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public TacoOrder postOrder(@RequestBody TacoOrder order) {
		return orderRepo.save(order);
	}
	
	@PutMapping(path="/{orderId}", consumes="application/json")
	public TacoOrder putOrder(@PathVariable("orderId") String orderId, @RequestBody TacoOrder order) {
		order.setId(orderId);
		return orderRepo.save(order);
	}
	
	@PatchMapping(path="/{orderId}", consumes="application/json")
	public TacoOrder patchOrder(@PathVariable("orderId") String orderId, @RequestBody TacoOrder patch) {
		TacoOrder order = orderRepo.findById(orderId).get();
	
		if (patch.getDeliveryName() != null) {
			order.setDeliveryName(patch.getDeliveryName());
		}
		
		if (patch.getDeliveryStreet() != null) {
			order.setDeliveryStreet(patch.getDeliveryStreet());
		}
		
		if (patch.getDeliveryCity() != null) {
			order.setDeliveryCity(patch.getDeliveryCity());
		}
		
		if (patch.getDeliveryState() != null) {
			order.setDeliveryState(patch.getDeliveryState());
		}
		
		if (patch.getDeliveryZip() != null) {
			order.setDeliveryZip(patch.getDeliveryZip());
		}
		
		if (patch.getCcNumber() != null) {
			order.setCcNumber(patch.getCcNumber());
		}
		
		if (patch.getCcExpiration() != null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
		
		if (patch.getCcCVV() != null) {
			order.setCcCVV(patch.getCcCVV());
		}
		
		return orderRepo.save(order);
	}
	
	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") String orderId) {
		try {
			orderRepo.deleteById(orderId);
		} catch (EmptyResultDataAccessException e) {}
	}
	
}