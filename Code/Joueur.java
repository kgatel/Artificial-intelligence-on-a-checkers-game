
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
	
	public void Ajoue(int x, int y, boolean tourBlanc) {
		boolean ordi = (this instanceof Ordi);
		int ii=0,jj=0;
		if (this.getDamier().getSautMultiple()&&(!this.getDamier().getGrille()[x][y].getSaut())) {
			//ne rien faire tant que le pion ne mange pas l'autre pion
			if (!ordi) {
				System.out.println("Vous devez manger le pion");
			}
		}
		else {
			this.getDamier().setSautMultiple(false); //on enleve le cas saut de pièce multiple
			if ((this.getDamier().getGrille()[x][y].getPossibleClique()==false)&&(this.getDamier().getGrille()[x][y].getSaut()==false) ) {  //il clique sur une case ou il peut pas se déplacer
				
				for (int j=0;j<this.getDamier().getTaille();j++) {   //rénitialise tout
					for (int i=0;i<this.getDamier().getTaille();i++) {
						if (this.getDamier().getGrille()[i][j].getPossibleClique()) {
							this.getDamier().getGrille()[i][j].setPossibleClique(false);
						}
						if (this.getDamier().getGrille()[i][j].getSaut()) {
							this.getDamier().getGrille()[i][j].setSaut(false);
						}
						if (this.getDamier().getGrille()[i][j].getClique()){
							ii=i;
							jj=j;
							this.getDamier().getGrille()[i][j].setClique(false); 
						}
					}
				}
				this.getDamier().getGrille()[x][y].click();
				
				if (this.getDamier().getGrille()[x][y].getPiece()!=null) {
					this.getDamier().afficherDeplacement(x,y);
				}
				
				
			}
			
			else {
				
				if ( (this.getDamier().getSautObligatoire())&&(!this.getDamier().getGrille()[x][y].getSaut()) ){
					//ne rien faire tant que le saut n'est pas effectué
					if (!ordi) {
						System.out.print("\nVous avez obligation de manger");
					}
				}
				else {
					this.getDamier().setSautObligatoire(false);
					if (this.getDamier().getGrille()[x][y].getSaut()==true) {
						//le joueur vient de manger un pion
					}
					for (int j=0;j<this.getDamier().getTaille();j++) {   //renitialise tous les sauts
						for (int i=0;i<this.getDamier().getTaille();i++) {
							if (this.getDamier().getGrille()[i][j].getSaut()==true) {
								this.getDamier().getGrille()[i][j].setSaut(false);
							}
							if (this.getDamier().getGrille()[i][j].getClique()){
								ii=i;
								jj=j;
							}
						}
					}
					
					boolean pion;
					pion=(this.getDamier().getGrille()[ii][jj].getPiece() instanceof Pion);
					
					//this.getDamier().deplacer(ii,jj,x,y);   //selection de la case où la pièce veut bouger
					this.pieces.deplacer(ii, jj, x, y);
					
					
					Coordonnees c = new Coordonnees(); //coordonnées de la pièce sautée
					boolean b=false;
					
					c=this.getDamier().pieceMangee(x,y,ii,jj,tourBlanc);	//savoir s'il y a eu une pièce mangée ou non
					
					this.getDamier().getGrille()[ii][jj].click();
					
					if (c.getX()!=-1) {		//il y a eu une pièce mangée
						this.getDamier().getGrille()[c.getX()][c.getY()].setPiece(null);  //enlever la pièce mangée
						if (!( (pion)&&(this.getDamier().getGrille()[x][y].getPiece() instanceof Reine) )){   //vérifier qu'il ne peut pas continuer à manger s'il vient d'obtenir une reine
							b = this.getDamier().sautPossible(x,y);		//si b=true alors le joueur peut continuer à sauter
						}
						if ((b)&&(ordi)) {
							attendre(500);
						}
					}
					
					if (b!=true) {	//s'il le pion ne peut pas sauter d'autre pion après avoir sauté alors tour suivant
						if (this.getDamier().getObligerLesSauts()) {
							boolean ilYaUnPionQuiPeutSauter=false;
							for (int i=0;i<this.damier.getTaille();i++) {
								for (int j=0;j<this.damier.getTaille();j++) {
									if (this.getDamier().getGrille()[i][j].getPiece()!=null) {
										if ( ((this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Blanc)&&(tourBlanc)) || ((this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Noir)&&(!(tourBlanc))) ) {
											//if (this.getDamier().peutEtreMange(i,j)) {
											Coordonnees coor = new Coordonnees(i,j);
											if (this.pieces.getPiece(this.pieces.trouverIndice(coor)).peutEtreMange(tourBlanc)) {
												ilYaUnPionQuiPeutSauter=true;
											}
											
										}
									}
								}
							}
							this.getDamier().setSautObligatoire(ilYaUnPionQuiPeutSauter);
						}
						this.getDamier().changementTour();
						
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

	public boolean aGagne(boolean tourBlanc) {
		boolean b=true;
		boolean peutBouger=false;
		for (int i=0; i<this.getDamier().getTaille();i++) {
			for (int j=0;j<this.getDamier().getTaille();j++) {
				if (this.getDamier().getGrille()[i][j].getPiece()!=null) {
					if (((this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Blanc)&&(tourBlanc))||((this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Noir)&&(!tourBlanc))) {
						b=false;
					}
				}
			}
		}
		if (b==false) {
			for (int i=0; i<this.getDamier().getTaille();i++) {
				for (int j=0;j<this.getDamier().getTaille();j++) {
					if (this.getDamier().getGrille()[i][j].getPiece()!=null) {
						if ((tourBlanc)&&(this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Blanc)) {	//tour au blanc
							if ((i<this.getDamier().getTaille()-1)&&(j>0)) {	  //diagonale haute droite
								if (this.getDamier().getGrille()[i+1][j-1].getPiece()==null) {	//
									peutBouger=true;
								}
								else{
									if (this.getDamier().getGrille()[i+1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i<this.getDamier().getTaille()-2)&&(j>1)) {
											if (this.getDamier().getGrille()[i+2][j-2].getPiece()==null) {  //saut de pion
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j>0)) {		//diagonale haute gauche
								if (this.getDamier().getGrille()[i-1][j-1].getPiece()==null) {
									peutBouger=true;
								}
								else {
									if (this.getDamier().getGrille()[i-1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i>1)&&(j>1)) {
											if (this.getDamier().getGrille()[i-2][j-2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (this.getDamier().getGrille()[i][j].getPiece() instanceof Reine) {
								if ((i>0)&&(j<this.getDamier().getTaille()-1)) {		//diagonale basse gauche
									if (this.getDamier().getGrille()[i-1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {		//diagonale basse droite
									if (this.getDamier().getGrille()[i+1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//situation ou le pion mange en arrière
							if ((i>0)&&(j<this.getDamier().getTaille()-1)) {
								if (this.getDamier().getGrille()[i-1][j+1].getPiece()!=null) {
									if (this.getDamier().getGrille()[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i>1)&&(j<this.getDamier().getTaille()-2)) {
											if (this.getDamier().getGrille()[i-2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {
								if (this.getDamier().getGrille()[i+1][j+1].getPiece()!=null) {
									if (this.getDamier().getGrille()[i+1][j+1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i<this.getDamier().getTaille()-2)&&(j<this.getDamier().getTaille()-2)) {
											if (this.getDamier().getGrille()[i+2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
						}
						if ((!tourBlanc)&&(this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Noir)) {  //tour noir
							
							if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {
								if (this.getDamier().getGrille()[i+1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (this.getDamier().getGrille()[i+1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i<this.getDamier().getTaille()-2)&&(j<this.getDamier().getTaille()-2)) {
											if (this.getDamier().getGrille()[i+2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j<this.getDamier().getTaille()-1)) {
								if (this.getDamier().getGrille()[i-1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (this.getDamier().getGrille()[i-1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i>1)&&(j<this.getDamier().getTaille()-2)) {
											if (this.getDamier().getGrille()[i-2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (this.getDamier().getGrille()[i][j].getPiece() instanceof Reine) {
								if ((i>0)&&(j>0)) {		//haut basse gauche
									if (this.getDamier().getGrille()[i-1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<this.getDamier().getTaille()-1)&&(j>0)) {		//diagonale haut droite
									if (this.getDamier().getGrille()[i+1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//depassement en arrière pour pièces noires
							if ((i>0)&&(j>0)) {
								if (this.getDamier().getGrille()[i-1][j-1].getPiece()!=null) {
									if (this.getDamier().getGrille()[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i>1)&&(j>1)) {
											if (this.getDamier().getGrille()[i-2][j-2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i<this.getDamier().getTaille()-1)&&(j>0)) {
								if (this.getDamier().getGrille()[i+1][j-1].getPiece()!=null) {
									if (this.getDamier().getGrille()[i+1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i<this.getDamier().getTaille()-2)&&(j>1)) {
											if (this.getDamier().getGrille()[i+2][j-2].getPiece()==null) {
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
		if ((peutBouger==false)&&(b==false)) {
			System.out.println("Plus aucun déplacement possible");
			b=true;
		}
		return b;
	}
	
	
	
	public void attendre(int ms) {
		try { Thread.sleep (ms); } 
        catch (InterruptedException e)  {  }
	}
	
	
	
}
