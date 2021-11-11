
public class Joueur {

	private String pseudo;
	private Couleur couleur;
	private Damier damier;  //tableau de cases
	private TableauPiece pieces;
	
	public Joueur(Couleur couleur, String pseudo) {
		this.couleur=couleur;
		this.pseudo=pseudo;
		pieces = null;
		damier=null;
	}
	
	//getters and setters
	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
	}

	public TableauPiece getPieces() {
		return pieces;
	}
	
	public Piece getPieces(int i) {
		return pieces.getPiece(i);
	}

	public void setPieces(TableauPiece pieces) {
		this.pieces = pieces;
	}

	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public Couleur getCouleur() {
		return couleur;
	}
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	
	//Méthodes
	public void Ajoue(int x, int y, boolean tourBlanc, boolean peutMangerEnArriere ,boolean obligerLesSauts) {
		boolean ordi = (this instanceof Ordi);
		int ii=0,jj=0;
		if (this.getDamier().getSautMultiple()&&(!this.getDamier().getCases()[x][y].getSaut())) {
			//ne rien faire tant que le pion ne mange pas l'autre pion
			if (!ordi) {
				System.out.println();
				System.out.println("Vous devez manger le pion");
			}
		}
		else {
			this.getDamier().setSautMultiple(false); //on enleve le cas saut de pièce multiple
			if ((this.getDamier().getCases()[x][y].getPossibleClique()==false)&&(this.getDamier().getCases()[x][y].getSaut()==false) ) {  //il clique sur une case ou il peut pas se déplacer
				
				for (int j=0;j<this.getDamier().getTaille();j++) {   //rénitialise tout
					for (int i=0;i<this.getDamier().getTaille();i++) {
						if (this.getDamier().getCases()[i][j].getPossibleClique()) {
							this.getDamier().getCases()[i][j].setPossibleClique(false);
						}
						if (this.getDamier().getCases()[i][j].getSaut()) {
							this.getDamier().getCases()[i][j].setSaut(false);
						}
						if (this.getDamier().getCases()[i][j].getClique()){
							ii=i;
							jj=j;
							this.getDamier().getCases()[i][j].setClique(false); 
						}
					}
				}
				this.getDamier().getCases()[x][y].click();
				
				if (this.getDamier().getCases()[x][y].getPiece()!=null) {
					//this.getDamier().afficherDeplacement(x,y);
					this.getDamier().getCases()[x][y].getPiece().afficherDeplacement(tourBlanc,peutMangerEnArriere);
				}
				
				
			}
			
			else {
				
				if ( (this.getDamier().getSautObligatoire())&&(!this.getDamier().getCases()[x][y].getSaut()) ){
					//ne rien faire tant que le saut n'est pas effectué
					if (!ordi) {
						System.out.print("\nVous avez obligation de manger");
					}
				}
				else {
					this.getDamier().setSautObligatoire(false);
					if (this.getDamier().getCases()[x][y].getSaut()==true) {
						//le joueur vient de manger un pion
					}
					for (int j=0;j<this.getDamier().getTaille();j++) {   //renitialise tous les sauts
						for (int i=0;i<this.getDamier().getTaille();i++) {
							if (this.getDamier().getCases()[i][j].getSaut()==true) {
								this.getDamier().getCases()[i][j].setSaut(false);
							}
							if (this.getDamier().getCases()[i][j].getClique()){
								ii=i;
								jj=j;
							}
						}
					}
					
					boolean pion;
					pion=(this.getDamier().getCases()[ii][jj].getPiece() instanceof Pion);
					
					//this.getDamier().deplacer(ii,jj,x,y);   //selection de la case où la pièce veut bouger
					this.pieces.deplacer(ii, jj, x, y, tourBlanc);
					
					Coordonnees c = new Coordonnees(); //coordonnées de la pièce sautée
					boolean b=false;
					
					c=this.pieceMangeeLorsDunSaut(x,y,ii,jj,tourBlanc);	//savoir s'il y a eu une pièce mangée ou non
					
					this.getDamier().getCases()[ii][jj].click();
					
					if (c.getX()!=-1) {		//il y a eu une pièce mangée
						this.getDamier().getCases()[c.getX()][c.getY()].setPiece(null);  //enlever la pièce mangée
						if (tourBlanc) {
							damier.getPiecesNoires().setPiece(null, damier.getPiecesNoires().trouverIndice(c));
						}else {
							damier.getPiecesBlanches().setPiece(null, damier.getPiecesBlanches().trouverIndice(c));
						}
						if (!( (pion)&&(this.getDamier().getCases()[x][y].getPiece() instanceof Reine) )){   //vérifier qu'il ne peut pas continuer à manger s'il vient d'obtenir une reine
							b = this.getDamier().getCases()[x][y].getPiece().sautPossible(tourBlanc,peutMangerEnArriere,obligerLesSauts);		//si b=true alors le joueur peut continuer à sauter
						}
						if ((b)&&(ordi)) {
							attendre(500);
						}
					}
					
					if (b!=true) {	//s'il le pion ne peut pas sauter d'autre pion après avoir sauté alors tour suivant
						if (obligerLesSauts) {
							boolean ilYaUnPionQuiPeutSauter=false;
							for (int i=0;i<this.damier.getTaille();i++) {
								for (int j=0;j<this.damier.getTaille();j++) {
									if (this.getDamier().getCases()[i][j].getPiece()!=null) {
										if ( ((this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Blanc)&&(tourBlanc)) || ((this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Noir)&&(!(tourBlanc))) ) {
											//if (this.getDamier().peutEtreMange(i,j)) {
											Coordonnees coor = new Coordonnees(i,j);
											if (this.pieces.getPiece(this.pieces.trouverIndice(coor)).peutEtreMange(tourBlanc,this.getDamier().getTaille(),peutMangerEnArriere,obligerLesSauts)) {
												ilYaUnPionQuiPeutSauter=true;
											}
											
										}
									}
								}
							}
							this.getDamier().setSautObligatoire(ilYaUnPionQuiPeutSauter);
						}						
						this.getDamier().setTourFini(true);	
					}
					else {
						this.getDamier().setSautMultiple(true);
					}
				}
			}
		}
		this.getDamier().repaint();
	}

	public Coordonnees pieceMangeeLorsDunSaut(int x, int y,int i,int j,boolean tourBlanc) { //donne les coordonnées de la pièce mangée
		Coordonnees c = new Coordonnees();
		int delta=abs(y-j);
		if (delta>=2) {
			if (y-j<0) {
				if (x-i>0) {		//diagonale haute droite
					int k=1;
					while (this.getDamier().getCases()[i+k][j-k].getPiece()==null) {
						k++;
					}
					if ( ((this.getDamier().getCases()[i+k][j-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[i+k][j-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i+k);
						c.setY(j-k);
					}
					
				}
				if (x-i<0)	{		//diagonale haute gauche
					int k=1;
					while (this.getDamier().getCases()[i-k][j-k].getPiece()==null) {
						k++;
					}
					if ( ((this.getDamier().getCases()[i-k][j-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[i-k][j-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i-k);
						c.setY(j-k);
					}
				}
			}
			else {  //y-j>0
				if (x-i>0) {		//diagonale basse droite
					int k=1;
					while (this.getDamier().getCases()[i+k][j+k].getPiece()==null) {
						k++;
					}
					if ( ((this.getDamier().getCases()[i+k][j+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[i+k][j+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i+k);
						c.setY(j+k);
					}
				}
				if (x-i<0)	{		//diagonale basse gauche
					int k=1;
					while (this.getDamier().getCases()[i-k][j+k].getPiece()==null) {
						k++;
					}
					if ( ((this.getDamier().getCases()[i-k][j+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[i-k][j+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i-k);
						c.setY(j+k);
					}
				}
			}
		}
		return c;
	}
	
	public boolean APerdu(boolean peutMangerEnArriere) {
		boolean tourBlanc=false;
		if (this.couleur==Couleur.Blanc){
			tourBlanc=true;
		}
		boolean b=true;
		boolean peutBouger=false;
		//savoir s'il reste au moins une pièce
		for (int i=0; i<this.getDamier().getTaille();i++) {
			for (int j=0;j<this.getDamier().getTaille();j++) {
				if (this.getDamier().getCases()[i][j].getPiece()!=null) {
					if (((this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Blanc)&&(tourBlanc))||((this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Noir)&&(!tourBlanc))) {
						b=false;
					}
				}
			}
		}
		//
		if (b==false) {
			for (int i=0; i<this.getDamier().getTaille();i++) {
				for (int j=0;j<this.getDamier().getTaille();j++) {
					if (this.getDamier().getCases()[i][j].getPiece()!=null) {
						if ((tourBlanc)&&(this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Blanc)) {	//tour au blanc
							if ((i<this.getDamier().getTaille()-1)&&(j>0)) {	  //diagonale haute droite
								if (this.getDamier().getCases()[i+1][j-1].getPiece()==null) {	//
									peutBouger=true;
								}
								else{
									if (this.getDamier().getCases()[i+1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i<this.getDamier().getTaille()-2)&&(j>1)) {
											if (this.getDamier().getCases()[i+2][j-2].getPiece()==null) {  //saut de pion
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j>0)) {		//diagonale haute gauche
								if (this.getDamier().getCases()[i-1][j-1].getPiece()==null) {
									peutBouger=true;
								}
								else {
									if (this.getDamier().getCases()[i-1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i>1)&&(j>1)) {
											if (this.getDamier().getCases()[i-2][j-2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (this.getDamier().getCases()[i][j].getPiece() instanceof Reine) {
								if ((i>0)&&(j<this.getDamier().getTaille()-1)) {		//diagonale basse gauche
									if (this.getDamier().getCases()[i-1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {		//diagonale basse droite
									if (this.getDamier().getCases()[i+1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//situation ou le pion mange en arrière
							if ((peutMangerEnArriere)||(damier.getCases()[i][j].getPiece() instanceof Reine)) {
								if ((i>0)&&(j<this.getDamier().getTaille()-1)) {
									if (this.getDamier().getCases()[i-1][j+1].getPiece()!=null) {
										if (this.getDamier().getCases()[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
											if ((i>1)&&(j<this.getDamier().getTaille()-2)) {
												if (this.getDamier().getCases()[i-2][j+2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
								if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {
									if (this.getDamier().getCases()[i+1][j+1].getPiece()!=null) {
										if (this.getDamier().getCases()[i+1][j+1].getPiece().getCouleur()==Couleur.Noir) {
											if ((i<this.getDamier().getTaille()-2)&&(j<this.getDamier().getTaille()-2)) {
												if (this.getDamier().getCases()[i+2][j+2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
							}
						}
						if ((!tourBlanc)&&(this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Noir)) {  //tour noir
							
							if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {
								if (this.getDamier().getCases()[i+1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (this.getDamier().getCases()[i+1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i<this.getDamier().getTaille()-2)&&(j<this.getDamier().getTaille()-2)) {
											if (this.getDamier().getCases()[i+2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j<this.getDamier().getTaille()-1)) {
								if (this.getDamier().getCases()[i-1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (this.getDamier().getCases()[i-1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i>1)&&(j<this.getDamier().getTaille()-2)) {
											if (this.getDamier().getCases()[i-2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (this.getDamier().getCases()[i][j].getPiece() instanceof Reine) {
								if ((i>0)&&(j>0)) {		//haut basse gauche
									if (this.getDamier().getCases()[i-1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<this.getDamier().getTaille()-1)&&(j>0)) {		//diagonale haut droite
									if (this.getDamier().getCases()[i+1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//depassement en arrière pour pièces noires
							if ((peutMangerEnArriere)||(damier.getCases()[i][j].getPiece() instanceof Reine)) {
								if ((i>0)&&(j>0)) {
									if (this.getDamier().getCases()[i-1][j-1].getPiece()!=null) {
										if (this.getDamier().getCases()[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
											if ((i>1)&&(j>1)) {
												if (this.getDamier().getCases()[i-2][j-2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
								if ((i<this.getDamier().getTaille()-1)&&(j>0)) {
									if (this.getDamier().getCases()[i+1][j-1].getPiece()!=null) {
										if (this.getDamier().getCases()[i+1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
											if ((i<this.getDamier().getTaille()-2)&&(j>1)) {
												if (this.getDamier().getCases()[i+2][j-2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if ((peutBouger==false)&&(b==false)) {
			System.out.println("Plus aucun déplacement possible");
			b=true;
		}
		return b;
	}
	
	public int abs(int a) {
		if (a>=0) {
			return a;
		}
		else {
			return -a;
		}
	}
	
	public void attendre(int ms) {
		try { Thread.sleep (ms); } 
        catch (InterruptedException e)  {  }
	}
	
	
	
}
