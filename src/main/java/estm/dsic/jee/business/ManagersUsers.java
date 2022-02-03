package estm.dsic.jee.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import estm.dsic.jee.dal.DBConnection;
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
		/*
		 * User us = new User(); us.setLogin("estm"); us.setPassword("12345678");
		 * list.add(us);
		 * 
		 * us = new User(); us.setLogin("dsic"); us.setPassword("87654321");
		 * list.add(us);
		 */
	}

	@Override
	public User getUserByID() {
		// TODO Auto-generated method stub
		User usr = null;
		try {
			String query = "select * from user where email=? and password=?";

			PreparedStatement pst = DBConnection.getConnection().prepareStatement(query);
			pst.setString(1, user.getEmail());
			pst.setString(2, user.getPassword());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				usr = new User();
				usr.setLogin(rs.getInt("id"));
				usr.setName(rs.getString("name"));
				usr.setEmail(rs.getString("email"));
				usr.setPassword(rs.getString("password"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}

	@Override
	public Vector<User> getAllUsers() {
		// TODO Auto-generated method stub
		Vector<User> usr = new Vector<User>();
		try {
			String query = "SELECT * FROM `gestioncontacts`.`user`";

			PreparedStatement pst = DBConnection.getConnection().prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setLogin(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));

				usr.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}

	@Override
	public boolean CheckUser() {
		// TODO Auto-generated method stub
		User us = getUserByID();
		if (us != null && user.getEmail().equals(us.getEmail()) && user.getPassword().equals(us.getPassword())) {
			return true;
		} else {

			FacesMessage messageID = new FacesMessage("Login ou mot depasse incorrect !!!!");
			FacesContext.getCurrentInstance().addMessage(null, messageID);
			return false;
		}
	}
}
