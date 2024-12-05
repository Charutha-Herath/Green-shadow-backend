package lk.ijse.greenshadow;

import lk.ijse.greenshadow.entity.Role;
import lk.ijse.greenshadow.repository.RoleRepository;
import lk.ijse.greenshadow.util.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GreenShadowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenShadowBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository) {
		return args -> {
			for (Roles role : Roles.values()) {
				if (!roleRepository.findByName(role).isPresent()) {
					roleRepository.save(new Role(null, role));
				}
			}
		};
	}

}
