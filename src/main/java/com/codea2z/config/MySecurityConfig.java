package com.codea2z.config;

import com.codea2z.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.file.Path;
import java.nio.file.Paths;

@EnableWebSecurity
@Configuration
//@EnableWebMvc
//@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig  extends WebSecurityConfigurerAdapter  {
	
	//public static final String[] PUBLIC_URLS = {"/generate-token", "/user/"}; 
	

	
	
	

	
	  @Autowired 
	  private JwtAuthenticationEntryPoint unauthorizedHandler;
	  
	  @Autowired 
	  private JwtAuthenticationFilter jwtAuthenticationFilter;
	  
	  
	  @Autowired 
	  private UserDetailsServiceImpl userDetailsServiceImpl;
	  
	  @Override
	  @Bean 
	  public AuthenticationManager authenticationManagerBean() throws Exception {
		  return super.authenticationManagerBean();
	  }
	 
	
//	@Bean
//	public SecurityFilterChain securatryFilterChain(HttpSecurity http) throws Exception {
//		
//
//		 http
//         	.csrf()
//            .disable()
//            .cors()
//            .disable()
//            .authorizeRequests()
//            .antMatchers("/generate-token", "/user/").permitAll()
//            .antMatchers(HttpMethod.OPTIONS).permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		 http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//		 
//		return http.build();
// 
//		
//	}
	 

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    


    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/generate-token", "/user/").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
