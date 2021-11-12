import java.util.ArrayList;

public class NoeudDame extends Noeud{
	private ArrayList<NoeudDame> successeurs;
	private Damier valeur;
	private int profondeur;

//Constructeur
	
	public NoeudDame(Damier damier) {
		this.valeur=damier;
		this.profondeur=-1;
		this.successeurs=new ArrayList<NoeudDame>();
	}

//Accesseurs
	
	public ArrayList<NoeudDame> getSuccesseurs() {
		return successeurs;
	}

	public void setSuccesseurs(ArrayList<NoeudDame> successeurs) {
		this.successeurs = successeurs;
	}

	public Damier getValeur() {
		return valeur;
	}

	public void setValeur(Damier valeur) {
		this.valeur = valeur;
	}
	
	public int getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}

//Méthodes
	
	public void ajouterSuccesseur (NoeudDame next) {
		next.setProfondeur(this.profondeur+1);
		successeurs.add(next);
	}
	
	@Override
	public int heuristique() {
		return 0;
	}
	
}