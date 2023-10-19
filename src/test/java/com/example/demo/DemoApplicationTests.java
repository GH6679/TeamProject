package com.example.demo;

import com.example.demo.domain.entity.Product;
import com.example.demo.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	void contextLoads() {

		for(Long i = 1L; i < 30L; i++) {

			Product product = Product.builder()
					.productcode(i)
					.productname("name"+i)
					.producttime(LocalDateTime.now())
					.productcontext("상품 정보를 입력할 곳")
					.build();

			productRepository.save(product);
		}
	}

}
