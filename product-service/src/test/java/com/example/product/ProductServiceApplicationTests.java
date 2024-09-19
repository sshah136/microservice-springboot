package com.example.product;

import com.example.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
					{
					    "name":"Test",
					    "description": "test",
					    "price":10000
					}
				""";


		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/product/create")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Test"))
				.body("description", Matchers.equalTo("test"))
				.body("price", Matchers.equalTo(10000));
	}

		@Test
		void shouldGetProducts()
		{

			RestAssured.given()
					.contentType("application/json")
					.when()
					.get("api/product/products")
					.then()
					.statusCode(200)
					.body("$", Matchers.everyItem(Matchers.allOf(
							Matchers.hasKey("id"),
							Matchers.hasKey("name"),
							Matchers.hasKey("description"),
							Matchers.hasKey("price")
					)));
		}



}
