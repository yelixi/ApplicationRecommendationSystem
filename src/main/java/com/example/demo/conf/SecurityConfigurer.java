package com.example.demo.conf;

import com.example.demo.handler.TigerLogoutSuccessHandler;
import com.example.demo.model.RestResult;
import com.example.demo.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/3/9 9:08
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final TigerLogoutSuccessHandler logoutSuccessHandler = new TigerLogoutSuccessHandler("/");

    private static final String[] NO_AUTH_LIST = {
            /*"/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/user/**",
            "/userInfo/**",
            "/school/**"*/
            "/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and().formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("code")
                //登录失败处理，返回json
                .failureHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.FORBIDDEN, RestResult.error(403, e.getMessage())))
                //登录成功处理，返回json
                .successHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.OK, RestResult.success("登录成功")))
                .permitAll()
                .and().exceptionHandling()
                //请求登录处理，改变默认跳转登录页
                .authenticationEntryPoint((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.UNAUTHORIZED, RestResult.error(401, "请先登录")))
                //没有权限访问
                .accessDeniedHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.FORBIDDEN, RestResult.error(403, "抱歉，你当前的身份无权访问")))
                .and().sessionManagement().maximumSessions(1)
                .expiredSessionStrategy(s -> ResponseUtil.restResponse(s.getResponse(), HttpStatus.FORBIDDEN, RestResult.error(499, "您的账号在别的地方登录，当前登录已失效")))
                .and()
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID").permitAll()
                .and().authorizeRequests().antMatchers(NO_AUTH_LIST).permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
