import java.util.ArrayList;

public class NoeudDame extends Noeud{

	private Damier valeur;
	private ArrayList<NoeudDame> successeurs;
	private ArrayList<Coup> listeDeCoups;
	private int profondeur;
	

//Constructeur
	public NoeudDame(Damier damier) {
		this.valeur=damier;
		this.listeDeCoups=null;
		this.profondeur=-1;
		this.successeurs=new ArrayList<NoeudDame>();
	}
	
	public NoeudDame(Damier damier, ArrayList<Coup> listeDeCoups) {
		this.valeur=damier;
		this.profondeur=-1;
		this.listeDeCoups=listeDeCoups;
		this.successeurs=new ArrayList<NoeudDame>();
	}

//Accesseurs
	
	public ArrayList<Coup> getListeDeCoups() {
		return listeDeCoups;
	}

	public void setListeDeCoups(ArrayList<Coup> listeDeCoups) {
		this.listeDeCoups = listeDeCoups;
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
	
	
	public int Heuristique(boolean peutMangerEnArriere,boolean obligerLesSauts) {
		
		//donne un score entier au coup réalisé
		int tmp=0;
		/*
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
		}*/
		boolean tourBlanc;
		if (listeDeCoups.get(0).getPieceAvantD().getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}else {
			tourBlanc=false;
		}
		//if (listeDeCoups.get(0).getPieceAvantD().sautPossible(tourBlanc, peutMangerEnArriere, true)&&(listeDeCoups.get(0).getPieceApresD().getC())) {
		if (listeDeCoups.get(0).getPieceAvantD().sautPossible(tourBlanc, peutMangerEnArriere, true)) {
			int x1=listeDeCoups.get(0).getPieceAvantD().getC().X();
			int x2=listeDeCoups.get(0).getPieceApresD().getC().X();
			if (abs(x1-x2)>=2) {
				if (obligerLesSauts) {
					tmp=+(listeDeCoups.size())*50000;
				}else {
					tmp=+(listeDeCoups.size())*5;
				}
			}
			
		}
		//faire aussi en cas de sauts multiples
	return(tmp);
	}
	
	private int abs(int a) {
		if (a>=0) {
			return a;
		}else {
			return -a;
		}
	}

}
