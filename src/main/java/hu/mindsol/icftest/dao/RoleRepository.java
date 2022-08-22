/**
 * 
 */
package hu.mindsol.icftest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.mindsol.icftest.entity.Role;

/**
 * @author Hápy
 *
 */
public interface RoleRepository extends JpaRepository<Role, String> {
	@Query("SELECT r FROM Role r WHERE r.name = ?1")
	public Role findByName(String name);
}
