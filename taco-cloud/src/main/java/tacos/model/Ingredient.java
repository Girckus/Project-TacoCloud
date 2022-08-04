package tacos.model;

public class Ingredient {

	private final String id;
	private String name;
	private Type type;
	
	public static enum Type { WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE }

	public Ingredient( String id, String name, Type type) {
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