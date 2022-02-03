package estm.dsic.jee.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import estm.dsic.jee.dal.Contact;
import estm.dsic.jee.dal.DBConnection;

@ManagedBean(name = "ManagersContacts", eager = true)
@RequestScoped
public class ManagersContacts {

	private Contact con;

	public ManagersContacts() {
		con = new Contact();
	}

	public Contact getCon() {
		return con;
	}

	public void setCon(Contact con) {
		this.con = con;
	}

	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	public String edit_Contact() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id_contact = params.get("action");
		try {
			Connection connection = DBConnection.getConnection();
			Statement st = connection.createStatement();
			String req = "SELECT * FROM `gestioncontacts`.`contacts` where id_contact = " + id_contact;

			ResultSet rs = st.executeQuery(req);
			rs.next();

			ManagersContacts MngContact = new ManagersContacts();
			MngContact.con.setIdC(rs.getInt("id_contact"));
			MngContact.con.setNom(rs.getString("nameCon"));
			MngContact.con.setAdresse(rs.getString("adresseCon"));
			MngContact.con.setEmail(rs.getString("emailCon"));
			MngContact.con.setTel(rs.getString("telCon"));
			sessionMap.put("editcontact", MngContact);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/edit.xhtml?faces-redirect=true";
	}

	public String delete_Contact() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String id_contact = params.get("action");
		try {
			Connection connection = DBConnection.getConnection();
			String req = "DELETE FROM `gestioncontacts`.`contacts` WHERE  `id_contact`=?;";
			PreparedStatement ps = connection.prepareStatement(req);
			ps.setString(1, id_contact);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/index.xhtml?faces-redirect=true";
	}

	public String update_Contact() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int id_conUp = Integer.parseInt(params.get("id_conUp"));
		try {
			Connection connection = DBConnection.getConnection();
			String req = "UPDATE `gestioncontacts`.`contacts` SET `nameCon`=?, `adresseCon`=?, `emailCon`=?, `telCon`=? WHERE  `id_contact`=?;";
			PreparedStatement ps = connection.prepareStatement(req);
			ps.setString(1, con.getNom());
			ps.setString(2, con.getAdresse());
			ps.setString(3, con.getEmail());
			ps.setString(4, con.getTel());
			ps.setInt(5, id_conUp);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/index.xhtml?faces-redirect=true";
	}

	public ArrayList<Contact> getGet_all_contacts() {
		ArrayList<Contact> arrayList = new ArrayList<Contact>();
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
			Statement st = connection.createStatement();
			String req = "SELECT * FROM `gestioncontacts`.`contacts`";
			ResultSet rs = st.executeQuery(req);
			while (rs.next()) {
				Contact contact = new Contact(rs.getInt("id_contact"), rs.getString("nameCon"),
						rs.getString("adresseCon"), rs.getString("emailCon"), rs.getString("telCon"));
				arrayList.add(contact);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return arrayList;
	}

	public void add_Contact() {
		try {
			Connection connection = DBConnection.getConnection();
			String req = "INSERT INTO `gestioncontacts`.`contacts` (`nameCon`, `adresseCon`, `emailCon`, `telCon`) VALUES (?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(req);
			ps.setString(1, con.getNom());
			ps.setString(2, con.getAdresse());
			ps.setString(3, con.getEmail());
			ps.setString(4, con.getTel());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
