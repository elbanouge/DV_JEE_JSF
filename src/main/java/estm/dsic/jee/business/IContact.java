package estm.dsic.jee.business;

import java.util.ArrayList;
import estm.dsic.jee.dal.Contact;

public interface IContact {
	public String addContact();

	public String updateContact();

	public String deleteContact();

	public String getContactByID();

	public ArrayList<Contact> getAllContacts();

}
