import java.util.ArrayList;

public class NoeudDame extends Noeud{

	
	private Damier valeur;
	private ArrayList<NoeudDame> successeurs;
	private ArrayList<Coup> listeDeCoups;
	private int profondeur;
	private boolean peutMangerEnArriere,obligerLesSauts;
	

//Constructeur
	public NoeudDame(Damier damier,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		this.valeur=damier;
		this.listeDeCoups=null;
		this.profondeur=-1;
		this.successeurs=new ArrayList<NoeudDame>();
		this.peutMangerEnArriere=peutMangerEnArriere;
		this.obligerLesSauts=obligerLesSauts;
	}
	
	public NoeudDame(Damier damier, ArrayList<Coup> listeDeCoups,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		this.valeur=damier;
		this.profondeur=-1;
		this.listeDeCoups=listeDeCoups;
		this.successeurs=new ArrayList<NoeudDame>();
		this.peutMangerEnArriere=peutMangerEnArriere;
		this.obligerLesSauts=obligerLesSauts;
	}

//Accesseurs
	public boolean getPeutMangerEnArriere() {
		return peutMangerEnArriere;
	}

	public void setPeutMangerEnArriere(boolean peutMangerEnArriere) {
		this.peutMangerEnArriere = peutMangerEnArriere;
	}

	public boolean getObligerLesSauts() {
		return obligerLesSauts;
	}

	public void setObligerLesSauts(boolean obligerLesSauts) {
		this.obligerLesSauts = obligerLesSauts;
	}	
	
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
	
	public int Heuristique(Couleur couleurOrdi) {
		int res=0;
		boolean ordiBlanc=false;
		if (couleurOrdi==Couleur.Blanc) {
			ordiBlanc=true;
		}
		
		if (ordiBlanc) {
			for (int i=0;i<this.valeur.getPiecesBlanches().getTailleTabPiece();i++) {
				//Points du joueur
				if (this.valeur.getPiecesBlanches().getPiece(i)!=null) {
									
					Piece pieceJoueur =  this.valeur.getPiecesBlanches().getPiece(i);
					
					//reine
					if (pieceJoueur instanceof Reine) {
						res+=20;
						//System.out.println("Reine");
					}
					
					//pion
					if (pieceJoueur instanceof Pion) {
						res+=5;
						//System.out.println("Pion");
					}
					
					//position imprenable
					if ((pieceJoueur.getC().X()==0)||(pieceJoueur.getC().X()==this.getValeur().getTaille()-1)) {
						res+=2;
						//System.out.println("Position imprenable : "+pieceJoueur.getC());
					}
					
				}
				
				//points adversaires
				if (this.valeur.getPiecesNoires().getPiece(i)!=null) {
					
					Piece pieceAdversaire =  this.valeur.getPiecesNoires().getPiece(i);
					
					//reine
					if (pieceAdversaire instanceof Reine) {
						res-=20;
						//System.out.println("Reine");
					}
					
					//pion
					if (pieceAdversaire instanceof Pion) {
						res-=5;
						//System.out.println("Pion");
					}
					
					//position imprenable
					if ((pieceAdversaire.getC().X()==0)||(pieceAdversaire.getC().X()==this.getValeur().getTaille()-1)||(pieceAdversaire.getC().Y()==0)||(pieceAdversaire.getC().Y()==this.getValeur().getTaille()-1)) {
						res-=2;
					}
										
				}
			}
			
		}else {
			for (int i=0;i<this.valeur.getPiecesNoires().getTailleTabPiece();i++) {
				
				//Points du joueur
				if (this.valeur.getPiecesNoires().getPiece(i)!=null) {
									
					Piece pieceJoueur =  this.valeur.getPiecesNoires().getPiece(i);
					
					//reine
					if (pieceJoueur instanceof Reine) {
						res+=20;
						//System.out.println("Reine");
					}
					
					//pion
					if (pieceJoueur instanceof Pion) {
						res+=5;
						//System.out.println("Pion");
					}
					
					//position imprenable
					if ((pieceJoueur.getC().X()==0)||(pieceJoueur.getC().X()==this.getValeur().getTaille()-1)||(pieceJoueur.getC().Y()==0)||(pieceJoueur.getC().Y()==this.getValeur().getTaille()-1)) {
						res+=2;
						//System.out.println("Position imprenable : "+pieceJoueur.getC());
					}					
					
				}
				
				//points adversaires
				if (this.valeur.getPiecesBlanches().getPiece(i)!=null) {
					
					Piece pieceAdversaire =  this.valeur.getPiecesBlanches().getPiece(i);
					
					//reine
					if (pieceAdversaire instanceof Reine) {
						res-=20;
						//System.out.println("Reine");
					}
					
					//pion
					if (pieceAdversaire instanceof Pion) {
						res-=5;
						//System.out.println("Pion");
					}
					
					//position imprenable
					if ((pieceAdversaire.getC().X()==0)||(pieceAdversaire.getC().X()==this.getValeur().getTaille()-1)||(pieceAdversaire.getC().Y()==0)||(pieceAdversaire.getC().Y()==this.getValeur().getTaille()-1)) {
						res-=2;
					}
					
				}
			}
		}
		return res;
	}
	

}
