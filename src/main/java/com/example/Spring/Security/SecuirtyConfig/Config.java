package com.example.Spring.Security.SecuirtyConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config {


    public UserDetailsService userDetailsService;

    public Config(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public AuthenticationProvider authenticationProvider() { // this method is used to authenticate the user
        // this is an interface and DaoAuthenticationProvider is a class that implements this interface
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        // The setUserDetailsService(userDetailsService) method in the DaoAuthenticationProvider is used to tell the provider how to retrieve user details during the authentication process.
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
      //  What does return provider mean?

             //   When you return provider, you are:

     //   Providing the configured DaoAuthenticationProvider bean to Spring Security.
      //  Spring Security uses this AuthenticationProvider during the authentication process.

              //   It is a tool (not an authenticated object) that Spring Security uses to authenticate users.
    }

    // HttpSecurity This is a class used to configure web-based security for specific HTTP requests.
    // It allows you to define which endpoints should be secured,
    // what kind of authentication should be used, and other security-related configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable); // disable csrf
        http.authorizeHttpRequests(request -> request.requestMatchers("/register","/login").permitAll() // permit all requests to register
                .anyRequest()
                .authenticated()); //  any request should be authenticated
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
       // The http.build() method in the HttpSecurity configuration is used to build
        // and return a SecurityFilterChain object. This method finalizes the configuration
        // of the HttpSecurity object and creates a SecurityFilterChain that applies the
        // defined security rules to incoming HTTP requests.
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   //  Retrieves the AuthenticationManager from the provided AuthenticationConfiguration.
     // param config the AuthenticationConfiguration from which to retrieve the AuthenticationManager
     // return the AuthenticationManager used by Spring Security to handle authentication requests
     // throws exception if an error occurs while retrieving the AuthenticationManager

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }




}
