
public class Arbre {

	private Noeuddame racine;
	
	public Arbre(Noeuddame root) {
		racine=root;
		racine.setProfondeur(0);
	}

	public Noeuddame getRacine() {
		return racine;
	}

	public void setRacine(Noeuddame racine) {
		this.racine = racine;
	}
	
	
		
}
