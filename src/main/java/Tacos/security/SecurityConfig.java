package Tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	//这里可以使userDetailsService提供
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new StandardPasswordEncoder("53cr3t");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/design", "/order")
//		.hasRole("ROLE_USER").antMatchers("/","/**").access("permitAll");
//	}

	//使用access函数规定特殊权限，如具备ROLE_USER权限的用户可以在星期二创建新的Taco（Taco Tuesday）p101
@Override
protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
	.antMatchers("/design", "/orders/*")
	.access("hasRole('ROLE_USER')")
	.antMatchers("/", "/**").access("permitAll")
	.and()
	.formLogin().loginPage("/login")
	.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/design", true)
	.and()
	.logout().logoutSuccessUrl("/");
}

	
	//LDAP轻量级目录访问协议p89
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//	}
	
	
	//通过自动装配来让它知道如何访问数据库
//	@Autowired
//	DataSource dataSource;
	
	//基于jdbc用户存储认证
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.usersByUsernameQuery("select username, password, enabled from Users where username=?")
//		.authoritiesByUsernameQuery("select name, authority from UserAuthorities where username=?")
//		//可以自己配置PasswordEncoder接口来实现加密
//		.passwordEncoder(new StandardPasswordEncoder("53cr3t"));
//	}
	
	
	//使用在内存用户存储中定义用户
	//{noop}是指出密码无加密
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("buzz").password("{noop}123456").authorities("ROLE_USER");
//	}
}
