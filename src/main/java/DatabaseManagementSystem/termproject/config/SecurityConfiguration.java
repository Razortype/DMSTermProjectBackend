package DatabaseManagementSystem.termproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final CookieService cookieService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/institute/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/university/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/subject/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/related-keyword/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/user/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/thesis/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/manager/**").hasAnyAuthority("MANAGER", "ADMIN")
                .requestMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN")

                // hand fixed
                .requestMatchers("/api/v1/profession").permitAll()

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> {
                    cookieService.setRefreshCookieNull();
                    SecurityContextHolder.clearContext();
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "refresh-token");
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173", "https://dbm-thesis.vercel.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
