package com.eventshub.backend.servico.seguranca;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import com.eventshub.backend.modelo.RotasModelo;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SegurancaFiltro filtro;

    @Autowired
    private List<RotasModelo> rotasModeloConfig;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("dale");
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
            rotasModeloConfig.forEach(rota -> {
                System.out.println("dale");
                if (rota.getMetodo() != null && rota.getRota() != null
                        && !rota.getRota().isEmpty()) {
                            System.out.println(rota.getMetodo());
                    auth.requestMatchers(rota.getMetodo(), rota.getRota())
                            .hasRole(rota.getAutorizacao());

                }
            });
            auth.requestMatchers(HttpMethod.POST, "/usuario/cadastrar", "/usuario/login")
                  .permitAll();
            auth.requestMatchers(HttpMethod.POST, "/usuario/admin/**").hasRole("ADMIN");
            auth.anyRequest().authenticated();
        })
      .exceptionHandling(exception -> exception
          .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )
      .formLogin(form -> form
          .loginPage("/login")
          .permitAll() 
        )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public List<RotasModelo> rotasModeloConfig() {
        List<RotasModelo> rotas = new ArrayList<>();
        rotas.add(new RotasModelo("/usuario/remover/{id}", HttpMethod.DELETE, "ADMIN"));
        rotas.add(new RotasModelo("/usuario/alterar/{id}", HttpMethod.PUT, "USER"));
        rotas.add(new RotasModelo("/usuario/listar", HttpMethod.GET, "ADMIN"));
        return rotas;
    }

}
