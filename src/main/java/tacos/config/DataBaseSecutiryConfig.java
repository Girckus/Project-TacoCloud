package tacos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import tacos.repository.UserRepository;
import tacos.model.User;

@Configuration
@Profile("databaseuser")
public class DataBaseSecutiryConfig {

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if (user != null) return user;
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}
	
}