package com.ecommerce.shop;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.shop.models.entitys.category.Category;
import com.ecommerce.shop.models.entitys.image.Image;
import com.ecommerce.shop.models.entitys.products.Brownie;
import com.ecommerce.shop.models.entitys.products.Cookie;
import com.ecommerce.shop.models.entitys.products.Product;
import com.ecommerce.shop.models.entitys.user.Permission;
import com.ecommerce.shop.models.entitys.user.Role;
import com.ecommerce.shop.models.entitys.user.User;
import com.ecommerce.shop.models.entitys.user.enums.ROLE;
import com.ecommerce.shop.repository.category.CategoryRepository;
import com.ecommerce.shop.repository.products.ProductRepository;
import com.ecommerce.shop.repository.users.UserRepository;
import com.ecommerce.shop.repository.users.permissions.PermissionRepository;
import com.ecommerce.shop.repository.users.roles.RoleRepository;

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
					.build();

			User user = User.builder().username("ferUser").password(new BCryptPasswordEncoder()
					.encode("1234")).roles(Set.of(roleUser))
					.build();

			User userInvited = User.builder().username("ferInvited").password(new BCryptPasswordEncoder()
					.encode("1234")).roles(Set.of(roleInvited)).build();

			userRepository.saveAll(List.of(user, userAdmin, userInvited));

			Category brownieCategory = Category.builder().name("Brownies").build();
			Category cookieCategory = Category.builder().name("Cookies").build();
			Category trufasCategory = Category.builder().name("Trufas").build();

			categoryRepository.saveAll(List.of(brownieCategory, cookieCategory, trufasCategory));

			cookieCategory = categoryRepository.findByName("Cookies").get();
			brownieCategory = categoryRepository.findByName("Brownies").get();
			trufasCategory = categoryRepository.findByName("Trufas").get();

			Product brownieClasico = Brownie.builder()
					.name("Brownie Clásico")
					.price(new BigDecimal("12000"))
					.code("Brownie clasico")
					.category(brownieCategory)
					.description(
							"El Brownie Clásico es el alma de La Casa del Brownie Verde. Preparado con cacao puro premium, mantequilla artesanal y un toque secreto de la casa, cada bocado explota con un sabor profundo a chocolate, una textura densa y un centro ligeramente fundente. Terminado con un generoso drizzle de fudge recién preparado. Perfecto para acompañar con un café, un vaso de leche fría o, mejor aún, con una buena conversación.")
					.ingredients(List.of("Chocolate belga 70%",
							"Mantequilla artesanal",
							"Azúcar moreno",
							"Huevo fresco",
							"Harina seleccionada",
							"Cacao puro",
							"Extracto de vainilla"))
					.features(List.of("Chocolate belga 70%",
							"Textura densa y jugosa",
							"Drizzle de fudge artesanal",
							"Sin conservantes"))
					.brand("LCDBV")
					.weight(120)
					.servings(1)
					.stock(10)
					.images(List.of(Image.builder().downloadUrl(
							"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779589383/tyg-products/kbyxtl2xx4gfd6f4wg90.png")
							.build(),
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779589385/tyg-products/d3xxbaqzmyljp8umrbp8.png")
									.build(),
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779589386/tyg-products/nukfjqn70uknvvelgvlg.png")
									.build()))
					.build();

			Product cookieChocochip = Cookie.builder()
					.name("Cookie chocochip")
					.price(new BigDecimal("10000"))
					.code("Cookie chocohip")
					.category(cookieCategory)
					.description(
							"La Cookie Chocochip de la casa es puro placer: bordes crujientes, centro suave y tierno, y chispas de chocolate semi-amargo que se funden en cada mordida. Horneadas en pequeños lotes para que siempre llegue fresca a tus manos. Una galleta clásica con carácter, pensada para compartir (aunque no te prometemos que quieras hacerlo).")
					.ingredients(List.of("Harina de trigo",
							"Mantequilla",
							"Azúcar moreno",
							"Azúcar blanco",
							"Huevo",
							"Chispas de chocolate",
							"Vainilla",
							"Sal marina"))
					.features(List.of("Crujiente por fuera, suave por dentro",
							"Chispas de chocolate semi-amargo",
							"Receta de la casa",
							"Horneado diario"))
					.brand("LCDBV")
					.weight(100)
					.servings(1)
					.stock(10)
					.images(List.of(
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779589738/tyg-products/d6tpptautj56e6uth8jl.png")
									.build(),
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779589737/tyg-products/karmoq8xqkc52cdm9jvw.png")
									.build(),
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779589735/tyg-products/ftgrvrkpn07d9omjdzr9.png")
									.build()))
					.build();

			Product brownieDoble = Brownie.builder()
					.name("Brownie doble")
					.price(new BigDecimal("15000"))
					.code("brownie doble")
					.category(brownieCategory)
					.description(
							"El Brownie Doble lleva la experiencia a otro nivel. Dos capas de brownie intenso, separadas por un corazón de ganache cremoso, coronadas con un extra drizzle y un toque decorativo verde que hace honor a la casa. Es el postre definitivo para los verdaderos fans del chocolate: denso, poderoso y con una presentación que enamora.")
					.ingredients(List.of("Chocolate belga 70%",
							"Chocolate semi-amargo",
							"Mantequilla",
							"Crema de leche",
							"Azúcar moreno",
							"Huevo",
							"Harina",
							"Cacao"))
					.features(List.of("Doble capa de brownie",
							"Ganache cremoso en el centro",
							"Extra drizzle de chocolate",
							"Edición especial de la casa"))
					.brand("LCDBV")
					.weight(150)
					.servings(1)
					.stock(10)
					.images(List.of(
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779590094/tyg-products/cafa9hamykfpmwrhixl3.png")
									.build(),
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779590097/tyg-products/utwgo3aoyf6ve82w84b1.png")
									.build()))
					.build();

			Product trufas = Cookie.builder()
					.name("Combo de la Casa")
					.price(new BigDecimal("8000"))
					.code("trufas")
					.description(
							"El Combo de la Casa es nuestra selección estrella: 1 Brownie Clásico + 2 Cookies Chocochip. Pensado para compartir un momento, sorprender a alguien o simplemente darte un gustazo doble. Llega cuidadosamente empacado con el toque de la casa y listo para endulzar cualquier ocasión.")
					.category(trufasCategory)
					.ingredients(List.of(
							"Ver ingredientes individuales de cada producto"))
					.features(List.of("1 Brownie Clásico + 2 Cookies",
							"Packaging especial de la casa",
							"Ahorra $2.50 vs productos sueltos",
							"Ideal para regalar"))
					.brand("LCDBV")
					.weight(100)
					.servings(1)
					.stock(10)
					.images(List.of(
							Image.builder().downloadUrl(
									"https://res.cloudinary.com/drhf4zw7b/image/upload/v1779590096/tyg-products/jyr8h5lehook6e3vowlq.png")
									.build()))
					.build();

			productRepository.saveAll(List.of(brownieClasico, cookieChocochip, brownieDoble, trufas));
			System.out.println(brownieClasico.getClass());
		};

	}

}
