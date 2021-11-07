import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Damier extends JPanel{
	
	private int TAILLE; //taille de la fenêtre
	private int taille; //taille=8 pour un plateau 8*8 par exemple
	private Case[][] grille;  //tableau de cases
	private boolean tourBlanc;  //savoir à qui est le tour
	private boolean tourFini;	//savoir quand le tour du joueur est fini
	private boolean sautObligatoire;	//savoir si le joueur a un saut obligatoire en cours
	private boolean sautMultiple;	//savoir si le joueur est dans une situation de saut multiple ou non
	private boolean obligerLesSauts;
	private boolean peutMangerEnArriere;
	private TableauPiece PiecesBlanches;
	private TableauPiece PiecesNoires;


	public Damier(int TAILLE,int taille,boolean obligerLesSauts,boolean peutMangerEnArriere,TableauPiece PiecesBlanches,TableauPiece PiecesNoires) {
		this.TAILLE=TAILLE;
		this.taille=taille;
		this.obligerLesSauts=obligerLesSauts;
		this.peutMangerEnArriere=peutMangerEnArriere;
		this.tourBlanc=true;
		this.tourFini=false;
		this.sautObligatoire=false;
		this.sautMultiple=false;
		this.PiecesBlanches=PiecesBlanches;
		this.PiecesNoires=PiecesNoires;
		this.grille = new Case[taille][taille];
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				Coordonnees c = new Coordonnees(i,j);
				if ( (i+j)%2==0 ) {
					grille[i][j]= new Case(Couleur.Blanc,c,this,grille);
				}
				else {
					if (j<=(taille-2)/2-1) {
						grille[i][j]= new Case(Couleur.Noir,c,Couleur.Noir,this,grille);
					}
					else {
						if (j>=taille/2+1) {
							grille[i][j]= new Case(Couleur.Noir,c,Couleur.Blanc,this,grille);
						}
						else {
							grille[i][j]= new Case(Couleur.Noir,c,this,grille);
						}
					}
				}
			}
		}
	}
	
		
	public boolean getPeutMangerEnArriere() {
		return peutMangerEnArriere;
	}

	public void setPeutMangerEnArriere(boolean peutMangerEnArriere) {
		this.peutMangerEnArriere = peutMangerEnArriere;
	}
	
	public boolean isTourFini() {
		return tourFini;
	}

	public void setTourFini(boolean tourFini) {
		this.tourFini = tourFini;
	}

	public boolean getSautMultiple() {
		return sautMultiple;
	}

	public void setSautMultiple(boolean sautMultiple) {
		this.sautMultiple = sautMultiple;
	}

	public boolean getObligerLesSauts() {
		return obligerLesSauts;
	}

	public void setObligerLesSauts(boolean obligerLesSauts) {
		this.obligerLesSauts = obligerLesSauts;
	}

	public boolean getSautObligatoire() {
		return sautObligatoire;
	}

	public void setSautObligatoire(boolean sautObligatoire) {
		this.sautObligatoire = sautObligatoire;
	}

	public int getTaille() {
		return taille;
	}
	
	public int getTAILLE() {
		return TAILLE;
	}
	
	public Case[][] getGrille(){
		return grille;
	}
	
	public boolean getTourBlanc() {
		return tourBlanc;
	}

	public void setTourBlanc(boolean tourBlanc) {
		this.tourBlanc = tourBlanc;
	}
		
	public void afficherDeplacement(int i, int j) {
		if (grille[i][j].getPiece()!=null) {  //repère la case cliquée
			if (grille[i][j].getPiece() instanceof Pion) {
				if ((tourBlanc)&&(grille[i][j].getPiece().getCouleur()==Couleur.Blanc)) {	//tour au blanc
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
					if (peutMangerEnArriere) {
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
				if ((!tourBlanc)&&(grille[i][j].getPiece().getCouleur()==Couleur.Noir)) {  //tour noir
					
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
					if (peutMangerEnArriere) {
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
				if ((tourBlanc)&&(grille[i][j].getPiece().getCouleur()==Couleur.Blanc)) {
	
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
				if (((!tourBlanc)&&(grille[i][j].getPiece().getCouleur()==Couleur.Noir))) {	//tour noir
					
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
	
	public boolean sautPossible(int x, int y) {
		boolean b=false;
		boolean pionRencontre=false;
		int k=1,l=1;
		while ( (x-k>=0) && (y-k>=0) && (!pionRencontre) ) {	//diagonale haute gauche
			if (grille[x-k][y-k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x-2>=0) && (y-2>=0) ) {		// pour un pion
			if ( (grille[x-2][y-2].getPiece()==null) && (grille[x-1][y-1].getPiece()!=null) ) {
				if ( ((grille[x-1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || (((grille[x-1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc))&&(!this.getObligerLesSauts())) )  {
					if ((peutMangerEnArriere)||(tourBlanc)) {
						grille[x-2][y-2].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (grille[x][y].getPiece() instanceof Reine) {
			while ((x-k-l>=0) && (y-k-l>=0) && (!pionRencontre)) {
				if (grille[x-k-l][y-k-l].getPiece()!=null) {
					pionRencontre=true;
				}
				else {
					if ( ((grille[x-k][y-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x-k][y-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						grille[x-k-l][y-k-l].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		k=1;l=1;pionRencontre=false;
		
		while ( (x+k<taille) && (y-k>=0) && (!pionRencontre) ) {	//diagonale haute droite
			if (grille[x+k][y-k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x+2<taille) && (y-2>=0) ) {		// pour un pion
			if ( (grille[x+2][y-2].getPiece()==null) && (grille[x+1][y-1].getPiece()!=null) ) {
				if ( ((grille[x+1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || (((grille[x+1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc))&&(!this.getObligerLesSauts())) )  {
					if ((peutMangerEnArriere)||(tourBlanc)) {	
						grille[x+2][y-2].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (grille[x][y].getPiece() instanceof Reine) {
			while ((x+k+l<taille) && (y-k-l>=0) && (!pionRencontre)) {
				if (grille[x+k+l][y-k-l].getPiece()!=null) {
					pionRencontre=true;
				}
				else {
					if ( ((grille[x+k][y-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x+k][y-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						grille[x+k+l][y-k-l].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		
		k=1;l=1;pionRencontre=false;
		
		while ( (x+k<taille) && (y+k<taille) && (!pionRencontre) ) {	//diagonale basse droite
			if (grille[x+k][y+k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x+2<taille) && (y+2<taille) ) {		// pour un pion
			if ( (grille[x+2][y+2].getPiece()==null) && (grille[x+1][y+1].getPiece()!=null) ) {
				if ( (((grille[x+1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc))&&(!this.getObligerLesSauts())) || ((grille[x+1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					if ((peutMangerEnArriere)||(!tourBlanc)) {
						grille[x+2][y+2].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (grille[x][y].getPiece() instanceof Reine) {
			while ((x+k+l<taille) && (y+k+l<taille) && (!pionRencontre)) {
				if (grille[x+k+l][y+k+l].getPiece()!=null) {
					pionRencontre=true;
				}
				else {
					if ( ((grille[x+k][y+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x+k][y+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						grille[x+k+l][y+k+l].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		
		k=1;l=1;pionRencontre=false;
		
		while ( (x-k>=0) && (y+k<taille) && (!pionRencontre) ) {	//diagonale basse gauche
			if (grille[x-k][y+k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x-2>=0) && (y+2<taille) ) {		// pour un pion
			if ( (grille[x-2][y+2].getPiece()==null) && (grille[x-1][y+1].getPiece()!=null) ) {
				if ( (((grille[x-1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc))&&(!this.getObligerLesSauts())) || ((grille[x-1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					if ((peutMangerEnArriere)||(!tourBlanc)) {
						grille[x-2][y+2].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (grille[x][y].getPiece() instanceof Reine) {
			while ((x-k-l>=0) && (y+k+l<taille) && (!pionRencontre)) {
				if (grille[x-k-l][y+k+l].getPiece()!=null) {
					pionRencontre=true;
				} 
				else {
					if ( ((grille[x-k][y+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x-k][y+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						grille[x-k-l][y+k+l].setSaut(true);
						grille[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		
		return b;
	}	
		
	public Coordonnees pieceMangee(int x, int y,int i,int j,boolean tourBlanc) { //donne les coordonnées de la pièce mangée
		Coordonnees c = new Coordonnees();
		int delta=abs(y-j);
		if (delta>=2) {
			if (y-j<0) {
				if (x-i>0) {		//diagonale haute droite
					int k=1;
					while (grille[i+k][j-k].getPiece()==null) {
						k++;
					}
					if ( ((grille[i+k][j-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[i+k][j-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i+k);
						c.setY(j-k);
					}
					
				}
				if (x-i<0)	{		//diagonale haute gauche
					int k=1;
					while (grille[i-k][j-k].getPiece()==null) {
						k++;
					}
					if ( ((grille[i-k][j-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[i-k][j-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i-k);
						c.setY(j-k);
					}
				}
			}
			else {  //y-j>0
				if (x-i>0) {		//diagonale basse droite
					int k=1;
					while (grille[i+k][j+k].getPiece()==null) {
						k++;
					}
					if ( ((grille[i+k][j+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[i+k][j+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i+k);
						c.setY(j+k);
					}
				}
				if (x-i<0)	{		//diagonale basse gauche
					int k=1;
					while (grille[i-k][j+k].getPiece()==null) {
						k++;
					}
					if ( ((grille[i-k][j+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[i-k][j+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
						c.setX(i-k);
						c.setY(j+k);
					}
				}
			}
		}
		return c;
	}
	
	public void changementTour() {
		this.tourBlanc=!tourBlanc;
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
	
	public void paintComponent(Graphics g) {
	//cases
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				grille[i][j].dessinerCase(g,i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				if (grille[i][j].getPiece()!=null) {
					grille[i][j].getPiece().dessinerPiece(g, i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				}
			}
		}
		/*for (int k=0;k<this.PiecesBlanches.getTaille();k++) {
			int x= this.PiecesBlanches.getPiece(k).getCoordonnees().getX();
			int y= this.PiecesBlanches.getPiece(k).getCoordonnees().getY();
			this.PiecesBlanches.getPiece(k).dessinerPiece(g,x*TAILLE/taille,y*TAILLE/taille, TAILLE, taille);
			x= this.PiecesNoires.getPiece(k).getCoordonnees().getX();
			y= this.PiecesNoires.getPiece(k).getCoordonnees().getY();
			this.PiecesNoires.getPiece(k).dessinerPiece(g,x*TAILLE/taille,y*TAILLE/taille, TAILLE, taille);
		}*/
	}
	
}
