package estm.dsic.jee.dal;

public class Contact {
	private int idC;
	private String nom;
	private String adresse;
	private String email;
	private String tel;

	public Contact() {

	}

	public Contact(String nom, String adresse, String email, String tel) {
		this.nom = nom;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
	}

	public Contact(int idC, String nom, String adresse, String email, String tel) {
		this.idC = idC;
		this.nom = nom;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
	}

	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Contact [idC=" + idC + ", nom=" + nom + ", adresse=" + adresse + ", email=" + email + ", tel=" + tel
				+ "]";
	}
}