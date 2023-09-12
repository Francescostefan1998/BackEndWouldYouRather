package BackEndWouldYouRather.BackEndWouldYouRather.web.entity;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.converter.GrantedAuthorityConverter;

/**
 * Security user of application
 * 
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = -8590658338942267954L;

	private Long id;
	private String username;
	private String password;

	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	private Collection<? extends GrantedAuthority> authorities;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Column(name = "USERNAME", unique = true, nullable = false, updatable = false, length = 64)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	@Column(name = "PASSWORD", nullable = false, length = 256)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	@Column(name = "ACCOUNT_NON_EXPIRED", nullable = false)
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	@Column(name = "ACCOUNT_NON_LOCKED", nullable = false)
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	@Column(name = "CREDENTIALS_NON_EXPIRED", nullable = false)
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	@Column(name = "ENABLED", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	@Column(name = "AUTHORITIES", nullable = false, length = 2048)
	@Convert(converter = GrantedAuthorityConverter.class)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User entity = (User) o;

		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
