
public class Arbre {

	private NoeudDame racine;
	
	public Arbre(NoeudDame root) {
		racine=root;
		racine.setProfondeur(0);
	}

	public NoeudDame getRacine() {
		return racine;
	}

	public void setRacine(NoeudDame racine) {
		this.racine = racine;
	}
	
	
		
}
