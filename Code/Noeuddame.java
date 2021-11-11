import java.util.ArrayList;

public class Noeuddame extends Noeud{
	private ArrayList<Noeuddame> successeurs;
	private Damier valeur;
	private int profondeur;

//Constructeur
	
	public Noeuddame(Damier damier) {
		valeur=damier;
		this.successeurs=new ArrayList<Noeuddame>();
	}

//Accesseurs
	
	public ArrayList<Noeuddame> getSuccesseurs() {
		return successeurs;
	}

	public void setSuccesseurs(ArrayList<Noeuddame> successeurs) {
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
	
	public void ajoutersuccesseur (Noeuddame next) {
		next.setProfondeur(this.profondeur+1);
		successeurs.add(next);
	}
	
	@Override
	public int heuristique() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}