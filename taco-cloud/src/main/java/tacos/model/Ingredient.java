package tacos.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;


@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
	public static enum Type { WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE }

	public Ingredient() {}
	
	public Ingredient(String id, String name, Type type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

}