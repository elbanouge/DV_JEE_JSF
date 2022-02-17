package estm.dsic.jee.business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import estm.dsic.jee.dal.Contact;
import estm.dsic.jee.dal.DBConnection;

@ManagedBean(name = "ManagersContacts", eager = true)
@RequestScoped
public class ManagersContacts implements IContact {

	private Contact con;
	private String value = null;

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

	@Override
	public String addContact(int id) {
		try {
			Connection connection = DBConnection.getConnection();
			String req = "INSERT INTO `gestioncontacts`.`contacts` (`name`, `adresse`, `email`, `tel`, `id_us`) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(req);
			ps.setString(1, con.getNom());
			ps.setString(2, con.getAdresse());
			ps.setString(3, con.getEmail());
			ps.setString(4, con.getTel());
			ps.setInt(5, id);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/contactsList.xhtml?faces-redirect=true";
	}

	@Override
	public String updateContact() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int id_conUp = Integer.parseInt(params.get("id_Update"));

		try {
			Connection connection = DBConnection.getConnection();
			String req = "UPDATE `gestioncontacts`.`contacts` SET `name`=?, `adresse`=?, `email`=?, `tel`=? WHERE  `id_contact`=?;";
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
		return "/contactsList.xhtml?faces-redirect=true";
	}

	@Override
	public String deleteContact() {
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
		return "/contactsList.xhtml?faces-redirect=true";
	}

	@Override
	public String getContactByID() {
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
			MngContact.con.setId_contact(rs.getInt("id_contact"));
			MngContact.con.setNom(rs.getString("name"));
			MngContact.con.setAdresse(rs.getString("adresse"));
			MngContact.con.setEmail(rs.getString("email"));
			MngContact.con.setTel(rs.getString("tel"));
			sessionMap.put("editcontact", MngContact);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/editContact.xhtml?faces-redirect=true";
	}

	@Override
	public ArrayList<Contact> getAllContacts(int id) {
		ArrayList<Contact> arrayList = new ArrayList<Contact>();
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
			Statement st = connection.createStatement();

			System.out.println("*****" + value);
			String req = "SELECT * FROM `gestioncontacts`.`contacts` where id_us = " + id;

			if (value != null) {
				System.out.println("OK" + value);

				req = "SELECT * FROM `gestioncontacts`.`contacts` where name like '%" + value + "%' or adresse like '%"
						+ value + "%' or email like '%" + value + "%'or tel like '%" + value + "%' HAVING id_us = "
						+ id;
			}

			ResultSet rs = st.executeQuery(req);
			while (rs.next()) {
				Contact contact = new Contact(rs.getInt("id_contact"), rs.getString("name"), rs.getString("adresse"),
						rs.getString("email"), rs.getString("tel"));
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

	public String logout() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}