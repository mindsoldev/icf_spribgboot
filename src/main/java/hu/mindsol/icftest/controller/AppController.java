/**
 * 
 */
package hu.mindsol.icftest.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.apiclub.captcha.Captcha;
import hu.mindsol.icftest.dao.UserRepository;
import hu.mindsol.icftest.entity.User;
//import hu.mindsol.icftest.entity.UserForView;
import hu.mindsol.icftest.security.MyUserDetails;
import hu.mindsol.icftest.sevice.CaptchaService;
import hu.mindsol.icftest.sevice.LoginAttemptService;

/**
 * @author Hápy
 *
 */
@Controller
public class AppController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	LoginAttemptService loginAttemptService;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		long loggedInUsersID = ((MyUserDetails)authentication.getPrincipal()).getId();
		List<User> listUsers = userRepository.findAll();

		listUsers = listUsers.stream()
        .filter( user -> user.getId() != loggedInUsersID )  
        .collect(Collectors.toList());
		
		model.addAttribute("listUsers", listUsers);
		return "index";
	}
	
	@RequestMapping("/adminpage")
	public String viewAdminPage(Model model) {
		return "adminpage";
	}
	
	@RequestMapping("/contentpage")
	public String viewcontentPage(Model model) {
		return "contentpage";
	}
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
	
	@GetMapping("/login")
    public String login(Model model, String error, String logout, HttpSession session, HttpServletRequest request) {
		boolean mustcaptcha = loginAttemptService.isUseCaptchaThisRequest();
//		boolean mustcaptcha = false;
		model.addAttribute("mustCaptcha", mustcaptcha);
		Object captchaAnswer = request.getSession().getAttribute("captchaAnswer");
		if (captchaAnswer == null) {
			Captcha captcha = CaptchaService.createCaptcha(240, 50);
			String captchaImg = CaptchaService.encodeCaptcha(captcha);
			model.addAttribute("captchaImg", captchaImg);
			model.addAttribute("captcha", "");
			session.setAttribute("captchaImg", captchaImg);
			session.setAttribute("mustCaptcha", mustcaptcha);
			session.setAttribute("captchaAnswer", captcha.getAnswer());
		} else {
			model.addAttribute("captchaImg", session.getAttribute("captchaImg"));
			model.addAttribute("captcha", "");
			session.setAttribute("mustCaptcha", mustcaptcha);
		}
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }
}
