package tacos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecutiryConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests()
						.antMatchers("/design", "/orders").hasRole("USER")
						.antMatchers("/admin/**").hasRole("ADMIN")
						.antMatchers("/", "/**").permitAll()
						.antMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
						.antMatchers(HttpMethod.DELETE, "/api//ingredients").hasAuthority("SCOPE_deleteIngredients")
					.and()
						.formLogin()
						.loginPage("/login")
						.defaultSuccessUrl("/design")
					.and()
						.oauth2Login()
						.loginPage("/login")
						.defaultSuccessUrl("/design")
					.and()
						.logout()
						.logoutSuccessUrl("/")
					.and()
					    .csrf().ignoringAntMatchers("/api/tacos/**")
					.and()
						.oauth2ResourceServer(oauth2 -> oauth2.jwt())
					.build();
	}
	
}