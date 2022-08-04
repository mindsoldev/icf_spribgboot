/**
 * 
 */
package hu.mindsol.icftest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import hu.mindsol.icftest.dao.UserRepository;
import hu.mindsol.icftest.entity.User;

/**
 * @author Hápy
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Coult not find user");
		}
		return new MyUserDetails(user);
	}

}
