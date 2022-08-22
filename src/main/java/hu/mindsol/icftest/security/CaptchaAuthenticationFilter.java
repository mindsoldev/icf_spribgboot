/**
 * 
 */
package hu.mindsol.icftest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * @author Hápy
 *
 */
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String processUrl;

	public CaptchaAuthenticationFilter(String defaultFilterProcessesUrl, String failureUrl) {
        super(defaultFilterProcessesUrl);
        this.processUrl = defaultFilterProcessesUrl;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        if(processUrl.equals(req.getServletPath()) && "POST".equalsIgnoreCase(req.getMethod())){
            Object mustCaptchaO = req.getSession().getAttribute("mustCaptcha");
            boolean mustCaptcha = mustCaptchaO != null ? (boolean)mustCaptchaO : false;
            if (mustCaptcha) {
            	String captchaAnswer = req.getSession().getAttribute("captchaAnswer").toString();
            	String captcha = request.getParameter("captcha");
            	if (captcha != null && !captcha.equals(captchaAnswer)){
                    req.getSession().removeAttribute("mustCaptcha");
                    req.getSession().removeAttribute("captchaAnswer");
            		unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException("Wrong verification code."));
            		return;
            	}
			}
            req.getSession().removeAttribute("mustCaptcha");
            req.getSession().removeAttribute("captchaAnswer");
        }
        chain.doFilter(request, response);
    }

    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }
	
}
