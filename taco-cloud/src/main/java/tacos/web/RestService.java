package tacos.web;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tacos.model.Ingredient;
import tacos.model.TacoOrder;

@RestController
@RequestMapping(path="/restapi", produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
public class RestService {

	private RestTemplate rest = new RestTemplate();
	
	@GetMapping("/getIngredient/{id}")
	public Ingredient getIngredientById(@PathVariable("id") String ingredientId) {
		return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
	}	
	
	@PutMapping(path="/putIngredient/{id}", consumes="application/json")
	public void putOrder(@PathVariable("id") String ingredientId, @RequestBody Ingredient ingredient) {
		rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
	}
	
	@DeleteMapping("/deleteIngredient/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIngredient(@PathVariable("id") String ingredientId) {
		rest.delete("http://localhost:8080/ingredients/{id}", ingredientId);
	} 
	
	@PostMapping(path="/postIngredient/{id}", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
		return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
	}
	
}