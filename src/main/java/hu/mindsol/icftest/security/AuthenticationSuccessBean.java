/**
 * 
 */
package hu.mindsol.icftest.security;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import hu.mindsol.icftest.dao.UserRepository;
import hu.mindsol.icftest.entity.User;
import hu.mindsol.icftest.sevice.LoginAttemptService;

/**
 * @author Hápy
 *
 */
@Component
public class AuthenticationSuccessBean implements ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private LoginAttemptService loginAttemptService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		MyUserDetails userDetails = ((MyUserDetails)event.getAuthentication().getPrincipal());
		User user = userDetails.getUser();
		String sessionId = ((WebAuthenticationDetails)((UsernamePasswordAuthenticationToken)event.getSource()).getDetails()).getSessionId();
		user.setSession(sessionId);
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		
		final String xfHeadr = httpServletRequest.getHeader("X-Forwarded-For");
		if (xfHeadr == null) {
			loginAttemptService.loginSucceded(httpServletRequest.getRemoteAddr());
		} else {
			loginAttemptService.loginSucceded(xfHeadr.split(",")[0]);
		}
	}

}
