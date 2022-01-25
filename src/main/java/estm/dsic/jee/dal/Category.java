package estm.dsic.jee.dal;

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

@ManagedBean(name = "category", eager = true)
@RequestScoped
public class Category {
	private String category_name;
	private String sl_no;

	public Category() {
	}

	public void setSl_no(String sl_no) {
		this.sl_no = sl_no;
	}

	public String getSl_no() {
		return sl_no;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	public String edit_Category() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String field_sl_no = params.get("action");
		try {
			Connection connection = DBConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from test where id=" + field_sl_no);
			Category obj_Category = new Category();
			rs.next();
			obj_Category.setCategory_name(rs.getString("name"));
			obj_Category.setSl_no(rs.getString("id"));
			sessionMap.put("editcategory", obj_Category);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/edit.xhtml?faces-redirect=true";
	}

	public String delete_Category() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String field_sl_no = params.get("action");
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement("delete from test where id=?");
			ps.setString(1, field_sl_no);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/index.xhtml?faces-redirect=true";
	}

	public String update_category() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String update_sl_no = params.get("update_sl_no");
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement("update test set name=? where id=?");
			ps.setString(1, category_name);
			ps.setString(2, update_sl_no);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/index.xhtml?faces-redirect=true";
	}

	public ArrayList<Category> getGet_all_category() {
		ArrayList<Category> list_of_categories = new ArrayList<Category>();
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from test");
			while (rs.next()) {
				Category obj_Category = new Category();
				obj_Category.setCategory_name(rs.getString("name"));
				obj_Category.setSl_no(rs.getString("id"));
				list_of_categories.add(obj_Category);
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
		return list_of_categories;
	}

	public void add_Category() {
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement("insert into test(name) value('" + category_name + "')");
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}