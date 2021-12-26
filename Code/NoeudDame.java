import java.util.ArrayList;

public class NoeudDame extends Noeud{


	private Damier damier;
	private ArrayList<NoeudDame> successeurs;
	private ArrayList<Coup> listeDeCoups;
	private int profondeur;
	private boolean peutMangerEnArriere,obligerLesSauts;
	

//Constructeur
	public NoeudDame(Damier damier,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		this.damier=damier;
		this.listeDeCoups=null;
		this.profondeur=-1;
		this.successeurs=new ArrayList<NoeudDame>();
		this.peutMangerEnArriere=peutMangerEnArriere;
		this.obligerLesSauts=obligerLesSauts;
	}
	
	public NoeudDame(Damier damier, ArrayList<Coup> listeDeCoups,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		this.damier=damier;
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

	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
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
		Joueur j = new Joueur(couleurOrdi, "", this.peutMangerEnArriere, this.obligerLesSauts);	//de manière à utiliser la méthode APerdu du joueur
		j.setDamier(damier);
		Joueur jAdverse = new Joueur(couleurOrdi.inverser(couleurOrdi), "", this.peutMangerEnArriere, this.obligerLesSauts);	//de manière à utiliser la méthode APerdu du joueur
		jAdverse.setDamier(damier);
		
		if (ordiBlanc) {
			for (int i=0;i<this.damier.getPiecesBlanches().getTailleTabPiece();i++) {
				//Points du joueur
				if (this.damier.getPiecesBlanches().getPiece(i)!=null) {
									
					Piece pieceJoueur =  this.damier.getPiecesBlanches().getPiece(i);
					
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
					if ((pieceJoueur.getC().X()==0)||(pieceJoueur.getC().X()==this.getDamier().getTaille()-1)) {
						res+=2;
						//System.out.println("Position imprenable : "+pieceJoueur.getC());
					}
					
				}
				
				//points adversaires
				if (this.damier.getPiecesNoires().getPiece(i)!=null) {
					
					Piece pieceAdversaire =  this.damier.getPiecesNoires().getPiece(i);
					
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
					if ((pieceAdversaire.getC().X()==0)||(pieceAdversaire.getC().X()==this.getDamier().getTaille()-1)||(pieceAdversaire.getC().Y()==0)||(pieceAdversaire.getC().Y()==this.getDamier().getTaille()-1)) {
						res-=2;
					}
										
				}
			}
			
		}else {
			for (int i=0;i<this.damier.getPiecesNoires().getTailleTabPiece();i++) {
				
				//Points du joueur
				if (this.damier.getPiecesNoires().getPiece(i)!=null) {
									
					Piece pieceJoueur =  this.damier.getPiecesNoires().getPiece(i);
					
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
					if ((pieceJoueur.getC().X()==0)||(pieceJoueur.getC().X()==this.getDamier().getTaille()-1)||(pieceJoueur.getC().Y()==0)||(pieceJoueur.getC().Y()==this.getDamier().getTaille()-1)) {
						res+=2;
						//System.out.println("Position imprenable : "+pieceJoueur.getC());
					}					
					
				}
				
				//points adversaires
				if (this.damier.getPiecesBlanches().getPiece(i)!=null) {
					
					Piece pieceAdversaire =  this.damier.getPiecesBlanches().getPiece(i);
					
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
					if ((pieceAdversaire.getC().X()==0)||(pieceAdversaire.getC().X()==this.getDamier().getTaille()-1)||(pieceAdversaire.getC().Y()==0)||(pieceAdversaire.getC().Y()==this.getDamier().getTaille()-1)) {
						res-=2;
					}
					
				}
			}
		}
		if (jAdverse.APerdu(peutMangerEnArriere)) {
			res+=10000;
		}
		if (j.APerdu(peutMangerEnArriere)) {
			res-=10000;
		}
		return res;
	}
	

}
