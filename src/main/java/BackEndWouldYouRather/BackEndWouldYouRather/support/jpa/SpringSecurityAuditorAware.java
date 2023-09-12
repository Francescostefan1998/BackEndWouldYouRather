package BackEndWouldYouRather.BackEndWouldYouRather.support.jpa;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.User;

/**
 * <b>Spring Security Auditor Aware</b> <BR>
 * In case you use either {@link @CreatedBy} or {@link @LastModifiedBy} , the
 * auditing infrastructure somehow needs to where to retrieve the current user.
 * Here we retrieve current user from SecurityContextHolder.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public class SpringSecurityAuditorAware implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		return ((User) authentication.getPrincipal());
	}
}
