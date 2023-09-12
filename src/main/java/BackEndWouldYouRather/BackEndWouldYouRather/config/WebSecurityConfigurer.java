package BackEndWouldYouRather.BackEndWouldYouRather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import BackEndWouldYouRather.BackEndWouldYouRather.support.authentication.CustomAuthenticationProvider;
import BackEndWouldYouRather.BackEndWouldYouRather.web.service.UserService;

import org.springframework.boot.autoconfigure.security.SecurityProperties;

/**
 * <b>Security configurations</b><br>
 * 
 * You can configure your application security features by this class when .yml
 * file configurations can not meet your requirements.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	private @Autowired UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/signin").permitAll()
			.and().authorizeRequests().anyRequest().authenticated()
			.and().formLogin().loginPage("/signin")
			.and().authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
			.and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
			.and().authorizeRequests().antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
			.and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
			.and().cors().and().csrf().ignoringAntMatchers("/api/**")
			.and().httpBasic();
	}

	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
		CustomAuthenticationProvider ssoAuthenticationProvider = new CustomAuthenticationProvider(userService);
		return ssoAuthenticationProvider;
	}
}
