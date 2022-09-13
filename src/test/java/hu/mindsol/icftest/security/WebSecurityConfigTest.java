/**
 * 
 */
package hu.mindsol.icftest.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import hu.mindsol.icftest.entity.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@CsvSource({"admin,admin", "user1,user1", "user2,user2", "user3,user3"})
    public void loginWithValidUserThenAuthenticated(String user, String password) throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user(user)
                .password(password);
        mockMvc.perform(login)
                .andExpect(authenticated().withUsername(user));
    }

	@Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");
        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }

    @Test
    public void accessUnsecuredResourceThenOk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/adminpage"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void accessSecuredResourceOnlyAuthenticatedThenOk() throws Exception {
    	UserDetails userDetails = new MyUserDetails(new User("indifferent", null));
    	mockMvc.perform(get("/index")
    			.with(SecurityMockMvcRequestPostProcessors.user(userDetails)))
                .andExpect(status().isOk());
    }
	    
    @ParameterizedTest
    @CsvSource({"/adminpage,ADMIN", "/contentpage,EDITOR"})
    public void accessSecuredByRoleResourceAUthorizedThenOk(String url, String role) throws Exception {
    	mockMvc.perform(get(url)
    					.with(SecurityMockMvcRequestPostProcessors.user("indifferent")
    					.authorities(new SimpleGrantedAuthority(role))))
    	.andExpect(status().isOk());
    }
    
    @ParameterizedTest
	@CsvSource({"/adminpage,EDITOR", "/adminpage,USER", "/contentpage,ADMIN", "/contentpage,USER"})
    public void accessSecuredByRoleResourceAuthenticatedThenUnAuthorized(String url, String role) throws Exception {
    	mockMvc.perform(get(url)
    						.with(SecurityMockMvcRequestPostProcessors.user("indifferent")
    						.authorities(new SimpleGrantedAuthority(role))))
    					.andExpect(status().is(403));
    }
    
}
