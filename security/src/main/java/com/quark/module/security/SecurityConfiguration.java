package com.quark.module.security;/*
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


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@SuppressWarnings("deprecation")
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		List<UserDetails>  userDetailList = new ArrayList();
		userDetailList.add(User.withUsername("user").password("password")
				.authorities("ROLE_USER").build());
		userDetailList.add(User.withUsername("admin").password("admin")
				.authorities("ROLE_ACTUATOR", "ROLE_USER").build());
		return new InMemoryUserDetailsManager(userDetailList);
		// TODO: 2018/2/27/027
//		多种方式认证用户 （数据库查询）
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		spring boot 1.5.9版本支持
//		http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
		http
				.authorizeRequests()
				.mvcMatchers("/foo/**")
				.permitAll()
				.mvcMatchers("/skip")
				.hasAnyRole("SUPERUSER")
				.anyRequest()
				.authenticated().and()
				.httpBasic();
//		需要 spring boot 2.0.0buildsnapshot 版本
//		http.authorizeRequests()
//				.requestMatchers(EndpointRequest.to("health", "info")).permitAll()
//				.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
//				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//				.antMatchers("/foo").permitAll()
//				.antMatchers("/**").hasRole("USER")
//				.and()
//			.cors()
//				.and()
//			.httpBasic();
	}

}
