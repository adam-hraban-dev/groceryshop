package cz.libsoft.groceryshop;

import cz.libsoft.groceryshop.model.Product;
import cz.libsoft.groceryshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@SpringBootApplication
@RestController
public class GroceryshopApplication {

	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(GroceryshopApplication.class, args);
	}

/*	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/save-product")
	public String saveProduct(){
		productRepository.save(Product.builder()
				.name("Name")
				.price(BigDecimal.valueOf(123.5))
						.stock(null)
				.build());
		return "ok";
	}*/

}
