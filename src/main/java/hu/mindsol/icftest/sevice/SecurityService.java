/**
 * 
 */
package hu.mindsol.icftest.sevice;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Hápy
 *
 */
public class SecurityService {
	
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public static String encodePassword(String plainTextPassword) {
		return passwordEncoder.encode(plainTextPassword);
	}

}
