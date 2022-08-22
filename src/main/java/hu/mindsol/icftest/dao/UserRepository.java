/**
 * 
 */
package hu.mindsol.icftest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hu.mindsol.icftest.entity.User;

/**
 * @author Hápy
 *
 */
public interface UserRepository extends JpaRepository<User, Long>  {
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUserName(@Param("username") String username);

}
