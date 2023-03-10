package com.example.back.base.config;

//import org.checkerframework.checker.units.qual.h;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import com.example.back.base.jwt.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().httpBasic().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/user/**", "/post/**", "/comment/**").permitAll().anyRequest()
				.authenticated();

		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	}

}
