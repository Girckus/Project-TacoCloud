package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.model.TacoOrder;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@ModelAttribute(name = "order") TacoOrder order, SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
}