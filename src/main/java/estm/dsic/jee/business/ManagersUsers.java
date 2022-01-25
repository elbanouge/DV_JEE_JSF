package estm.dsic.jee.business;

import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import estm.dsic.jee.dal.User;

@ManagedBean(name = "ManageUsers", eager = true)
@SessionScoped
public class ManagersUsers implements IUsers {
	@ManagedProperty(value = "#{login}")

	private User user;
	private Vector<User> list = new Vector<User>();

	public Vector<User> getList() {
		return list;
	}

	public void setList(Vector<User> list) {
		this.list = list;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ManagersUsers() {
		// TODO Auto-generated constructor stub
		User us = new User();
		us.setLogin("estm");
		us.setPassword("12345678");
		list.add(us);

		us = new User();
		us.setLogin("dsic");
		us.setPassword("87654321");
		list.add(us);
	}

	@Override
	public Vector<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CheckUser() {
		// TODO Auto-generated method stub
		if (user.getLogin().equals("estm") && user.getPassword().equals("12345678")) {
			return true;
		} else {

			FacesMessage messageID = new FacesMessage("Login ou mot depasse incorrect !!!!");
			FacesContext.getCurrentInstance().addMessage(null, messageID);
			return false;
		}
	}
}
