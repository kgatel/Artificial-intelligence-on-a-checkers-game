import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Damier extends JPanel{
	
	private JFrame frame;
	private int TAILLE; //taille de la fenêtre
	private int taille; //taille=8 pour un plateau 8*8 par exemple
	private Case[][] grille;  //tableau de cases
	private boolean tourBlanc;  //savoir à qui est le tour
	private boolean sautObligatoire;	//savoir si le joueur a un saut obligatoire
	private boolean sautMultiple;	//savoir si le joueur est dans une situation de saut multiple ou non
	private boolean obligerLesSauts;
	private boolean sauterNEstPasJoue;
	private Joueur joueur1;
	private Joueur joueur2;

	
	public Damier(JFrame frame,int TAILLE,int taille,boolean obligerLesSauts,boolean sauterNEstPasJoue,Joueur joueur1,Joueur joueur2) {
		this.frame=frame;
		this.TAILLE=TAILLE;
		this.taille=taille;
		this.tourBlanc=true;
		this.sautObligatoire=false;
		this.sautMultiple=false;
		this.obligerLesSauts=obligerLesSauts;
		this.sauterNEstPasJoue=sauterNEstPasJoue;
		this.joueur1=joueur1;
		this.joueur2=joueur2;
		this.grille = new Case[taille][taille];
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				if ( (i+j)%2==0 ) {
					grille[i][j]= new Case(Couleur.Blanc,this);
				}
				else {
					if (j<=(taille-2)/2-1) {
						grille[i][j]= new Case(Couleur.Noir,Couleur.Noir,this);
					}
					else {
						if (j>=taille/2+1) {
							grille[i][j]= new Case(Couleur.Noir,Couleur.Blanc,this);
						}
						else {
							grille[i][j]= new Case(Couleur.Noir,this);
						}
					}
				}
			}
		}
	}
	
	public boolean getSauterNEstPasJoue() {
		return sauterNEstPasJoue;
	}

	public void setSauterNEstPasJoue(boolean sauterNEstPasJoue) {
		this.sauterNEstPasJoue = sauterNEstPasJoue;
	}

	public boolean getSautMultiple() {
		return sautMultiple;
	}

	public void setSautMultiple(boolean sautMultiple) {
		this.sautMultiple = sautMultiple;
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
										//this.setSautObligatoire(true);  //indique qu'il y a un saut à faire
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
										//this.setSautObligatoire(true);
									}
								}
							}
						}
					}
					//situation ou le pion mange en arrière
					if ((i>0)&&(j<taille-1)) {
						if (grille[i-1][j+1].getPiece()!=null) {
							if (grille[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
								if ((i>1)&&(j<taille-2)) {
									if (grille[i-2][j+2].getPiece()==null) {
										grille[i-2][j+2].setSaut(true);
										//this.setSautObligatoire(true);
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
										//this.setSautObligatoire(true);
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
										//this.setSautObligatoire(true);
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
										//this.setSautObligatoire(true);
									}
								}
							}
						}
					}
					//depassement en arrière pour pièces noires
					if ((i>0)&&(j>0)) {
						if (grille[i-1][j-1].getPiece()!=null) {
							if (grille[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
								if ((i>1)&&(j>1)) {
									if (grille[i-2][j-2].getPiece()==null) {
										grille[i-2][j-2].setSaut(true);
										//this.setSautObligatoire(true);
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
										//this.setSautObligatoire(true);
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
	
	public void Ajoue(int x, int y) {
		int ii=0,jj=0;
		if (this.getSautMultiple()&&(!this.getGrille()[x][y].getSaut())) {
			//ne rien faire tant que le pion ne mange pas l'autre pion
			System.out.println("Vous devez manger le pion");
		}
		else {
			this.setSautMultiple(false);
			if ((this.getGrille()[x][y].getPossibleClique()==false)&&(this.getGrille()[x][y].getSaut()==false) ) {  //selection de la pièce à bouger
				
				for (int j=0;j<this.getTaille();j++) {   //rénitialise tout
					for (int i=0;i<this.getTaille();i++) {
						if (this.getGrille()[i][j].getPossibleClique()) {
							this.getGrille()[i][j].setPossibleClique(false);
						}
						if (this.getGrille()[i][j].getSaut()) {
							this.getGrille()[i][j].setSaut(false);
						}
						if (this.getGrille()[i][j].getClique()){
							ii=i;
							jj=j;
							this.getGrille()[i][j].setClique(false);
						}
					}
				}
				this.getGrille()[x][y].click();			
				this.afficherDeplacement(x,y);
				
			}
			
			else {
				
				if ( (this.getSautObligatoire())&&(!this.getGrille()[x][y].getSaut()) ){
					//ne rien faire tant que le saut n'est pas effectué
					System.out.println("Vous avez obligation de manger");
				}
				else {
					this.setSautObligatoire(false);
					if (this.getGrille()[x][y].getSaut()==true) {
						//le joueur vient de manger un pion
					}
					for (int j=0;j<this.getTaille();j++) {   //renitialise tous les sauts
						for (int i=0;i<this.getTaille();i++) {
							if (this.getGrille()[i][j].getSaut()==true) {
								this.getGrille()[i][j].setSaut(false);
							}
							if (this.getGrille()[i][j].getClique()){
								ii=i;
								jj=j;
							}
						}
					}
					
					this.deplacer(x,y,this.getGrille()[ii][jj].getPiece() instanceof Reine);   //selection de la case où la pièce veut bouger
				}
			}
		}
		this.repaint();
	}
	public void deplacer(int x, int y, boolean reine) {
		Coordonnees c = new Coordonnees(); //coordonnées de la pièce sautée
		boolean b=false;
		int ii=0,jj=0; //coordonnées du pion avant déplacement
		
		if (tourBlanc) {	//piece blanche
			if ((y==0)||(reine)) {
				grille[x][y].setPiece(new Reine(Couleur.Blanc));
				//on vient d'obtenir une reine
			}
			else {
				grille[x][y].setPiece(new Pion(Couleur.Blanc));
			}
		}
		else {	//piece noire
			if ((y==taille-1)||(reine)) {
				grille[x][y].setPiece(new Reine(Couleur.Noir));
				//on vient d'obtenir une reine
			}
			else {
				grille[x][y].setPiece(new Pion(Couleur.Noir));
			}
		}
		
		for (int i=0;i<this.taille;i++) {	
			for (int j=0;j<this.taille;j++) {
				if (grille[i][j].getClique()) {  //i et j sont les coordonnées de la pièce avant d'etre déplacée
					ii=i;
					jj=j;
				}
				if (grille[i][j].getPossibleClique()) {		//rénitialiser
					grille[i][j].setPossibleClique(false);
				}
			}
		}
		grille[ii][jj].click();
		boolean pion;
		pion=(grille[ii][jj].getPiece() instanceof Pion);
		grille[ii][jj].setPiece(null);
//		System.out.println(ii+" "+jj);
//		System.out.println(x+" "+y);
//		
		
		c=pieceMangee(x,y,ii,jj,tourBlanc);	//savoir s'il y a eu une pièce mangée ou non
//		System.out.println(c.getX());
		
		if (c.getX()!=-1) {		//il y a eu une pièce mangée
			grille[c.getX()][c.getY()].setPiece(null);  //enlever la pièce mangée
			if (!( (pion)&&(grille[x][y].getPiece() instanceof Reine) )){   //vérifier qu'il ne peut pas continuer à manger s'il vient d'obtenir une reine
				b = sautPossible(x,y);		//calculer les sauts de pion possibles après déplacement
			}
		}
		
		if (b!=true) {	//s'il le pion ne peut pas sauter d'autre pion après avoir sauté alors tour suivant
			if (this.obligerLesSauts) {
				this.setSautObligatoire(this.peutEtreMange(x,y));
			}
			changementTour();
			if (partieFinie()) {
				frame.dispose();
				System.out.println();
				if (tourBlanc) {
					System.out.println(joueur2.getPseudo()+" a remporté la partie");
				}
				else {
					System.out.println(joueur1.getPseudo()+" a remporté la partie");
				}
			}
		}
		else {
			this.setSautMultiple(true);
		}
		
	}
	
	public boolean peutEtreMange(int x, int y) {
		boolean b = false;
		boolean pionRencontre=false;
		int k=1;
		if ((x-1>=0)&&(x+1<taille)&&(y-1>=0)&&(y+1<taille)) {
			while ( (x-k>=0)&&(y-k>=0)&&(!pionRencontre) ) {	//pièce qui mange en haut à gauche
				if (grille[x-k][y-k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (grille[x-1][y-1].getPiece()!=null){	//mangé par un pion
				if (  ((grille[x-1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!this.tourBlanc)) || ((grille[x-1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x+1][y+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (grille[x-k][y-k].getPiece() instanceof Reine) {
				if (  (((grille[x-k][y-k].getPiece().getCouleur()==Couleur.Blanc))&&(!this.tourBlanc)) || ((grille[x-k][y-k].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x+1][y+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			k=1;
			pionRencontre = false;
			
			while ( (x+k<taille)&&(y-k>=0)&&(!pionRencontre) ) {	//pièce qui mange en haut à droite
				if (grille[x+k][y-k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (grille[x+1][y-1].getPiece()!=null){	//mangé par un pion
				if (  ((grille[x+1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!this.tourBlanc)) || ((grille[x+1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x-1][y+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (grille[x+k][y-k].getPiece() instanceof Reine) {
				if (  (((grille[x+k][y-k].getPiece().getCouleur()==Couleur.Blanc))&&(!this.tourBlanc)) || ((grille[x+k][y-k].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x-1][y+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			k=1;
			pionRencontre = false;
			
			while ( (x-k>=0)&&(y+k<taille)&&(!pionRencontre) ) {	//pièce qui mange en bas à gauche
				if (grille[x-k][y+k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (grille[x-1][y+1].getPiece()!=null){	//mangé par un pion
				if (  ((grille[x-1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!this.tourBlanc)) || ((grille[x-1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x+1][y-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (grille[x-k][y+k].getPiece() instanceof Reine) {
				if (  (((grille[x-k][y+k].getPiece().getCouleur()==Couleur.Blanc))&&(!this.tourBlanc)) || ((grille[x-k][y+k].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x+1][y-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			k=1;
			pionRencontre = false;
			
			while ( (x+k<taille)&&(y+k<taille)&&(!pionRencontre) ) {	//pièce qui mange en bas à droite
				if (grille[x+k][y+k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (grille[x+1][y+1].getPiece()!=null){	//mangé par un pion
				if (  ((grille[x+1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!this.tourBlanc)) || ((grille[x+1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x-1][y-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (grille[x+k][y+k].getPiece() instanceof Reine) {
				if (  (((grille[x+k][y+k].getPiece().getCouleur()==Couleur.Blanc))&&(!this.tourBlanc)) || ((grille[x+k][y+k].getPiece().getCouleur()==Couleur.Noir)&&(this.tourBlanc)) ) {
					if (grille[x-1][y-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			
			
		}
		return b;
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
				if ( ((grille[x-1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x-1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					grille[x-2][y-2].setSaut(true);
					grille[x][y].setClique(true);
					b=true;
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
				if ( ((grille[x+1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x+1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					grille[x+2][y-2].setSaut(true);
					grille[x][y].setClique(true);
					b=true;
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
				if ( ((grille[x+1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x+1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					grille[x+2][y+2].setSaut(true);
					grille[x][y].setClique(true);
					b=true;
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
				if ( ((grille[x-1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((grille[x-1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					grille[x-2][y+2].setSaut(true);
					grille[x][y].setClique(true);
					b=true;
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
	
	public boolean partieFinie() {
		boolean b=true;
		boolean peutBouger=false;
		for (int i=0; i<taille;i++) {
			for (int j=0;j<taille;j++) {
				if (grille[i][j].getPiece()!=null) {
					if (((grille[i][j].getPiece().getCouleur()==Couleur.Blanc)&&(tourBlanc))||((grille[i][j].getPiece().getCouleur()==Couleur.Noir)&&(!tourBlanc))) {
						b=false;
					}
				}
			}
		}
		if (b==false) {
			for (int i=0; i<taille;i++) {
				for (int j=0;j<taille;j++) {
					if (grille[i][j].getPiece()!=null) {
						if ((tourBlanc)&&(grille[i][j].getPiece().getCouleur()==Couleur.Blanc)) {	//tour au blanc
							if ((i<taille-1)&&(j>0)) {	  //diagonale haute droite
								if (grille[i+1][j-1].getPiece()==null) {	//
									peutBouger=true;
								}
								else{
									if (grille[i+1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i<taille-2)&&(j>1)) {
											if (grille[i+2][j-2].getPiece()==null) {  //saut de pion
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j>0)) {		//diagonale haute gauche
								if (grille[i-1][j-1].getPiece()==null) {
									peutBouger=true;
								}
								else {
									if (grille[i-1][j-1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i>1)&&(j>1)) {
											if (grille[i-2][j-2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (grille[i][j].getPiece() instanceof Reine) {
								if ((i>0)&&(j<taille-1)) {		//diagonale basse gauche
									if (grille[i-1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<taille-1)&&(j<taille-1)) {		//diagonale basse droite
									if (grille[i+1][j+1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//situation ou le pion mange en arrière
							if ((i>0)&&(j<taille-1)) {
								if (grille[i-1][j+1].getPiece()!=null) {
									if (grille[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
										if ((i>1)&&(j<taille-2)) {
											if (grille[i-2][j+2].getPiece()==null) {
												peutBouger=true;
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
												peutBouger=true;
											}
										}
									}
								}
							}
						}
						if ((!tourBlanc)&&(grille[i][j].getPiece().getCouleur()==Couleur.Noir)) {  //tour noir
							
							if ((i<taille-1)&&(j<taille-1)) {
								if (grille[i+1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (grille[i+1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i<taille-2)&&(j<taille-2)) {
											if (grille[i+2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if ((i>0)&&(j<taille-1)) {
								if (grille[i-1][j+1].getPiece()==null) {
									peutBouger=true;								}
								else{
									if (grille[i-1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i>1)&&(j<taille-2)) {
											if (grille[i-2][j+2].getPiece()==null) {
												peutBouger=true;
											}
										}
									}
								}
							}
							if (grille[i][j].getPiece() instanceof Reine) {
								if ((i>0)&&(j>0)) {		//haut basse gauche
									if (grille[i-1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								if ((i<taille-1)&&(j>0)) {		//diagonale haut droite
									if (grille[i+1][j-1].getPiece()==null) {
										peutBouger=true;
									}
								}
								
							}
							//depassement en arrière pour pièces noires
							if ((i>0)&&(j>0)) {
								if (grille[i-1][j-1].getPiece()!=null) {
									if (grille[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
										if ((i>1)&&(j>1)) {
											if (grille[i-2][j-2].getPiece()==null) {
												peutBouger=true;
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
		if (peutBouger==false) {
			System.out.println("Plus aucun déplacement possible");
			b=true;
		}
		return b;
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
		
	public void paint(Graphics g) {
	//cases
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				grille[i][j].dessinerCase(g,i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				if (grille[i][j].getPiece()!=null) {
					grille[i][j].getPiece().dessinerPiece(g, i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				}
			}
		}
	}
	
}
