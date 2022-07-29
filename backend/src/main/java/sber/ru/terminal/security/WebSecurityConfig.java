package sber.ru.terminal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sber.ru.terminal.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder cryptPasswordEncoder;

//    private final
//    @Autowired
    private final JWTFilter jwtFilter;

    @Autowired
    public WebSecurityConfig(UserService userService,
                             BCryptPasswordEncoder cryptPasswordEncoder, JWTFilter jwtFilter) {
        this.userService = userService;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/client/**").hasAuthority("CLIENT")
//                .antMatchers("/operator/**").hasAuthority("OPERATOR")
//                .antMatchers("/all/**").permitAll()
//                .anyRequest().authenticated()
//                .and().httpBasic();

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("api/authenticate").permitAll()
                .antMatchers("/all").permitAll()
                .antMatchers("/testclient/**").permitAll()
                .and().authorizeRequests().antMatchers("/client/**").hasAnyAuthority("CLIENT")
                .and().authorizeRequests().antMatchers("/operator/**").hasAuthority("OPERATOR")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(cryptPasswordEncoder);
    }



}
