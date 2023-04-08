package com.example.TicketGuru;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.TicketGuru.service.CorsFilter;
import com.example.TicketGuru.service.DetailsService;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

        @Autowired
        private DetailsService userDetailsService;

        @Autowired
        private CorsFilter corsFilter;

        // URLit, joihin ei tarvita mitään tunnareita
        private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
                        new AntPathRequestMatcher("/h2-console/**"),
                        new AntPathRequestMatcher("/**"),
                        new AntPathRequestMatcher("/login"),
                        
        };

        // URLit, joihin pääsy vain ADMIN-roolilla
        private static final AntPathRequestMatcher[] ADMIN_LIST_URLS = {
                        new AntPathRequestMatcher("/appusers"),
                        new AntPathRequestMatcher("/appusers/**"),
                        new AntPathRequestMatcher("/appuserroles"),
                        new AntPathRequestMatcher("/appuserroles/**"),
                        new AntPathRequestMatcher("/roles"),
                        new AntPathRequestMatcher("/roles/**"),

        };

        // URLit, joihin pääsy kaikilla rooleilla
        // Määritetään controller-luokkiin tarkemmat vaatimukset
        // Esim GET /events/1 pitäisi toimia kaikilla rooleilla, mutta DELETE vaan ADMIN
        private static final AntPathRequestMatcher[] AUTH_LIST_URLS = {
                        new AntPathRequestMatcher("/tickets"),
                        new AntPathRequestMatcher("/tickets/**"),
                        new AntPathRequestMatcher("/events"),
                        new AntPathRequestMatcher("/events/**"),
                        new AntPathRequestMatcher("/postalcodes"),
                        new AntPathRequestMatcher("/postalcodes/**"),
                        new AntPathRequestMatcher("/venues"),
                        new AntPathRequestMatcher("/venues/**"),
                        new AntPathRequestMatcher("/transactions"),
                        new AntPathRequestMatcher("/transactions/**"),
                        new AntPathRequestMatcher("/tickettypes"),
                        new AntPathRequestMatcher("/tickettypes/**"),
                        new AntPathRequestMatcher("/eventtickettypes"),
                        new AntPathRequestMatcher("/eventtickettypes/**"),
                        new AntPathRequestMatcher("/resources/public/**"),

        };

        // Sallitaan vapaa pääsy WHITE_LIST_URL -osotteisiin

        // MUTTA: "You are asking Spring Security to ignore Ant [pattern='/events'].
        // This is not recommended -- please use permitAll via
        // HttpSecurity#authorizeHttpRequests instead."

        // Ainakaan äsken en saanu toimimaan yllä mainitulla tavalla
        // Metoditason preAuthorize-ym. hommat ei toimi jos WHITE_LISTillä
        // Pidetään nyt vaan H2 listalla ja kaikki muut endpointit vaatii tunnarit
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                return (web) -> web.ignoring()
                                .requestMatchers(WHITE_LIST_URLS);
        }

        @Bean
        UrlBasedCorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests()
                                .requestMatchers(AUTH_LIST_URLS).permitAll()
                                .requestMatchers(ADMIN_LIST_URLS).hasAuthority("ADMIN")
                                .and()
                                .authorizeHttpRequests().requestMatchers(AUTH_LIST_URLS)
                                .hasAnyAuthority("CLERK", "ADMIN", "TICKET_INSPECTOR")
                                .and()
                                .headers().frameOptions().disable()
                                .and()
                                .formLogin().loginPage("/login").defaultSuccessUrl("/", true)
                                .and()
                                .logout().permitAll().logoutSuccessUrl("/")
                                .and()
                                .httpBasic();

                http.csrf().disable();
                http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
                return http.build();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }
}
