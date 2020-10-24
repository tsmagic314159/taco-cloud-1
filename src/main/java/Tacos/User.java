package Tacos;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	/*
	 * 用于标注主键的生成策略，通过strategy 属性指定。默认情况下，JPA 自动选择一个
	 * 最适合底层数据库的主键生成策略：SqlServer对应identity，MySQL 对应 auto increment。
	 * –IDENTITY：采用数据库ID自增长的方式来自增主键字段，Oracle 不支持这种方式； 
	 * –AUTO： JPA自动选择合适的策略，是默认选项； 
	 * –SEQUENCE：通过序列产生主键，通过@SequenceGenerator 注解指定序列名，MySql不支持这种方式 
	 * –TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private final String username;
	private final String password;
	private final String fullname;
	private final String street;
	private final String city;
	private final String zip;
	private final String phoneNumber;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
