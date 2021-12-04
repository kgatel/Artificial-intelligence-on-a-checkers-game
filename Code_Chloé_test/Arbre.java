
public class Arbre {

	private NoeudDame racine;
	private int profondeur;
	
	public Arbre(NoeudDame root) {
		racine=root;
		racine.setProfondeur(0);
	}

	public NoeudDame getRacine() {
		return racine;
	}
	
	public int getProfondeur(){
		return profondeur;
	}
	
	

	public void setRacine(NoeudDame racine) {
		this.racine = racine;
	}
	
	
		
}
