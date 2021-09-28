package dev.bank.moneymatters.entities;
import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userId;

	private String customerId;
	private String password;
	
	@OneToOne
	private Role role;
	private boolean isFirstTime;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isFirstTime() {
		return isFirstTime;
	}
	public void setFirstTime(boolean isFirstTime) {
		this.isFirstTime = isFirstTime;
	}
	public User(String customerId, String password, Role role) {
		super();
		this.customerId = customerId;
		this.password = password;
		this.role = role;
		this.isFirstTime = true;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
}
