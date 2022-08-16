package tacos.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.model.Ingredient;
import tacos.repository.jdbc.JdbcIngredientRepository;

@Component
public class IngredientByIdConverter  implements Converter<String, Ingredient> {

	private JdbcIngredientRepository ingredientRepo;
	
	public IngredientByIdConverter(JdbcIngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		return ingredientRepo.findById(id).orElse(null);
	}

}