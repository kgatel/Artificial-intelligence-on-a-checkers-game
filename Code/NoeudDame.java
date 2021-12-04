import java.util.ArrayList;

public class NoeudDame extends Noeud{
	
	private ArrayList<NoeudDame> successeurs;
	private Damier valeur;
	private Coup coup;
	private int profondeur;
	

//Constructeur
	public NoeudDame(Damier damier) {
		this.valeur=damier;
		this.coup=null;
		this.profondeur=-1;
		this.successeurs=new ArrayList<NoeudDame>();
	}
	
	public NoeudDame(Damier damier, Coup coup) {
		this.valeur=damier;
		this.profondeur=-1;
		this.coup=coup;
		this.successeurs=new ArrayList<NoeudDame>();
	}

//Accesseurs
	
	public Coup getCoup() {
		return coup;
	}

	public void setCoup(Coup coup) {
		this.coup = coup;
	}
	
	public ArrayList<NoeudDame> getSuccesseurs() {
		return successeurs;
	}
	
	public NoeudDame getSuccesseurs(int i) {
		return successeurs.get(i);
	}

	public void setSuccesseurs(ArrayList<NoeudDame> successeurs) {
		this.successeurs = successeurs;
	}
	
	public void setSuccesseurs(int i,NoeudDame noeudDame) {
		successeurs.set(i, noeudDame);
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
	
	public void clear () {
		successeurs.clear();
	}
	
	
	public int Heuristique() {
	Piece exemple=coup.getPiece();

		//donne un score entier au coup réalisé
		int tmp=0;
		//test si est une dame (
		if (exemple.getCoordonnees().Y() ==0 ) {
			tmp = tmp+15;}
		//test position imprenable
		if ((exemple.getCoordonnees().Y() ==0 )|(exemple.getCoordonnees().Y()== valeur.getTaille() )) {
			tmp= tmp + 7;}
		// à une pièce à proximité = reste vérifier si il peut manger
		if (exemple.getCoordonnees().testexistance(valeur.getTaille(), exemple.getCoordonnees().X()+1,exemple.getCoordonnees().Y()+1)){
			if (valeur.getPiece(exemple.getCoordonnees().X()+1,exemple.getCoordonnees().Y()+1).getCouleur() == exemple.getCouleur()){  //comparer couleur de la pièce présente sur cette case)
				tmp = tmp + 5;
			}
		}
				if (exemple.getCoordonnees().testexistance(valeur.getTaille(), exemple.getCoordonnees().X()+1,exemple.getCoordonnees().Y()-1)){
					if (valeur.getPiece(exemple.getCoordonnees().X()+1,exemple.getCoordonnees().Y()-1).getCouleur() == exemple.getCouleur()){  //comparer couleur de la pièce présente sur cette case)
					tmp = tmp + 5;
			}
		}
		
		
		//faire aussi en cas de sauts multiples
	return(tmp);
	}
	

}
