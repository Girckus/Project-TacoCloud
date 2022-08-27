package tacos.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import tacos.TacoUDRUtils;

@Table("tacos")
public class Taco  implements Serializable {

    private static final long serialVersionUID = 1L;
	
    @PrimaryKeyColumn(type=PrimaryKeyType.PARTITIONED)
    private UUID id = Uuids.timeBased();
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@Size(min=1, message="You must choose at least 1 ingredient")
	@Column("ingredients")
	private List<IngredientUDT> ingredients;
	
	@PrimaryKeyColumn(type=PrimaryKeyType.CLUSTERED, ordering=Ordering.DESCENDING)
	private Instant createdAt;

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(TacoUDRUtils.toIngredientUDT(ingredient));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<IngredientUDT> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientUDT> ingredients) {
		this.ingredients = ingredients;
	}
	
	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public void createdAt() {
		this.createdAt = Instant.now();
	}
	
}