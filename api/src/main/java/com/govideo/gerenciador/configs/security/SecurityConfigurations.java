package com.govideo.gerenciador.configs.security;

import com.govideo.gerenciador.repositories.UsuarioRepository;
import com.govideo.gerenciador.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@Profile("dev")
public class SecurityConfigurations {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .antMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.GET, "/usuarios/buscarPorStatus/*").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.PUT, "/usuarios/resetarSenha/*").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.PUT, "/usuarios/alterarNome/*").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.DELETE, "/usuarios/*").hasRole("ADMINISTRADOR")

                .antMatchers(HttpMethod.POST, "/equipamentos").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.PUT, "/equipamentos/*").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.DELETE, "/equipamentos/*").hasRole("ADMINISTRADOR")

                .antMatchers(HttpMethod.GET, "/emprestimos").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.GET, "/emprestimos/encerrados").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.GET, "/emprestimos/vigentes").hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.POST, "/emprestimos/*").hasRole("COLABORADOR")

                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**.html", "/v3/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

}
