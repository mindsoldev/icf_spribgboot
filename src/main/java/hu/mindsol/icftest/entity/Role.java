/**
 * 
 */
package hu.mindsol.icftest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Hápy
 *
 */
@Entity
@Table(name = "icf_roles")
public class Role {
	@Id
	@Column(name = "role_id")
	private String id;
	private String name;
	
	protected Role() {
	}
	
	public Role(String id, String name) {
		this.id = id;
		this.name = name;
	}

//	public Role(String name) {
//		this.name = name;
//	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

}
