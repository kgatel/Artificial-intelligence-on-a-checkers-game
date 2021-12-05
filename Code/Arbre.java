
public class Arbre {
	
	private int profondeur;
	private NoeudDame racine;
	
	public Arbre(int profondeur,NoeudDame root) {
		this.profondeur=profondeur;
		this.racine=root;
	}

	public NoeudDame getRacine() {
		return racine;
	}

	public void setRacine(NoeudDame racine) {
		this.racine = racine;
	}
	
	public int getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}
		
}
