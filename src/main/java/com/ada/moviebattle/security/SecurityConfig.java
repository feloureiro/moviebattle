package com.ada.moviebattle.security;

import com.ada.moviebattle.security.jwt.AuthEntryPointJwt;
import com.ada.moviebattle.security.jwt.AuthenticationFilter;
import com.ada.moviebattle.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthenticationFilter authenticationJwtTokenFilter() {
        return new AuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
//                .headers()
//                .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy","default-src 'self'; " +
//                        "connect-src *; " +
//                        "child-src 'self' http://localhosts:8080/;"))
//                .addHeaderWriter(new StaticHeadersWriter("Permissions-Policy", "geolocation=(self), fullscreen=(self)"))
//                .addHeaderWriter(new StaticHeadersWriter("Referrer-Policy", "same-origin")).and()
                .csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .headers().frameOptions().disable().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers("/h2/**")
                .antMatchers("/token/**")
                .antMatchers("/auth/**")
                .antMatchers("/v3/api-docs/**")
                .antMatchers("/openapi/**")
                .antMatchers("/game/load-data/**", "/game/ranking/**")
                .antMatchers("/swagger-ui/**");
    }
}