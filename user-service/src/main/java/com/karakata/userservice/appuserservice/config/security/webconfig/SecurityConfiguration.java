package com.karakata.userservice.appuserservice.config.security.webconfig;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;




@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final  String[] WHITE_LIST_URL = {"http://"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST_URL)
                        .permitAll().anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("oauth2/authorization/api-client-oidc"))
                .oauth2Client(Customizer.withDefaults())
                .sessionManagement(manager ->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;
        return httpSecurity.build();
    }
}
