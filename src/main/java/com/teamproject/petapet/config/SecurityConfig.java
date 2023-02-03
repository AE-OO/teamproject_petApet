package com.teamproject.petapet.config;

import com.teamproject.petapet.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * 장사론 22.10.19 작성
 * 장사론 22.10.20 수정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                //시큐리티 세션사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//인증실패
                .accessDeniedHandler(jwtAccessDeniedHandler)//인가실패

                //로그아웃 쿠키에 넣은 토큰 삭제
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies(JwtAuthenticationFilter.AUTHORIZATION_HEADER)

                // 요청에 대한 사용권한 체크
                .and()
                .authorizeRequests()
                //인증 없이 접근 가능
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/message/**").hasRole("MEMBER")
                .antMatchers("/member/**","/community/insert","/community/update").hasAnyRole("MEMBER","ADMIN")
                .antMatchers("/company/**","/member").hasAnyRole("COMPANY","ADMIN")
                .antMatchers("/community/memberWriting/**").authenticated()
                .antMatchers("/member/**").hasAnyRole("MEMBER","ADMIN")
                .antMatchers("/company/**","/inquiry/manageInquiry").hasAnyRole("COMPANY","ADMIN")
                .antMatchers("/login","/join","/sms/send","/companyJoin").permitAll()
                .antMatchers("/product/**","/community/**").permitAll()
                .antMatchers("/").permitAll()
                //나머지 경로는 인증 없이 접근 불가
//                .anyRequest().authenticated()

                // JwtSecurityConfig 적용
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "static/**","/css/**","fonts.Nunito/**","/img/**"
                ,"/js/**","/lib/**","/vendors/**","/error","/favicon.ico"
        );
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}