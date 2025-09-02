package com.exam.demo.config;


import com.exam.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    // تعریف AuthenticationManager

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // تعریف SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // غیرفعال کردن CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/WEB-INF/views/**","/register").permitAll() // مسیرهای آزاد
                        .anyRequest().authenticated() // بقیه مسیرها نیازمند احراز هویت
                )
                .formLogin(form -> form
                        .loginPage("/login") // صفحه لاگین سفارشی
                        .defaultSuccessUrl("/my-profile", true) // مسیر بعد از ورود موفق
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // مسیر بعد از خروج
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // No encoding, فقط برای تست و شرایط فعلی دیتابیس
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

}