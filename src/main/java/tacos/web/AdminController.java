package tacos.web;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import tacos.model.Ingredient;
import tacos.model.Taco;
import tacos.model.TacoOrder;
import tacos.model.User;
import tacos.repository.OrderRepository;
import tacos.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private UserRepository userRepo;
	private RestTemplate rest = new RestTemplate();
	
	public AdminController(OrderRepository orderRepo, UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	//@PreAuthorize("hasRole('ADMIN')") -- If using WebSecurityConfigurerAdapter
	@GetMapping("/showOrders")
	public String showAllOrders(Model model) {
		List<?> orders = rest.getForObject("http://localhost:8080/api/tacos?recent", List.class);
		model.addAttribute("orders", orders);
		return "admin";
	}
	
	@PostMapping("/deleteOrder")
	public String deleteOrder(String orderId) {
		rest.delete("http://localhost:8080/api/tacos/{id}", orderId);
		return "redirect:/admin/showOrders";
	}
	
	@GetMapping("/showOrder/{id}")
	public String getOrderById(@PathVariable("id") String orderId, Model model) {
		TacoOrder order = rest.getForObject("http://localhost:8080/api/tacos/{id}", TacoOrder.class, orderId);
		model.addAttribute("orders", List.of(order));
		return "admin";
	}
	
	@PostMapping(path="/addOrder")
	public String createOrder() {
		Ingredient coto = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "COTO");
		Ingredient carn = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "CARN");
		Ingredient tmto = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "TMTO");
		Ingredient jack = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "JACK");
		Ingredient slsa = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "SLSA");

		User user = userRepo.findByUsername("user");
		
		Taco taco = new Taco();
		taco.setName("Super Taco");
		taco.setIngredients(List.of(coto, carn, tmto, jack, slsa));
		taco.setCreatedAt(Instant.now());
		
		TacoOrder order = new TacoOrder();
		order.setCcCVV("567");
		order.setCcExpiration("01/28");
		order.setCcNumber("4532369002041802");
		order.setDeliveryCity(user.getCity());
		order.setDeliveryName(user.getFullname());
		order.setDeliveryState(user.getState());
		order.setDeliveryStreet(user.getStreet());
		order.setDeliveryZip(user.getZip());
		order.setPlacedAt(Instant.now());
		order.setTacos(List.of(taco));
		order.setUser(user);
		
		rest.postForObject("http://localhost:8080/api/tacos", order, TacoOrder.class);
		return "redirect:/admin/showOrders";
	}
	
	@PostMapping(path="/alterOrder")
	public String alterOrder(String orderId) {
		Ingredient coto = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "COTO");
		Ingredient carn = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "CARN");
		Ingredient tmto = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "TMTO");
		Ingredient jack = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "JACK");
		Ingredient slsa = rest.getForObject("http://localhost:8080/data-api/ingredients/{id}", Ingredient.class, "SLSA");

		User user = userRepo.findByUsername("test");
		
		Taco taco = new Taco();
		taco.setName("Super Taco Test");
		taco.setIngredients(List.of(coto, carn, tmto, jack, slsa));
		taco.setCreatedAt(Instant.now());
		
		TacoOrder order = new TacoOrder();
		order.setCcCVV("890");
		order.setCcExpiration("06/28");
		order.setCcNumber("5529919122269725");
		order.setDeliveryCity(user.getCity());
		order.setDeliveryName(user.getFullname());
		order.setDeliveryState(user.getState());
		order.setDeliveryStreet(user.getStreet());
		order.setDeliveryZip(user.getZip());
		order.setPlacedAt(Instant.now());
		order.setTacos(List.of(taco));
		order.setUser(user);
		
		rest.put("http://localhost:8080/api/tacos/{id}", order, orderId);
		return "redirect:/admin/showOrders";
	}
	
	@PostMapping(path="/updateOrder")
	public String updateOrder(String orderId) {
		TacoOrder order = rest.getForObject("http://localhost:8080/api/tacos/{id}", TacoOrder.class, orderId);
		order.setDeliveryCity("New City");
		order.setDeliveryName("New Name");
		order.setDeliveryState("New State");
		
		rest.patchForObject("http://localhost:8080/api/taco/{id}s", order, TacoOrder.class, orderId);
		return "redirect:/admin/showOrders";
	}
	
}