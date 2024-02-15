package org.example.tacocloud.config;

import com.nimbusds.jose.jwk.source.JWKSource;

import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.tacocloud.data.UserRepository;
import org.example.tacocloud.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.util.ArrayList;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
       return username -> {
           User user = userRepo.findByUsername(username);
           if(user != null) return user;
           throw new UsernameNotFoundException("User '" + username + "' not found");
       };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(requests -> requests
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/design", "/orders", "/orders/*").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/tacos/*").hasAuthority("SCOPE_writeTacos")
                        .requestMatchers(HttpMethod.DELETE, "/api/tacos/*").hasAuthority("SCOPE_deleteTacos")
                        .requestMatchers("/", "/**", "/login/**").permitAll()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .defaultSuccessUrl("/design"))
                .csrf(request -> request
                        .disable())
                .oauth2Login(request -> request
                        .loginPage("/login").successHandler(this::onAuthenticationSuccess))
                .logout(logout ->logout
                        .logoutSuccessUrl("/design"))
                .headers(headers -> headers
                        .frameOptions(frameOpt -> frameOpt.disable()))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                                .jwkSetUri("http://localhost:9000/oauth2/jwks")))
                .build();
    }

    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {
        // 在这里可以获取用户信息，并根据需要给用户授予角色
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 根据需要处理用户信息，例如从第三方登录获取的信息中提取用户角色
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 创建新的Authentication对象，包含用户信息和角色
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication.getCredentials(), authorities);

        // 将新的Authentication对象设置到SecurityContextHolder中
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        // 重定向到默认的登录成功页面
        response.sendRedirect("/");
    }
}
