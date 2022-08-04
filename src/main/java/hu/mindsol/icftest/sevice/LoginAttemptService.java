/**
 * 
 */
package hu.mindsol.icftest.sevice;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author Hápy
 *
 */
@Component
public class LoginAttemptService {
	
	@Autowired
	private HttpServletRequest httpServletRequest;

	private final int MAX_ATTEMP = 3;
	private LoadingCache<String, Integer> attemptsCache;

	public LoginAttemptService() {
		super();
		attemptsCache = CacheBuilder.newBuilder()
		.expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
			public Integer load(String key) {
				return 0;
			}
		});
	}
	
	public void loginSucceded(String key) {
		attemptsCache.invalidate(key);
	}
	
	public void loginFailed(String key) {
		int attempts = 0;
		try {
			attempts = attemptsCache.get(key);
		} catch (Exception e) {
			attempts = 0;
		}
		attempts++;
		attemptsCache.put(key, attempts);
	}
	
	public boolean isUseCaptcha(String key) {
		boolean useCaptcha = false;
		try {
			useCaptcha = attemptsCache.get(key) >= MAX_ATTEMP;
		} catch (Exception e) {
			useCaptcha = false; 
		}
		return useCaptcha; 
	}
	
	public boolean isUseCaptchaThisRequest() {
		final String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
		boolean useCaptcha = false;
		if (xfHeader == null) {
			useCaptcha = isUseCaptcha(httpServletRequest.getRemoteAddr());
		} else {
			useCaptcha = isUseCaptcha(xfHeader.split(",")[0]);
		}
		return useCaptcha;
	}
	
}
