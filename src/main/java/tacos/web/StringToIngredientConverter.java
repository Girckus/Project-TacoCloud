package tacos.web;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

@Component
public class StringToIngredientConverter  implements Converter<String, Ingredient> {

	private IngredientRepository ingredientRepo;
	
	public StringToIngredientConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		Optional<Ingredient> ingredient = ingredientRepo.findById(id);
		return ingredient.orElse(null);
	}

}