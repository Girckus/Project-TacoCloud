package tacos.model;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import tacos.model.Ingredient.Type;

@UserDefinedType("ingredient")
public class IngredientUDT {

	private String name;
	private Ingredient.Type type;
	
	public IngredientUDT(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public Ingredient.Type getType() {
		return type;
	}

}