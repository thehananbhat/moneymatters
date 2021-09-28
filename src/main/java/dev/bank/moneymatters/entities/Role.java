package dev.bank.moneymatters.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Role {
	@Id
	@Column
	private Integer roleId;
	@Column
	private String name;
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(int roleId, String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
