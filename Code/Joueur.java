
public class Joueur {

	private String pseudo;
	private Couleur couleur;
	
	public Joueur(Couleur couleur, String pseudo) {
		this.couleur=couleur;
		this.pseudo=pseudo;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public Couleur getCouleur() {
		return couleur;
	}
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	
	
}
