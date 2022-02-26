package estm.dsic.jee.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
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

	public ManagersUsers() {
		// TODO Auto-generated constructor stub
	}

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

	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	@Override
	public User getUserByID() {
		// TODO Auto-generated method stub
		User usr = null;
		try {
			String query = "SELECT * FROM users WHERE name=? and password=?";

			PreparedStatement pst = DBConnection.getConnection().prepareStatement(query);
			pst.setString(1, user.getName());
			pst.setString(2, user.getPassword());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				usr = new User();
				usr.setId_user(rs.getInt("id_user"));
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
			String query = "SELECT * FROM users";

			PreparedStatement pst = DBConnection.getConnection().prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId_user(rs.getInt("id_user"));
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
		if (us != null && user.getName().equals(us.getName()) && user.getPassword().equals(us.getPassword())) {
			sessionMap.put("userInfo", us);
			return true;
		} else {

			FacesMessage messageID = new FacesMessage("Login ou mot depasse incorrect !!!!");
			FacesContext.getCurrentInstance().addMessage(null, messageID);
			return false;
		}
	}

	@Override
	public String saveUser() {
		try {
			// Insert register data to database
			String query = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";

			PreparedStatement pt = DBConnection.getConnection().prepareStatement(query);
			pt.setString(1, user.getName());
			pt.setString(2, user.getEmail());
			pt.setString(3, user.getPassword());

			if (pt.executeUpdate() != 0) {
				return "/login.xhtml?faces-redirect=true";
			} else {
				FacesMessage messageReg = new FacesMessage("Errour !!!!");
				FacesContext.getCurrentInstance().addMessage(null, messageReg);
				return "/registration.xhtml?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/registration.xhtml?faces-redirect=true";
		}
	}

}
