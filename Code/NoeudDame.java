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
		
		//faire une dame
		if ((x2== 0)|(listeDeCoups.get(0).getPieceAvantD() instanceof Pion)){	
			tmp=+26;
		}
		//perdre un pion
		
		//position imprenable
		if (((x2== 0)|(listeDeCoups.get(0).getPieceAvantD() instanceof Reine))&&(y2==0)&&(y2==valeur.getTaille())){
			tmp=+10;
		 }
		
		//est en danger
		if (listeDeCoups.get(0).getPieceApresD().peutEtreMange(tourBlanc, valeur.getTaille(), peutMangerEnArriere,obligerLesSauts)){  //ça me propose être manger c'est normal ? ça fait bien les choses ?
			tmp=+15;
			}else{
				//if ((valeur.getCase(x2-1,y1-1).getPiece() == null)|( valeur.getCase(x2-1,y1-1).getPiece()==null)){
				//tmp=+3;
				//}
			}
		
		//position neutre qui gagne quand même du terrain donc positif
		
		
		
		
		//if (listeDeCoups.get(0).getPieceAvantD().sautPossible(tourBlanc, peutMangerEnArriere, true)&&(listeDeCoups.get(0).getPieceApresD().getC())) {
		
		//pour manger
	//	if (listeDeCoups.get(0).getPieceAvantD().sautPossible(tourBlanc, peutMangerEnArriere, true)) {
			
			if (abs(x1-x2)>=2) {
				if (obligerLesSauts) {
					tmp=+(listeDeCoups.size())*50000;
				}else {
					tmp=+(listeDeCoups.size())*5;
				}
			}
			
		
		//faire aussi en cas de sauts multiples
		//System.out.println(tmp);
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
