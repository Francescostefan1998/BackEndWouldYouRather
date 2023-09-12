package BackEndWouldYouRather.BackEndWouldYouRather.support.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <b>Password util</b><br>
 * 
 * We recommend that you handle all passwords of your application in a unified
 * manner<br>
 * 
 * We strongly recommend that do not use MD5 encryption, because of it is
 * security limitation.<br>
 * 
 * We strongly recommend that to use {@link BCryptPasswordEncoder}
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 * 
 */
public class PasswordUtil {
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * Encode the given password
	 * 
	 */
	public static String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	/**
	 * Check whether the raw password matches the encoded password
	 * 
	 */
	public static boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public static void main(String args[]) {
		System.out.println(PasswordUtil.encode("admin"));
		System.out.println(PasswordUtil.encode("user"));
	}
}
