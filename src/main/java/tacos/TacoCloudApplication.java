package tacos;

import java.time.Instant;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Taco;
import tacos.model.TacoOrder;
import tacos.model.User;
import tacos.repository.IngredientRepository;
import tacos.repository.OrderRepository;
import tacos.repository.UserRepository;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner dataLoader(IngredientRepository ingredientRepo, UserRepository userRepo, OrderRepository oderRepo, PasswordEncoder passwordEncoder) {
		return args -> {
			Ingredient flto = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
			Ingredient coto = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
			Ingredient grbf = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
			Ingredient carn = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
			Ingredient tmto = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
			Ingredient letc = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
			Ingredient ched = new Ingredient("CHED", "Cheddar", Type.CHEESE);
			Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
			Ingredient slsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
			Ingredient srcr = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
			
			ingredientRepo.save(flto);
			ingredientRepo.save(coto);
			ingredientRepo.save(grbf);
			ingredientRepo.save(carn);
			ingredientRepo.save(tmto);
			ingredientRepo.save(letc);
			ingredientRepo.save(ched);
			ingredientRepo.save(jack);
			ingredientRepo.save(slsa);
			ingredientRepo.save(srcr);
			
			User admin = new User("admin", passwordEncoder.encode("admin"), "adminfullname", "adminstreet", "admincity", "adminstate", "adminzip", "adminphone");
			User user = new User("user", passwordEncoder.encode("user"), "userfullname", "userstreet", "usercity", "userstate", "userzip", "userphone");
			User test = new User("test", passwordEncoder.encode("test"), "testfullname", "teststreet", "testcity", "teststate", "testzip", "testphone");
			
			userRepo.save(admin);
			userRepo.save(user);
			userRepo.save(test);

			Taco taco1 = new Taco();
			taco1.setName("Taco1");
			taco1.setIngredients(List.of(flto, carn, tmto, ched, srcr));
			taco1.setCreatedAt(Instant.now());
			
			Taco taco2 = new Taco();
			taco2.setName("Taco2");
			taco2.setIngredients(List.of(coto, grbf, letc, jack, slsa));
			taco2.setCreatedAt(Instant.now());
			
			TacoOrder order1 = new TacoOrder();
			order1.setCcCVV("123");
			order1.setCcExpiration("12/28");
			order1.setCcNumber("4532026619556743");
			order1.setDeliveryCity(user.getCity());
			order1.setDeliveryName(user.getFullname());
			order1.setDeliveryState(user.getState());
			order1.setDeliveryStreet(user.getStreet());
			order1.setDeliveryZip(user.getZip());
			order1.setPlacedAt(Instant.now());
			order1.setTacos(List.of(taco1, taco2));
			order1.setUser(user);
			
			Taco taco3 = new Taco();
			taco3.setName("Taco3");
			taco3.setIngredients(List.of(coto, carn, letc, jack, srcr));
			taco3.setCreatedAt(Instant.now());
			
			Taco taco4 = new Taco();
			taco4.setName("Taco4");
			taco4.setIngredients(List.of(coto, carn, letc, jack, srcr));
			taco4.setCreatedAt(Instant.now());
			
			TacoOrder order2 = new TacoOrder();
			order2.setCcCVV("456");
			order2.setCcExpiration("01/30");
			order2.setCcNumber("5491906411461240");
			order2.setDeliveryCity(test.getCity());
			order2.setDeliveryName(test.getFullname());
			order2.setDeliveryState(test.getState());
			order2.setDeliveryStreet(test.getStreet());
			order2.setDeliveryZip(test.getZip());
			order2.setPlacedAt(Instant.now());
			order2.setTacos(List.of(taco3, taco4));
			order2.setUser(test);
			
			oderRepo.save(order1);
			oderRepo.save(order2);
		};
	}

}