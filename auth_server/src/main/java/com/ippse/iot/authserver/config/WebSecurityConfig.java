package com.ippse.iot.authserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
/*@EnableWebSecurity*/
/*@Order(-10)*/
//第一次启动打开这个order，才能显示login界面，然后必须关闭这个order，才能正确返回userinfo结果
//可能授权服务器和资源服务器不能放在一起，必须分开
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Autowired
	private CustomLogoutSuccessHandler logoutSuccessHandler;

//	@Autowired
//	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private QQLoginAuthenticationSecurityConfig qqLoginAuthenticationSecurityConfig;

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private final UserDetailsService userDetailsService;
	
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		log.info("开始实例化WebSecurityConfig");
		this.userDetailsService = userDetailsService;
	}
	
	/*@Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }*/
	
	private AuthenticationEntryPoint authenticationEntryPoint() {
	    return new AuthenticationEntryPoint() {
	        // You can use a lambda here
	        @Override
	        public void commence(HttpServletRequest aRequest, HttpServletResponse aResponse,
	               AuthenticationException aAuthException) throws IOException, ServletException {
	            aResponse.sendRedirect("?redirect_uri=localhost:9001/home");
	        }
	    };
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("开始WebSecurityConfig的HttpSecurity");

		http.cors().and()
				//.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/actuator/**", "/login", "/exit", "/login/**","/oauth/**", "/favicon.ico", "/swagger/**", "/").permitAll()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated()
				//.and().httpBasic()
//				.and().formLogin().loginPage("/login").defaultSuccessUrl("http://iot2.yunep.com/web/admin/index", true).failureUrl("/login-error").permitAll()//.defaultSuccessUrl("/", true)
				.and().formLogin().loginPage("/login").failureUrl("/login-error").permitAll()//.defaultSuccessUrl("/", true)
				.and()
				.logout().logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.csrf().disable().apply(qqLoginAuthenticationSecurityConfig);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("开始WebSecurityConfig的WebSecurity");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		web.ignoring().antMatchers("/actuator/health","/favicon.ico", "/css/**", "/js/**","/images/**", "/fonts/**","/dist/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("开始WebSecurityConfig的AuthenticationManagerBuilder");
		//如果将此设置为
		//auth.parentAuthenticationManager(authenticationManager);
		//则出现无限循环问题，暂时设置为null
		auth.parentAuthenticationManager(null);
		auth.userDetailsService(userDetailsService);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info("开始WebSecurityConfig的authenticationManagerBean");
		return super.authenticationManagerBean();
	}

/*	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "DELETE", "OPTION"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/


	/*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
	
}

