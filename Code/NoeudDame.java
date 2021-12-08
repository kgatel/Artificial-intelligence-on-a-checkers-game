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
	
	public int TotalHeuristique(boolean peutMangerEnArriere,boolean obligerLesSauts, TableauPiece PiecesBlanches, TableauPiece PiecesNoires){
		int tmp=0;
		
		boolean tourBlanc;
		if (listeDeCoups.get(0).getPieceAvantD().getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}else {
			tourBlanc=false;
		}
		
		if (tourBlanc){
			for (int j=0; j<PiecesBlanches.getTailleTabPiece(); j++) {
					tmp=+Heuristique(PiecesBlanches.getPiece(j),peutMangerEnArriere,obligerLesSauts);
				//System.out.println(tmp);	
			}
		} else {

			for (int j=0; j<PiecesNoires.getTailleTabPiece(); j++) {
				tmp=+Heuristique(PiecesNoires.getPiece(j),peutMangerEnArriere,obligerLesSauts);
				//System.out.println(tmp);	
			}
		}
	return (tmp);
	}
	
	public int Heuristique(Piece Piecebougee, boolean peutMangerEnArriere,boolean obligerLesSauts) {
		
		//donne un score entier au coup réalisé
		int tmp=0;
		int x2=Piecebougee.getC().X();
		int y2=Piecebougee.getC().Y();
		
		boolean tourBlanc;
		if (Piecebougee.getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}else {
			tourBlanc=false;
		}
// faire une dame en distinguant si pièce noire ou blanc

		if (tourBlanc){
			if ((y2== 0)&&(Piecebougee instanceof Pion)){	
			//if (listeDeCoups.get(0).getPieceAvantD() instanceof Reine)
				tmp=+25;

			}
		} else {
			if ((y2== valeur.getTaille()-1)&&(Piecebougee instanceof Pion)){	
				tmp=+25;

			}
		}


//position imprenable d'une dame 
		if (Piecebougee instanceof Reine){
			if ((y2== 0)||(y2== valeur.getTaille()-1)||(x2== 0)||(x2== valeur.getTaille()-1))

				tmp=+4;
//position imprenable d'un pion
		}else{ 
			
			if (tourBlanc){
				if ((x2== 0)||(x2== valeur.getTaille()-1)||(y2== valeur.getTaille()-1)){	

				tmp=+4;
			}
			} else {
				if ((x2== 0)||(x2== valeur.getTaille()-1)||(y2== 0)){	
				tmp=+4;
				}
			}			
		}
//position de se faire manger

			if (Piecebougee.peutEtreMange(tourBlanc, valeur.getTaille(), peutMangerEnArriere,obligerLesSauts)){  //ça me propose être manger c'est normal ? ça fait bien les choses ?
				tmp=-25;
			}


//position de manger 
			if (Piecebougee.sautPossible(tourBlanc,peutMangerEnArriere, true) ){  //ça me propose être manger c'est normal ? ça fait bien les choses ?
				tmp=+100;
			}

	return(tmp);
}
	

	private int abs(int a) {
		if (a>=0) {
			return a;
		}else {
			return -a;
		}
	}



	/*public int Heuristique(boolean peutMangerEnArriere,boolean obligerLesSauts) {
		
		//donne un score entier au coup réalisé
		int tmp=0;
		int x1=listeDeCoups.get(0).getPieceAvantD().getC().X();
		int x2=listeDeCoups.get(0).getPieceApresD().getC().X();
		int y1=listeDeCoups.get(0).getPieceAvantD().getC().Y();
		int y2=listeDeCoups.get(0).getPieceApresD().getC().Y();
		
		boolean tourBlanc;
		if (listeDeCoups.get(0).getPieceAvantD().getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}else {
			tourBlanc=false;
		}
// faire une dame en distinguant si pièce noire ou blanc

		if (tourBlanc){
			if ((y2== 0)&&(listeDeCoups.get(0).getPieceAvantD() instanceof Pion)){	
				tmp=+25;

			}
		} else {
			if ((y2== valeur.getTaille())&&(listeDeCoups.get(0).getPieceAvantD() instanceof Pion)){	
				tmp=+25;

			}
		}


//position imprenable d'une dame 
		if (listeDeCoups.get(0).getPieceAvantD() instanceof Reine){
			if ((y2== 0)||(y2== valeur.getTaille())||(x2== 0)||(x2== valeur.getTaille()))

				tmp=+4;
//position imprenable d'un pion
		}else{ 
			
			if (tourBlanc){
				if ((x2== 0)||(x2== valeur.getTaille())||(y2== valeur.getTaille())){	

				tmp=+4;
			}
			} else {
				if ((x2== 0)||(x2== valeur.getTaille())||(y2== 0)){	
				tmp=+4;
				}
			}			
		}
//position de se faire manger

			if (listeDeCoups.get(0).getPieceApresD().peutEtreMange(tourBlanc, valeur.getTaille(), peutMangerEnArriere,obligerLesSauts)){  //ça me propose être manger c'est normal ? ça fait bien les choses ?
				tmp=-25;
			}

		
	boolean IAordi=false;
//position de manger 
			if (listeDeCoups.get(0).getPieceApresD().sautPossible(tourBlanc,peutMangerEnArriere, IAordi) ){  //ça me propose être manger c'est normal ? ça fait bien les choses ?
				tmp=+3000;
			}

		
		
		
		
					
			if (abs(x1-x2)>=2) {
				if (obligerLesSauts) {
					tmp=+(listeDeCoups.size())*50000;
				}else {
					tmp=+(listeDeCoups.size())*5;
				}
			}
	return(tmp);
}
	

	private int abs(int a) {
		if (a>=0) {
			return a;
		}else {
			return -a;
		}
	}*/




}
