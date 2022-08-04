/**
 * 
 */
package hu.mindsol.icftest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import hu.mindsol.icftest.dao.RoleRepository;
import hu.mindsol.icftest.dao.UserRepository;
import hu.mindsol.icftest.entity.Role;
import hu.mindsol.icftest.entity.User;
import hu.mindsol.icftest.sevice.RoleService;
import hu.mindsol.icftest.sevice.SecurityService;

/**
 * @author Hápy
 *
 */
@Component
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

	private static final Logger  log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
        Role roleUser = RoleService.ROLE_USER;
        Role roleAdmin = RoleService.ROLE_ADMIN;
        Role roleEditor = RoleService.ROLE_EDITOR;
        // Only Java 9 :(
//        roleRepository.saveAll(List.of(roleUser, roleAdmin, roleEditor));
        roleRepository.saveAll(Arrays.asList(roleUser, roleAdmin, roleEditor));
        
        User user = null;
        User savedUser = null;
        {		
	        user = new User("admin", SecurityService.encodePassword("admin"));
	        user.setRoles(new HashSet<Role>(Arrays.asList(new Role[] {roleAdmin, roleEditor, roleUser})));
	        user.setEnabled(true);
	        savedUser = userRepository.save(user);
        }
        {
	        user = new User("user1", SecurityService.encodePassword("user1"));
	        user.setRoles(new HashSet<Role>(Arrays.asList(new Role[] {roleEditor, roleUser})));       
	        user.setEnabled(true);
	        savedUser = userRepository.save(user);
        }
        {
        	user = new User("user2", SecurityService.encodePassword("user2"));
        	user.setRoles(new HashSet<Role>(Arrays.asList(new Role[] {roleEditor})));       
	        user.setEnabled(true);
        	savedUser = userRepository.save(user);
        }
        {
        	user = new User("user3", SecurityService.encodePassword("user3"));
        	user.setRoles(new HashSet<Role>(Arrays.asList(new Role[] {roleUser})));       
	        user.setEnabled(true);
        	savedUser = userRepository.save(user);
        }
		
		List<User> users = (List<User>)userRepository.findAll();
		log.info("All Users: " + users);
	}
	

}
