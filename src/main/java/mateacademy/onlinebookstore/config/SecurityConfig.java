package mateacademy.onlinebookstore.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .cors(AbstractHttpConfigurer::disable)
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(
                                auth -> auth
                                        .requestMatchers(HttpMethod.POST, "/api/auth/registration")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**",
                                                "/swagger-ui.html")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/books",
                                                " /api/books/{id}", "/api/books/search")
                                        .hasRole("USER")
                                        .requestMatchers(HttpMethod.POST, "/api/books")
                                        .hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/api/books/{id}")
                                        .hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/api/books/{id}")
                                        .hasRole("ADMIN")
                                        .anyRequest()
                                        .authenticated())
                        .httpBasic(withDefaults())
                        .userDetailsService(userDetailsService)
                        .build();
    }
}
