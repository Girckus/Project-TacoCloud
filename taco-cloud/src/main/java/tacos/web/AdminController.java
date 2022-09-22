package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tacos.model.User;
import tacos.repository.OrderRepository;
import tacos.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private OrderRepository orderRepo;
	private UserRepository userRepo;
	
	public AdminController(OrderRepository orderRepo, UserRepository userRepo) {
		this.orderRepo = orderRepo;
		this.userRepo = userRepo;
	}
	
	
	//@PreAuthorize("hasRole('ADMIN')") -- If using WebSecurityConfigurerAdapter
	@GetMapping("/showOrders")
	public String showAllOrders(Model model) {
		model.addAttribute("orders", orderRepo.findAll());
		return "admin";
	}
	
	//@PostAuthorize("hasRole('ADMIN') || returnObjec.username == authentication.name") -- If using WebSecurityConfigurerAdapter
	@PostMapping("/getUser")
	public User getUser(@RequestParam("username") String username) {
		return userRepo.findByUsername(username);
	}
	
}