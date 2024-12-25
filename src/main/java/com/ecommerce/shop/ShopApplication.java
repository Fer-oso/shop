package com.ecommerce.shop;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.shop.models.user.Permission;
import com.ecommerce.shop.models.user.Role;
import com.ecommerce.shop.models.user.User;
import com.ecommerce.shop.models.user.enums.ROLE_NAME;
import com.ecommerce.shop.repository.users.UserRepository;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	/* */
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			Permission createPermission = Permission.builder().name("CREATE").build();

			Permission updatePermission = Permission.builder().name("UPDATE").build();

			Permission readPermission = Permission.builder().name("READ").build();

			Permission deletePermission = Permission.builder().name("DELETE").build();

			Role roleAdmin = Role.builder().roleName(ROLE_NAME.ADMIN)
					.permissions(Set.of(createPermission, updatePermission, readPermission,
							deletePermission))
					.build();

			Role roleUser = Role.builder().roleName(ROLE_NAME.USER)
					.permissions(Set.of(createPermission, updatePermission, readPermission,
							deletePermission))
					.build();

			Role roleInvited = Role.builder().roleName(ROLE_NAME.INVITED)
					.permissions(Set.of(readPermission))
					.build();

			User userAdmin = User.builder().username("ferAdmin").password(new BCryptPasswordEncoder().encode("1234"))
					.roles(Set.of(roleAdmin))
					.enabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true).credentialsNonExpired(true).build();

			User user = User.builder().username("ferUser").password(new BCryptPasswordEncoder()
					.encode("1234")).roles(Set.of(roleUser)).enabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true).credentialsNonExpired(true).build();

			User userInvited = User.builder().username("ferInvited").password(new BCryptPasswordEncoder()
					.encode("1234")).roles(Set.of(roleInvited,roleAdmin)).enabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true).credentialsNonExpired(true).build();

			userRepository.saveAll(List.of(user, userAdmin, userInvited));
		};
	}

}
