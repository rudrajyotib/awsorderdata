package org.learn.aws.orderdata;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/org/learn/aws/orderdata/persistence/spring-h2-application-context-test.properties")
class OrderdataApplicationTests {

	@Test
	void contextLoads() {
	}

}
