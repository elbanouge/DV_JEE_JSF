package estm.dsic.jee.dal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "login", eager = true)
@SessionScoped
public class User {

	private int login;
	private String name;
	private String email;
	private String password;

	public int getLogin() {
		return login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

}
