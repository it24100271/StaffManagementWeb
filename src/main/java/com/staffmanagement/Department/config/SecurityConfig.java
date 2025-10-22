////package com.staffmanagement.Department.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                // Disable security for now to test if everything works
////                .authorizeHttpRequests(auth -> auth
////                        .anyRequest().permitAll()
////                )
////                // Disable CSRF for API endpoints
////                .csrf(csrf -> csrf.disable())
////                // Disable form login for now
////                .formLogin(form -> form.disable());
////
////        return http.build();
////    }
////
////    // UNCOMMENT THIS AFTER TESTING TO RE-ENABLE SECURITY:
////    /*
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////            .authorizeHttpRequests(auth -> auth
////                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/dashboard.html").permitAll()
////                .requestMatchers("/api/**").permitAll() // or .authenticated() if you want to protect APIs
////                .anyRequest().authenticated()
////            )
////            .formLogin(form -> form
////                .loginPage("/login")
////                .defaultSuccessUrl("/dashboard.html", true)
////                .permitAll()
////            )
////            .logout(logout -> logout
////                .logoutSuccessUrl("/login?logout")
////                .permitAll()
////            )
////            .csrf(csrf -> csrf.disable()); // Keep CSRF disabled for REST APIs
////
////        return http.build();
////    }
////    */
////}
//
//package com.staffmanagement.Department.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )
//                .csrf(csrf -> csrf.disable()); // Disable CSRF for API endpoints (enable in production!)
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("kanishka")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

package com.staffmanagement.Department.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                        .requestMatchers("/api/**").permitAll() // Allow API endpoints
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for development

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("kanishka")
                .password(passwordEncoder().encode("1234"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}