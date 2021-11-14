
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
		if (damier.getSautMultiple()&&(!damier.getCase(x,y).getSaut())) {
			//ne rien faire tant que le pion ne mange pas l'autre pion
			if (!ordi) {
				System.out.println();
				System.out.println("Vous devez manger le pion");
			}
		}
		else {
			damier.setSautMultiple(false); //on enleve le cas saut de pièce multiple
			if ((damier.getCase(x,y).getPossibleClique()==false)&&(damier.getCase(x,y).getSaut()==false) ) {  //il clique sur une case ou il peut pas se déplacer
				
				for (int j=0;j<damier.getTaille();j++) {   //rénitialise tout
					for (int i=0;i<damier.getTaille();i++) {
						if (damier.getCase(i,j).getPossibleClique()) {
							damier.getCase(i,j).setPossibleClique(false);
						}
						if (damier.getCase(i,j).getSaut()) {
							damier.getCase(i,j).setSaut(false);
						}
						if (damier.getCase(i,j).getClique()){
							ii=i;
							jj=j;
							damier.getCase(i,j).setClique(false); 
						}
					}
				}
				damier.getCase(x,y).click();
				
				if (damier.getCase(x,y).getPiece()!=null) {
					//damier.afficherDeplacement(x,y);
					damier.getCase(x,y).getPiece().afficherDeplacement(tourBlanc,peutMangerEnArriere);
				}
				
				
			}
			
			else {
				
				if ( (damier.getSautObligatoire())&&(!damier.getCase(x,y).getSaut()) ){
					//ne rien faire tant que le saut n'est pas effectué
					if (!ordi) {
						System.out.print("\nVous avez obligation de manger");
					}
				}
				else {
					damier.setSautObligatoire(false);
					if (damier.getCase(x,y).getSaut()==true) {
						//le joueur vient de manger un pion
					}
					for (int j=0;j<damier.getTaille();j++) {   //renitialise tous les sauts
						for (int i=0;i<damier.getTaille();i++) {
							if (damier.getCase(i,j).getSaut()==true) {
								damier.getCase(i,j).setSaut(false);
							}
							if (damier.getCase(i,j).getClique()){
								ii=i;
								jj=j;
							}
						}
					}
					
					boolean pion;
					pion=(damier.getPiece(ii,jj) instanceof Pion);
					
					//damier.deplacer(ii,jj,x,y);   //selection de la case où la pièce veut bouger
					this.pieces.deplacer(ii, jj, x, y, tourBlanc);
					
					Coordonnees c = this.getPieces().pieceMangeeLorsDunSaut(x,y,ii,jj,tourBlanc);	//savoir s'il y a eu une pièce mangée ou non
					boolean b=false;
					
					damier.getCase(ii,jj).click();
					
					if (c.X()!=-1) {		//il y a eu une pièce mangée
						damier.getCase(c.X(),c.Y()).setPiece(null);  //enlever la pièce mangée
						if (tourBlanc) {
							damier.getPiecesNoires().setPiece(null, damier.getPiecesNoires().trouverIndice(c));
						}else {
							damier.getPiecesBlanches().setPiece(null, damier.getPiecesBlanches().trouverIndice(c));
						}
						if (!( (pion)&&(damier.getCase(x,y).getPiece() instanceof Reine) )){   //vérifier qu'il ne peut pas continuer à manger s'il vient d'obtenir une reine
							b = damier.getCase(x,y).getPiece().sautPossible(tourBlanc,peutMangerEnArriere,obligerLesSauts);		//si b=true alors le joueur peut continuer à sauter
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
									if (damier.getPiece(i,j)!=null) {
										if ( ((damier.getPiece(i,j).getCouleur()==Couleur.Blanc)&&(tourBlanc)) || ((damier.getPiece(i,j).getCouleur()==Couleur.Noir)&&(!(tourBlanc))) ) {
											//if (damier.peutEtreMange(i,j)) {
											Coordonnees coor = new Coordonnees(i,j);
											if (this.pieces.getPiece(this.pieces.trouverIndice(coor)).peutEtreMange(tourBlanc,damier.getTaille(),peutMangerEnArriere,obligerLesSauts)) {
												ilYaUnPionQuiPeutSauter=true;
											}
											
										}
									}
								}
							}
							damier.setSautObligatoire(ilYaUnPionQuiPeutSauter);
						}						
						damier.setTourFini(true);	
					}
					else {
						damier.setSautMultiple(true);
					}
				}
			}
		}
		damier.repaint();
	}
	
	public boolean APerdu(boolean peutMangerEnArriere) {
		boolean tourBlanc=false;
		if (this.couleur==Couleur.Blanc){
			tourBlanc=true;
		}
		boolean b=true;
		boolean peutBouger=false;
		//savoir s'il reste au moins une pièce
		for (int i=0; i<damier.getTaille();i++) {
			for (int j=0;j<damier.getTaille();j++) {
				if (damier.getPiece(i,j)!=null) {
					if (((damier.getPiece(i,j).getCouleur()==Couleur.Blanc)&&(tourBlanc))||((damier.getPiece(i,j).getCouleur()==Couleur.Noir)&&(!tourBlanc))) {
						b=false;
					}
				}
			}
		}
		//
		if (b==false) {
			for (int i=0; i<damier.getTaille();i++) {
				for (int j=0;j<damier.getTaille();j++) {
					if (damier.getPiece(i,j)!=null) {
						if ((tourBlanc)&&(damier.getPiece(i,j).getCouleur()==Couleur.Blanc)) {	//tour au blanc
							if ((i<damier.getTaille()-1)&&(j>0)) {	  //diagonale haute droite
								if (damier.getCases()[i+1][j-1].getPiece()==null) {	//
									peutBouger=true;
								}
								else{
									if (damier.getCases()[i+1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i<damier.getTaille()-2)&&(j>1)) {
											if (damier.getCases()[i+2][j-2].getPiece()==null) {  //saut de pion
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j>0)) {		//diagonale haute gauche
								if (damier.getCases()[i-1][j-1].getPiece()==null) {
									peutBouger=true;
								}
								else {
									if (damier.getCases()[i-1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i>1)&&(j>1)) {
											if (damier.getCases()[i-2][j-2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (damier.getPiece(i,j) instanceof Reine) {
								if ((i>0)&&(j<damier.getTaille()-1)) {		//diagonale basse gauche
									if (damier.getCases()[i-1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<damier.getTaille()-1)&&(j<damier.getTaille()-1)) {		//diagonale basse droite
									if (damier.getCases()[i+1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//situation ou le pion mange en arrière
							if ((peutMangerEnArriere)||(damier.getPiece(i,j) instanceof Reine)) {
								if ((i>0)&&(j<damier.getTaille()-1)) {
									if (damier.getCases()[i-1][j+1].getPiece()!=null) {
										if (damier.getCases()[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
											if ((i>1)&&(j<damier.getTaille()-2)) {
												if (damier.getCases()[i-2][j+2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
								if ((i<damier.getTaille()-1)&&(j<damier.getTaille()-1)) {
									if (damier.getCases()[i+1][j+1].getPiece()!=null) {
										if (damier.getCases()[i+1][j+1].getPiece().getCouleur()==Couleur.Noir) {
											if ((i<damier.getTaille()-2)&&(j<damier.getTaille()-2)) {
												if (damier.getCases()[i+2][j+2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
							}
						}
						if ((!tourBlanc)&&(damier.getPiece(i,j).getCouleur()==Couleur.Noir)) {  //tour noir
							
							if ((i<damier.getTaille()-1)&&(j<damier.getTaille()-1)) {
								if (damier.getCases()[i+1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (damier.getCases()[i+1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i<damier.getTaille()-2)&&(j<damier.getTaille()-2)) {
											if (damier.getCases()[i+2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j<damier.getTaille()-1)) {
								if (damier.getCases()[i-1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (damier.getCases()[i-1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i>1)&&(j<damier.getTaille()-2)) {
											if (damier.getCases()[i-2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (damier.getPiece(i,j) instanceof Reine) {
								if ((i>0)&&(j>0)) {		//haut basse gauche
									if (damier.getCases()[i-1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<damier.getTaille()-1)&&(j>0)) {		//diagonale haut droite
									if (damier.getCases()[i+1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//depassement en arrière pour pièces noires
							if ((peutMangerEnArriere)||(damier.getPiece(i,j) instanceof Reine)) {
								if ((i>0)&&(j>0)) {
									if (damier.getCases()[i-1][j-1].getPiece()!=null) {
										if (damier.getCases()[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
											if ((i>1)&&(j>1)) {
												if (damier.getCases()[i-2][j-2].getPiece()==null) {
													peutBouger=true;
												}
											}
										}
									}
								}
								if ((i<damier.getTaille()-1)&&(j>0)) {
									if (damier.getCases()[i+1][j-1].getPiece()!=null) {
										if (damier.getCases()[i+1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
											if ((i<damier.getTaille()-2)&&(j>1)) {
												if (damier.getCases()[i+2][j-2].getPiece()==null) {
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

	
	public void attendre(int ms) {
		try { Thread.sleep (ms); } 
        catch (InterruptedException e)  {  }
	}
	
	
	
}
