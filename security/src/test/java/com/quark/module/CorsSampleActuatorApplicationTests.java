package com.quark.module;/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for cors preflight requests to management endpoints.
 *
 * @author Madhura Bhave
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("cors")
public class CorsSampleActuatorApplicationTests {

	private TestRestTemplate testRestTemplate;

	@Autowired
	private ApplicationContext applicationContext;

	@Before
	public void setUp() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		LocalHostUriTemplateHandler handler = new LocalHostUriTemplateHandler(
				this.applicationContext.getEnvironment(), "http");
		builder = builder.uriTemplateHandler(handler);
		this.testRestTemplate = new TestRestTemplate(builder);
	}

	@Test
	public void endpointShouldReturnUnauthorized() {
		ResponseEntity<?> entity = this.testRestTemplate.getForEntity("/actuator/env",
				Map.class);
//		路径认证未通过 401 unauthorized
		System.out.println("--------------------"+entity.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
	}

	@Test
	public void preflightRequestToEndpointShouldReturnOk() throws Exception {
		RequestEntity<?> healthRequest = RequestEntity.options(new URI("/foo"))
				.header("Origin", "http://localhost:8080")
				.header("Access-Control-Request-Method", "GET").build();
		ResponseEntity<?> exchange = this.testRestTemplate.exchange(healthRequest,
				Map.class);
		System.out.println("-------------"+exchange.getStatusCode().equals(HttpStatus.OK));
	}

	@Test
	public void preflightRequestWhenCorsConfigInvalidShouldReturnForbidden()
			throws Exception {
//		URI认证通过
		RequestEntity<?> entity = RequestEntity.options(new URI("/foo"))
//      错误端口
				.header("Origin", "http://localhost:9095")
				.header("Access-Control-Request-Method", "GET").build();
		ResponseEntity<byte[]> exchange = this.testRestTemplate.exchange(entity,
				byte[].class);
//		返回403 FORBIDDEN
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
		System.out.println("------------"+exchange.getStatusCode().equals(HttpStatus.FORBIDDEN));
	}

}
