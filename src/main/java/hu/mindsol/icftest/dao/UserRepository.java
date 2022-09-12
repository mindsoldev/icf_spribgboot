/**
 * 
 */
package hu.mindsol.icftest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.mindsol.icftest.entity.User;

/**
 * @author Hápy
 *
 */
public interface UserRepository extends JpaRepository<User, Long>  {
	
	public User findByUsername(String username); 

}
