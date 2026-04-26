package com.ecommerce.shop;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.shop.models.entitys.category.Category;
import com.ecommerce.shop.models.entitys.user.Permission;
import com.ecommerce.shop.models.entitys.user.Role;
import com.ecommerce.shop.models.entitys.user.User;
import com.ecommerce.shop.models.entitys.user.enums.ROLE;
import com.ecommerce.shop.repository.category.CategoryRepository;
import com.ecommerce.shop.repository.products.ProductRepository;
import com.ecommerce.shop.repository.users.UserRepository;
import com.ecommerce.shop.repository.users.permissions.PermissionRepository;
import com.ecommerce.shop.repository.users.roles.RoleRepository;
import com.ecommerce.shop.utils.products.ProductsList;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, CategoryRepository categoryRepository,
			ProductRepository productRepository, PermissionRepository permissionRepository,
			RoleRepository roleRepository) {
		return args -> {

			permissionRepository.saveAll(List.of(
					Permission.builder().name("CREATE").build(),
					Permission.builder().name("UPDATE").build(),
					Permission.builder().name("READ").build(),
					Permission.builder().name("DELETE").build()));

			Permission createPermission = permissionRepository.findByName("CREATE").orElseThrow();
			Permission updatePermission = permissionRepository.findByName("UPDATE").orElseThrow();
			Permission readPermission = permissionRepository.findByName("READ").orElseThrow();
			Permission deletePermission = permissionRepository.findByName("DELETE").orElseThrow();

			Role roleAdmin = Role.builder().roleName(ROLE.ADMIN)
					.permissions(new HashSet<>(List.of(createPermission, updatePermission, readPermission,
							deletePermission)))
					.build();

			Role roleUser = Role.builder().roleName(ROLE.USER)
					.permissions(new HashSet<>(List.of(createPermission, updatePermission, readPermission,
							deletePermission)))
					.build();

			Role roleInvited = Role.builder().roleName(ROLE.INVITED)
					.permissions(new HashSet<>(List.of(readPermission)))
					.build();

			roleRepository.saveAll(List.of(roleAdmin, roleUser, roleInvited));

			roleAdmin = roleRepository.findByRoleName(ROLE.ADMIN).orElseThrow();
			roleUser = roleRepository.findByRoleName(ROLE.USER).orElseThrow();
			roleInvited = roleRepository.findByRoleName(ROLE.INVITED).orElseThrow();

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
					.encode("1234")).roles(Set.of(roleInvited, roleAdmin)).enabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true).credentialsNonExpired(true).build();

			userRepository.saveAll(List.of(user, userAdmin, userInvited));

			Category category = Category.builder().name("Microprocesadores").build();

			categoryRepository.save(category);

			ProductsList productsList = new ProductsList(category);

			productRepository.saveAll(productsList.getProductsList());
		};
	}
}
