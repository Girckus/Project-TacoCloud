package tacos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import tacos.repository.UserRepository;

@Configuration
@Profile("databaseuser")
public class DataBaseSecutiryConfig {

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			tacos.model.User user = userRepo.findByUsername(username);
			if (user != null) return user;
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}
	
}