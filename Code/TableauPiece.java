
public class TableauPiece {

	//Attributs
	private Damier damier;
	private Case [][] grille;
	private Piece [] piece;
	private int taille;
	private Couleur couleur;
	
	
	public TableauPiece(Damier damier,Case [][] grille, Piece [] piece,int taille,Couleur couleur){
		this.damier=damier;
		this.grille=grille;
		this.piece=piece;
		this.taille=taille;
		this.couleur=couleur;
	}
	
	//Constructeurs
		
	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}	
	
	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
		for (int i=0;i<taille;i++) {
			this.piece[i].setDamier(damier);
		}
	}
	
	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public Piece getPiece(int i) {
		return this.piece[i];
	}

	public void setPiece(Piece piece,int i) {
		this.piece[i] = piece;
	}
	
	public Case[][] getGrille() {
		return grille;
	}

	public void setGrille(Case[][] grille) {
		this.grille = grille;
		for (int i=0;i<taille;i++) {
			this.piece[i].setGrille(grille);
		}
	}

	//Méthodes
	public int trouverIndice(Coordonnees c) {
		int res = -1;
		int i=0;
		while ((res==-1)&&(i<this.taille)) {
			if ((this.piece[i].getCoordonnees().getX()==c.getX())&&(this.piece[i].getCoordonnees().getY()==c.getY())) {
				res=i;
			}
			i++;
		}
		return res;
	}

	public void deplacer(int i, int j, int x, int y) { //déplacer la pièce de (i,j) à (x,y)		
		boolean reine = (this.getGrille()[i][j].getPiece() instanceof Reine);
		Coordonnees c1 = new Coordonnees(i,j);
		Coordonnees c2 = new Coordonnees(x,y);
		int indice = trouverIndice(c1);
		if (this.damier.getTourBlanc()) {	//piece blanche
			if ((y==0)||(reine)) {
				//this.setPiece(new Reine(Couleur.Blanc,c2,damier,grille), indice);
				grille[x][y].setPiece(new Reine(Couleur.Blanc,c2,damier,grille));
				//on vient d'obtenir une reine
			}
			else {
				//this.setPiece(new Pion(Couleur.Blanc,c2,damier,grille), indice);
				grille[x][y].setPiece(new Pion(Couleur.Blanc,c2,damier,grille));
			}
		}
		else {	//piece noire
			if ((y==this.getDamier().getTaille()-1)||(reine)) {
				//this.setPiece(new Reine(Couleur.Noir,c2,damier,grille), indice);
				grille[x][y].setPiece(new Reine(Couleur.Noir,c2,damier,grille));
				//on vient d'obtenir une reine
			}
			else {
				//this.setPiece(new Pion(Couleur.Noir,c2,damier,grille), indice);
				grille[x][y].setPiece(new Pion(Couleur.Noir,c2,damier,grille));
			}
		}
		
		for (int ii=0;ii<this.damier.getTaille();ii++) {
			for (int jj=0;jj<this.damier.getTaille();jj++) {
				if (grille[ii][jj].getPossibleClique()) {		//rénitialiser toutes les cases sur lesquelles le pion pouvait bouger
					grille[ii][jj].setPossibleClique(false);
				}
			}
		}
		grille[i][j].setPiece(null);
				
	}
	
	public void afficherDeplacement(int i, int j) {
		if (grille[i][j].getPiece()!=null) {  //repère la case cliquée
			if (grille[i][j].getPiece() instanceof Pion) {
				if ((this.getDamier().getTourBlanc())&&(grille[i][j].getPiece().getCouleur()==Couleur.Blanc)) {	//tour au blanc
					if ((i<taille-1)&&(j>0)) {	  //diagonale haute droite
						if (grille[i+1][j-1].getPiece()==null) {	//
							grille[i+1][j-1].setPossibleClique(true);
						}
						else{
							if (grille[i+1][j-1].getPiece().getCouleur()==Couleur.Noir) {
								if ((i<taille-2)&&(j>1)) {
									if (grille[i+2][j-2].getPiece()==null) {  //saut de pion
										grille[i+2][j-2].setSaut(true);	
									}
								}
							}
						}
					}
					if ((i>0)&&(j>0)) {		//diagonale haute gauche
						if (grille[i-1][j-1].getPiece()==null) {
							grille[i-1][j-1].setPossibleClique(true);
						}
						else {
							if (grille[i-1][j-1].getPiece().getCouleur()==Couleur.Noir) {
								if ((i>1)&&(j>1)) {
									if (grille[i-2][j-2].getPiece()==null) {
										grille[i-2][j-2].setSaut(true);
									}
								}
							}
						}
					}
					//situation ou le pion mange en arrière
					if (this.damier.getPeutMangerEnArriere()) {
						if ((i>0)&&(j<taille-1)) {
							if (grille[i-1][j+1].getPiece()!=null) {
								if (grille[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
									if ((i>1)&&(j<taille-2)) {
										if (grille[i-2][j+2].getPiece()==null) {
											grille[i-2][j+2].setSaut(true);
										}
									}
								}
							}
						}
						if ((i<taille-1)&&(j<taille-1)) {
							if (grille[i+1][j+1].getPiece()!=null) {
								if (grille[i+1][j+1].getPiece().getCouleur()==Couleur.Noir) {
									if ((i<taille-2)&&(j<taille-2)) {
										if (grille[i+2][j+2].getPiece()==null) {
											grille[i+2][j+2].setSaut(true);
										}
									}
								}
							}
						}
					}
				}	//fin tour blanc
				if ((!this.getDamier().getTourBlanc())&&(grille[i][j].getPiece().getCouleur()==Couleur.Noir)) {  //tour noir
					
					if ((i<taille-1)&&(j<taille-1)) {
						if (grille[i+1][j+1].getPiece()==null) {
							grille[i+1][j+1].setPossibleClique(true);
						}
						else{
							if (grille[i+1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
								if ((i<taille-2)&&(j<taille-2)) {
									if (grille[i+2][j+2].getPiece()==null) {
										grille[i+2][j+2].setSaut(true);
									}
								}
							}
						}
					}
					if ((i>0)&&(j<taille-1)) {
						if (grille[i-1][j+1].getPiece()==null) {
							grille[i-1][j+1].setPossibleClique(true);
						}
						else{
							if (grille[i-1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
								if ((i>1)&&(j<taille-2)) {
									if (grille[i-2][j+2].getPiece()==null) {
										grille[i-2][j+2].setSaut(true);
									}
								}
							}
						}
					}
					//depassement en arrière pour pièces noires
					if (this.damier.getPeutMangerEnArriere()) {
						if ((i>0)&&(j>0)) {
							if (grille[i-1][j-1].getPiece()!=null) {
								if (grille[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
									if ((i>1)&&(j>1)) {
										if (grille[i-2][j-2].getPiece()==null) {
											grille[i-2][j-2].setSaut(true);
										}
									}
								}
							}
						}
						if ((i<taille-1)&&(j>0)) {
							if (grille[i+1][j-1].getPiece()!=null) {
								if (grille[i+1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
									if ((i<taille-2)&&(j>1)) {
										if (grille[i+2][j-2].getPiece()==null) {
											grille[i+2][j-2].setSaut(true);
										}
									}
								}
							}
						}
					}
				}
			}
			else {	//la pièce est une reine
				int k=1,l=1;
				boolean pionRencontre = false;
				if ((this.getDamier().getTourBlanc())&&(grille[i][j].getPiece().getCouleur()==Couleur.Blanc)) {
	
					while ( (i+k<taille)&&(j-k>=0) &&(!pionRencontre) ) {	//diagonale haute droite
						if (grille[i+k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i+k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<taille)&&(j-k>0)) {
							if (grille[i+k+1][j-k-1].getPiece()!=null) {
								if (grille[i+k+1][j-k-1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i+k+1+l<taille)&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (grille[i+k+1+l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i+k+1+l][j-k-1-l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
					k=1;
					l=1;
					pionRencontre=false;
					
					while ( (i-k>=0)&&(j-k>=0) &&(!pionRencontre) ) {	//diagonale haute gauche
						if (grille[i-k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i-k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k>0)&&(j-k>0)) {
							if (grille[i-k-1][j-k-1].getPiece()!=null) {
								if (grille[i-k-1][j-k-1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i-k-1-l>=0)&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (grille[i-k-1-l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i-k-1-l][j-k-1-l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
					k=1;
					l=1;
					pionRencontre=false;
					
					while ( (i+k<taille)&&(j+k<taille) &&(!pionRencontre) ) {	//diagonale basse droite
						if (grille[i+k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i+k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<taille)&&(j+k+1<taille)) {
							if (grille[i+k+1][j+k+1].getPiece()!=null) {
								if (grille[i+k+1][j+k+1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i+k+1+l<taille)&&(j+k+1+l<taille)&&(!pionRencontre)) {
										if (grille[i+k+1+l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i+k+1+l][j+k+1+l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
					k=1;
					l=1;
					pionRencontre=false;
					
					while ( (i-k>=0)&&(j+k<taille) &&(!pionRencontre) ) {	//diagonale basse gauche
						if (grille[i-k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i-k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k-1>=0)&&(j+k+1<taille)) {
							if (grille[i-k-1][j+k+1].getPiece()!=null) {
								if (grille[i-k-1][j+k+1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i-k-1-l>=0)&&(j+k+1+l<taille)&&(!pionRencontre)) {
										if (grille[i-k-1-l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i-k-1-l][j+k+1+l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
										
				}
				if (((!this.getDamier().getTourBlanc())&&(grille[i][j].getPiece().getCouleur()==Couleur.Noir))) {	//tour noir
					
					while ( (i+k<taille)&&(j-k>=0) &&(!pionRencontre) ) {	//diagonale haute droite
						if (grille[i+k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i+k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<taille)&&(j-k>0)) {
							if (grille[i+k+1][j-k-1].getPiece()!=null) {
								if (grille[i+k+1][j-k-1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i+k+1+l<taille)&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (grille[i+k+1+l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i+k+1+l][j-k-1-l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
					k=1;
					l=1;
					pionRencontre=false;
					
					while ( (i-k>=0)&&(j-k>=0) &&(!pionRencontre) ) {	//diagonale haute gauche
						if (grille[i-k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i-k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k>0)&&(j-k>0)) {
							if (grille[i-k-1][j-k-1].getPiece()!=null) {
								if (grille[i-k-1][j-k-1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i-k-1-l>=0)&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (grille[i-k-1-l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i-k-1-l][j-k-1-l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
					k=1;
					l=1;
					pionRencontre=false;
					
					while ( (i+k<taille)&&(j+k<taille) &&(!pionRencontre) ) {	//diagonale basse droite
						if (grille[i+k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i+k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<taille)&&(j+k+1<taille)) {
							if (grille[i+k+1][j+k+1].getPiece()!=null) {
								if (grille[i+k+1][j+k+1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i+k+1+l<taille)&&(j+k+1+l<taille)&&(!pionRencontre)) {
										if (grille[i+k+1+l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i+k+1+l][j+k+1+l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
					k=1;
					l=1;
					pionRencontre=false;
					
					while ( (i-k>=0)&&(j+k<taille) &&(!pionRencontre) ) {	//diagonale basse gauche
						if (grille[i-k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							grille[i-k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k-1>=0)&&(j+k+1<taille)) {
							if (grille[i-k-1][j+k+1].getPiece()!=null) {
								if (grille[i-k-1][j+k+1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i-k-1-l>=0)&&(j+k+1+l<taille)&&(!pionRencontre)) {
										if (grille[i-k-1-l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											grille[i-k-1-l][j+k+1+l].setSaut(true);
										}
										l++;
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
