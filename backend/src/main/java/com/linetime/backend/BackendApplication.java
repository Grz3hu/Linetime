package com.linetime.backend;

import com.linetime.backend.repository.RoleRepository;
import com.linetime.backend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class BackendApplication {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
//		Role user = new Role("ROLE_USER");
//		Role admin = new Role("ROLE_ADMIN");
//		roleRepository.save(user);
//		roleRepository.save(admin);
		SpringApplication.run(BackendApplication.class, args);
	}

//	@Bean
//	public CorsFilter corsFilter() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		final CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*"); // this allows all origin
//		config.addAllowedHeader("*"); // this allows all headers
//		config.addAllowedMethod("OPTIONS");
//		config.addAllowedMethod("HEAD");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("DELETE");
//		config.addAllowedMethod("PATCH");
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}
//
}
