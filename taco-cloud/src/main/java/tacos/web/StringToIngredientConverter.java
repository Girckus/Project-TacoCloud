package tacos.web;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.model.Ingredient;
import tacos.model.IngredientUDT;
import tacos.repository.IngredientRepository;

@Component
public class StringToIngredientConverter  implements Converter<String, IngredientUDT> {

	private IngredientRepository ingredientRepo;
	
	public StringToIngredientConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public IngredientUDT convert(String id) {
		Optional<Ingredient> ingredient = ingredientRepo.findById(id);
	    if (ingredient.isEmpty()) {
	    	return null;
	    }
	    
	    return ingredient.map(i -> {
	        return new IngredientUDT(i.getName(), i.getType());
	    }).get();
	}

}