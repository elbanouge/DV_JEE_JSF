package estm.dsic.jee.business;

import java.util.Vector;
import estm.dsic.jee.dal.User;

public interface IUsers {

	public boolean CheckUser();

	public User getUserByID();

	public Vector<User> getAllUsers();

	public String saveUser();
}
