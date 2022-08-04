/**
 * 
 */
package hu.mindsol.icftest.sevice;

import hu.mindsol.icftest.entity.Role;

/**
 * @author Hápy
 *
 */
public class RoleService {

	public static final Role ROLE_ADMIN = new Role("ADMIN", "Administrator");
	public static final Role ROLE_EDITOR = new Role("EDITOR", "Content editor");
	public static final Role ROLE_USER = new Role("USER", "Logged in user");

}
