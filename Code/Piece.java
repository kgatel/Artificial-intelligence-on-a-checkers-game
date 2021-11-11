import java.awt.Color;
import java.awt.Graphics;

public class Piece {

	private Couleur couleur;
	private Coordonnees c;
	private Damier damier;
	
	public Piece(Couleur couleur,Coordonnees c,Damier damier) {
		this.couleur=couleur;
		this.c=c;
		this.damier=damier;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	
	public Coordonnees getCoordonnees() {
		return c;
	}

	public void setCoordonnees(Coordonnees c) {
		this.c = c;
	}

	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
	}

	
	//Méthodes
	
	public boolean peutEtreMange(boolean tourBlanc,int taille,boolean peutMangerEnArriere, boolean obligerLesSauts) {
		boolean b = false;
		boolean pionRencontre=false;
		int k=1;
		if ((this.c.getX()-1>=0)&&(this.c.getX()+1<taille)&&(this.c.getY()-1>=0)&&(this.c.getY()+1<taille)) {
			while ( (this.c.getX()-k>=0)&&(this.c.getY()-k>=0)&&(!pionRencontre) ) {	//pièce qui mange en haut à gauche
				if (damier.getCases()[this.c.getX()-k][this.c.getY()-k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (damier.getCases()[this.c.getX()-1][this.c.getY()-1].getPiece()!=null){	//mangé par un pion
				if (  (((damier.getCases()[this.c.getX()-1][this.c.getY()-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc))&&(!obligerLesSauts)) || ((damier.getCases()[this.c.getX()-1][this.c.getY()-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
					if (damier.getCases()[this.c.getX()+1][this.c.getY()+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (damier.getCases()[this.c.getX()-k][this.c.getY()-k].getPiece() instanceof Reine) {
				if (  (((damier.getCases()[this.c.getX()-k][this.c.getY()-k].getPiece().getCouleur()==Couleur.Blanc))&&(!tourBlanc)) || ((damier.getCases()[this.c.getX()-k][this.c.getY()-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
					if (damier.getCases()[this.c.getX()+1][this.c.getY()+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			k=1;
			pionRencontre = false;
			
			while ( (this.c.getX()+k<taille)&&(this.c.getY()-k>=0)&&(!pionRencontre) ) {	//pièce qui mange en haut à droite
				if (damier.getCases()[this.c.getX()+k][this.c.getY()-k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (damier.getCases()[this.c.getX()+1][this.c.getY()-1].getPiece()!=null){	//mangé par un pion
				if (  (((damier.getCases()[this.c.getX()+1][this.c.getY()-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc))&&(!obligerLesSauts)) || ((damier.getCases()[this.c.getX()+1][this.c.getY()-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
					if (damier.getCases()[this.c.getX()-1][this.c.getY()+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (damier.getCases()[this.c.getX()+k][this.c.getY()-k].getPiece() instanceof Reine) {
				if (  (((damier.getCases()[this.c.getX()+k][this.c.getY()-k].getPiece().getCouleur()==Couleur.Blanc))&&(!tourBlanc)) || ((damier.getCases()[this.c.getX()+k][this.c.getY()-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
					if (damier.getCases()[this.c.getX()-1][this.c.getY()+1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			k=1;
			pionRencontre = false;
			
			while ( (this.c.getX()-k>=0)&&(this.c.getY()+k<taille)&&(!pionRencontre) ) {	//pièce qui mange en bas à gauche
				if (damier.getCases()[this.c.getX()-k][this.c.getY()+k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (damier.getCases()[this.c.getX()-1][this.c.getY()+1].getPiece()!=null){	//mangé par un pion
				if (  ((damier.getCases()[this.c.getX()-1][this.c.getY()+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || (((damier.getCases()[this.c.getX()-1][this.c.getY()+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc))&&(!obligerLesSauts)) ) {
					if (damier.getCases()[this.c.getX()+1][this.c.getY()-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (damier.getCases()[this.c.getX()-k][this.c.getY()+k].getPiece() instanceof Reine) {
				if (  (((damier.getCases()[this.c.getX()-k][this.c.getY()+k].getPiece().getCouleur()==Couleur.Blanc))&&(!tourBlanc)) || ((damier.getCases()[this.c.getX()-k][this.c.getY()+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
					if (damier.getCases()[this.c.getX()+1][this.c.getY()-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			k=1;
			pionRencontre = false;
			
			while ( (this.c.getX()+k<taille)&&(this.c.getY()+k<taille)&&(!pionRencontre) ) {	//pièce qui mange en bas à droite
				if (damier.getCases()[this.c.getX()+k][this.c.getY()+k].getPiece()!=null){
					pionRencontre=true;
				}
				k++;
			}
			k--;
			if (damier.getCases()[this.c.getX()+1][this.c.getY()+1].getPiece()!=null){	//mangé par un pion
				if (  ((damier.getCases()[this.c.getX()+1][this.c.getY()+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || (((damier.getCases()[this.c.getX()+1][this.c.getY()+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc))&&(!obligerLesSauts)) ) {
					if (damier.getCases()[this.c.getX()-1][this.c.getY()-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			if (damier.getCases()[this.c.getX()+k][this.c.getY()+k].getPiece() instanceof Reine) {
				if (  (((damier.getCases()[this.c.getX()+k][this.c.getY()+k].getPiece().getCouleur()==Couleur.Blanc))&&(!tourBlanc)) || ((damier.getCases()[this.c.getX()+k][this.c.getY()+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) ) {
					if (damier.getCases()[this.c.getX()-1][this.c.getY()-1].getPiece()==null) {	//espace libre
						b=true;
					}
				}
			}
			
			
		}
		return b;
	}
	
	public boolean sautPossible(boolean tourBlanc, boolean peutMangerEnArriere,boolean obligerLesSauts) {
		int x = this.c.getX();
		int y = this.c.getY();
		boolean b=false;
		boolean pionRencontre=false;
		int k=1,l=1;
		while ( (x-k>=0) && (y-k>=0) && (!pionRencontre) ) {	//diagonale haute gauche
			if (this.getDamier().getCases()[x-k][y-k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x-2>=0) && (y-2>=0) ) {		// pour un pion
			if ( (this.getDamier().getCases()[x-2][y-2].getPiece()==null) && (this.getDamier().getCases()[x-1][y-1].getPiece()!=null) ) {
				if ( ((this.getDamier().getCases()[x-1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || (((this.getDamier().getCases()[x-1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc))&&(!obligerLesSauts)) )  {
					if ((peutMangerEnArriere)||(tourBlanc)) {
						this.getDamier().getCases()[x-2][y-2].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (this.getDamier().getCases()[x][y].getPiece() instanceof Reine) {
			while ((x-k-l>=0) && (y-k-l>=0) && (!pionRencontre)) {
				if (this.getDamier().getCases()[x-k-l][y-k-l].getPiece()!=null) {
					pionRencontre=true;
				}
				else {
					if ( ((this.getDamier().getCases()[x-k][y-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[x-k][y-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						this.getDamier().getCases()[x-k-l][y-k-l].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		k=1;l=1;pionRencontre=false;
		
		while ( (x+k<this.getDamier().getTaille()) && (y-k>=0) && (!pionRencontre) ) {	//diagonale haute droite
			if (this.getDamier().getCases()[x+k][y-k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x+2<this.getDamier().getTaille()) && (y-2>=0) ) {		// pour un pion
			if ( (this.getDamier().getCases()[x+2][y-2].getPiece()==null) && (this.getDamier().getCases()[x+1][y-1].getPiece()!=null) ) {
				if ( ((this.getDamier().getCases()[x+1][y-1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || (((this.getDamier().getCases()[x+1][y-1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc))&&(!obligerLesSauts)) )  {
					if ((peutMangerEnArriere)||(tourBlanc)) {	
						this.getDamier().getCases()[x+2][y-2].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (this.getDamier().getCases()[x][y].getPiece() instanceof Reine) {
			while ((x+k+l<this.getDamier().getTaille()) && (y-k-l>=0) && (!pionRencontre)) {
				if (this.getDamier().getCases()[x+k+l][y-k-l].getPiece()!=null) {
					pionRencontre=true;
				}
				else {
					if ( ((this.getDamier().getCases()[x+k][y-k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[x+k][y-k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						this.getDamier().getCases()[x+k+l][y-k-l].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		
		k=1;l=1;pionRencontre=false;
		
		while ( (x+k<this.getDamier().getTaille()) && (y+k<this.getDamier().getTaille()) && (!pionRencontre) ) {	//diagonale basse droite
			if (this.getDamier().getCases()[x+k][y+k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x+2<this.getDamier().getTaille()) && (y+2<this.getDamier().getTaille()) ) {		// pour un pion
			if ( (this.getDamier().getCases()[x+2][y+2].getPiece()==null) && (this.getDamier().getCases()[x+1][y+1].getPiece()!=null) ) {
				if ( (((this.getDamier().getCases()[x+1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc))&&(!obligerLesSauts)) || ((this.getDamier().getCases()[x+1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					if ((peutMangerEnArriere)||(!tourBlanc)) {
						this.getDamier().getCases()[x+2][y+2].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (this.getDamier().getCases()[x][y].getPiece() instanceof Reine) {
			while ((x+k+l<this.getDamier().getTaille()) && (y+k+l<this.getDamier().getTaille()) && (!pionRencontre)) {
				if (this.getDamier().getCases()[x+k+l][y+k+l].getPiece()!=null) {
					pionRencontre=true;
				}
				else {
					if ( ((this.getDamier().getCases()[x+k][y+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[x+k][y+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						this.getDamier().getCases()[x+k+l][y+k+l].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		
		k=1;l=1;pionRencontre=false;
		
		while ( (x-k>=0) && (y+k<this.getDamier().getTaille()) && (!pionRencontre) ) {	//diagonale basse gauche
			if (this.getDamier().getCases()[x-k][y+k].getPiece()!=null) {
				pionRencontre=true;
			}
			k++;
		}
		k--;
		if ( (x-2>=0) && (y+2<this.getDamier().getTaille()) ) {		// pour un pion
			if ( (this.getDamier().getCases()[x-2][y+2].getPiece()==null) && (this.getDamier().getCases()[x-1][y+1].getPiece()!=null) ) {
				if ( (((this.getDamier().getCases()[x-1][y+1].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc))&&(!obligerLesSauts)) || ((this.getDamier().getCases()[x-1][y+1].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
					if ((peutMangerEnArriere)||(!tourBlanc)) {
						this.getDamier().getCases()[x-2][y+2].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
			}
		}
		pionRencontre=false;
		if (this.getDamier().getCases()[x][y].getPiece() instanceof Reine) {
			while ((x-k-l>=0) && (y+k+l<this.getDamier().getTaille()) && (!pionRencontre)) {
				if (this.getDamier().getCases()[x-k-l][y+k+l].getPiece()!=null) {
					pionRencontre=true;
				} 
				else {
					if ( ((this.getDamier().getCases()[x-k][y+k].getPiece().getCouleur()==Couleur.Blanc)&&(!tourBlanc)) || ((this.getDamier().getCases()[x-k][y+k].getPiece().getCouleur()==Couleur.Noir)&&(tourBlanc)) )  {
						this.getDamier().getCases()[x-k-l][y+k+l].setSaut(true);
						this.getDamier().getCases()[x][y].setClique(true);
						b=true;
					}
				}
				l++;
			}
		}
		
		return b;
	}
	
	public void afficherDeplacement(boolean tourBlanc, boolean peutMangerEnArriere) {
		int i=this.c.getX();
		int j=this.c.getY();
		if (this.getDamier().getCases()[i][j].getPiece()!=null) {  
			if (this.getDamier().getCases()[i][j].getPiece() instanceof Pion) {
				if ((tourBlanc)&&(this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Blanc)) {	//tour au blanc
					if ((i<this.getDamier().getTaille()-1)&&(j>0)) {	  //diagonale haute droite
						if (this.getDamier().getCases()[i+1][j-1].getPiece()==null) {	//
							this.getDamier().getCases()[i+1][j-1].setPossibleClique(true);
						}
						else{
							if (this.getDamier().getCases()[i+1][j-1].getPiece().getCouleur()==Couleur.Noir) {
								if ((i<this.getDamier().getTaille()-2)&&(j>1)) {
									if (this.getDamier().getCases()[i+2][j-2].getPiece()==null) {  //saut de pion
										this.getDamier().getCases()[i+2][j-2].setSaut(true);	
										//this.setSautObligatoire(true);  //indique qu'il y a un saut à faire
									}
								}
							}
						}
					}
					if ((i>0)&&(j>0)) {		//diagonale haute gauche
						if (this.getDamier().getCases()[i-1][j-1].getPiece()==null) {
							this.getDamier().getCases()[i-1][j-1].setPossibleClique(true);
						}
						else {
							if (this.getDamier().getCases()[i-1][j-1].getPiece().getCouleur()==Couleur.Noir) {
								if ((i>1)&&(j>1)) {
									if (this.getDamier().getCases()[i-2][j-2].getPiece()==null) {
										this.getDamier().getCases()[i-2][j-2].setSaut(true);
										//this.setSautObligatoire(true);
									}
								}
							}
						}
					}
					//situation ou le pion mange en arrière
					if (peutMangerEnArriere) {
						if ((i>0)&&(j<this.getDamier().getTaille()-1)) {
							if (this.getDamier().getCases()[i-1][j+1].getPiece()!=null) {
								if (this.getDamier().getCases()[i-1][j+1].getPiece().getCouleur()==Couleur.Noir) {
									if ((i>1)&&(j<this.getDamier().getTaille()-2)) {
										if (this.getDamier().getCases()[i-2][j+2].getPiece()==null) {
											this.getDamier().getCases()[i-2][j+2].setSaut(true);
											//this.setSautObligatoire(true);
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
											this.getDamier().getCases()[i+2][j+2].setSaut(true);
											//this.setSautObligatoire(true);
										}
									}
								}
							}
						}
					}
				}	//fin tour blanc
				if ((!tourBlanc)&&(this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Noir)) {  //tour noir
					
					if ((i<this.getDamier().getTaille()-1)&&(j<this.getDamier().getTaille()-1)) {
						if (this.getDamier().getCases()[i+1][j+1].getPiece()==null) {
							this.getDamier().getCases()[i+1][j+1].setPossibleClique(true);
						}
						else{
							if (this.getDamier().getCases()[i+1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
								if ((i<this.getDamier().getTaille()-2)&&(j<this.getDamier().getTaille()-2)) {
									if (this.getDamier().getCases()[i+2][j+2].getPiece()==null) {
										this.getDamier().getCases()[i+2][j+2].setSaut(true);
										//this.setSautObligatoire(true);
									}
								}
							}
						}
					}
					if ((i>0)&&(j<this.getDamier().getTaille()-1)) {
						if (this.getDamier().getCases()[i-1][j+1].getPiece()==null) {
							this.getDamier().getCases()[i-1][j+1].setPossibleClique(true);
						}
						else{
							if (this.getDamier().getCases()[i-1][j+1].getPiece().getCouleur()==Couleur.Blanc) {
								if ((i>1)&&(j<this.getDamier().getTaille()-2)) {
									if (this.getDamier().getCases()[i-2][j+2].getPiece()==null) {
										this.getDamier().getCases()[i-2][j+2].setSaut(true);
										//this.setSautObligatoire(true);
									}
								}
							}
						}
					}
					//depassement en arrière pour pièces noires
					if (peutMangerEnArriere) {
						if ((i>0)&&(j>0)) {
							if (this.getDamier().getCases()[i-1][j-1].getPiece()!=null) {
								if (this.getDamier().getCases()[i-1][j-1].getPiece().getCouleur()==Couleur.Blanc) {
									if ((i>1)&&(j>1)) {
										if (this.getDamier().getCases()[i-2][j-2].getPiece()==null) {
											this.getDamier().getCases()[i-2][j-2].setSaut(true);
											//this.setSautObligatoire(true);
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
											this.getDamier().getCases()[i+2][j-2].setSaut(true);
											//this.setSautObligatoire(true);
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
				if ((tourBlanc)&&(this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Blanc)) {
	
					while ( (i+k<this.getDamier().getTaille())&&(j-k>=0) &&(!pionRencontre) ) {	//diagonale haute droite
						if (this.getDamier().getCases()[i+k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i+k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<this.getDamier().getTaille())&&(j-k>0)) {
			
	
					if (this.getDamier().getCases()[i+k+1][j-k-1].getPiece()!=null) {
								if (this.getDamier().getCases()[i+k+1][j-k-1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i+k+1+l<this.getDamier().getTaille())&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i+k+1+l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i+k+1+l][j-k-1-l].setSaut(true);
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
						if (this.getDamier().getCases()[i-k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i-k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k>0)&&(j-k>0)) {
							if (this.getDamier().getCases()[i-k-1][j-k-1].getPiece()!=null) {
								if (this.getDamier().getCases()[i-k-1][j-k-1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i-k-1-l>=0)&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i-k-1-l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i-k-1-l][j-k-1-l].setSaut(true);
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
					
					while ( (i+k<this.getDamier().getTaille())&&(j+k<this.getDamier().getTaille()) &&(!pionRencontre) ) {	//diagonale basse droite
						if (this.getDamier().getCases()[i+k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i+k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<this.getDamier().getTaille())&&(j+k+1<this.getDamier().getTaille())) {
							if (this.getDamier().getCases()[i+k+1][j+k+1].getPiece()!=null) {
								if (this.getDamier().getCases()[i+k+1][j+k+1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i+k+1+l<this.getDamier().getTaille())&&(j+k+1+l<this.getDamier().getTaille())&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i+k+1+l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i+k+1+l][j+k+1+l].setSaut(true);
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
					
					while ( (i-k>=0)&&(j+k<this.getDamier().getTaille()) &&(!pionRencontre) ) {	//diagonale basse gauche
						if (this.getDamier().getCases()[i-k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i-k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k-1>=0)&&(j+k+1<this.getDamier().getTaille())) {
							if (this.getDamier().getCases()[i-k-1][j+k+1].getPiece()!=null) {
								if (this.getDamier().getCases()[i-k-1][j+k+1].getPiece().getCouleur()==Couleur.Noir) {
									while ((i-k-1-l>=0)&&(j+k+1+l<this.getDamier().getTaille())&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i-k-1-l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i-k-1-l][j+k+1+l].setSaut(true);
										}
										l++;
									}
								}
							}
						}
					}
										
				}
				if (((!tourBlanc)&&(this.getDamier().getCases()[i][j].getPiece().getCouleur()==Couleur.Noir))) {	//tour noir
					
					while ( (i+k<this.getDamier().getTaille())&&(j-k>=0) &&(!pionRencontre) ) {	//diagonale haute droite
						if (this.getDamier().getCases()[i+k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i+k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<this.getDamier().getTaille())&&(j-k>0)) {
							if (this.getDamier().getCases()[i+k+1][j-k-1].getPiece()!=null) {
								if (this.getDamier().getCases()[i+k+1][j-k-1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i+k+1+l<this.getDamier().getTaille())&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i+k+1+l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i+k+1+l][j-k-1-l].setSaut(true);
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
						if (this.getDamier().getCases()[i-k][j-k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i-k][j-k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k>0)&&(j-k>0)) {
							if (this.getDamier().getCases()[i-k-1][j-k-1].getPiece()!=null) {
								if (this.getDamier().getCases()[i-k-1][j-k-1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i-k-1-l>=0)&&(j-k-1-l>=0)&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i-k-1-l][j-k-1-l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i-k-1-l][j-k-1-l].setSaut(true);
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
					
					while ( (i+k<this.getDamier().getTaille())&&(j+k<this.getDamier().getTaille()) &&(!pionRencontre) ) {	//diagonale basse droite
						if (this.getDamier().getCases()[i+k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i+k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i+k+1<this.getDamier().getTaille())&&(j+k+1<this.getDamier().getTaille())) {
							if (this.getDamier().getCases()[i+k+1][j+k+1].getPiece()!=null) {
								if (this.getDamier().getCases()[i+k+1][j+k+1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i+k+1+l<this.getDamier().getTaille())&&(j+k+1+l<this.getDamier().getTaille())&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i+k+1+l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i+k+1+l][j+k+1+l].setSaut(true);
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
					
					while ( (i-k>=0)&&(j+k<this.getDamier().getTaille()) &&(!pionRencontre) ) {	//diagonale basse gauche
						if (this.getDamier().getCases()[i-k][j+k].getPiece()!=null) {
							pionRencontre=true;
						}
						else {
							this.getDamier().getCases()[i-k][j+k].setPossibleClique(true);
						}
						k++;
					}
					k=k-2;
					if (pionRencontre) {
						pionRencontre=false;
						if ((i-k-1>=0)&&(j+k+1<this.getDamier().getTaille())) {
							if (this.getDamier().getCases()[i-k-1][j+k+1].getPiece()!=null) {
								if (this.getDamier().getCases()[i-k-1][j+k+1].getPiece().getCouleur()==Couleur.Blanc) {
									while ((i-k-1-l>=0)&&(j+k+1+l<this.getDamier().getTaille())&&(!pionRencontre)) {
										if (this.getDamier().getCases()[i-k-1-l][j+k+1+l].getPiece()!=null) {
											pionRencontre=true;
										}
										else {
											this.getDamier().getCases()[i-k-1-l][j+k+1+l].setSaut(true);
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
	
	public String toString() {
		return "("+c.getX()+","+c.getY()+")";
	}
	
	public void dessinerPiece(Graphics g, int x, int y,int TAILLE,int taille) {
		switch(couleur) {
		case Blanc : 
			g.setColor(new Color(255,255,220));
			break;
		case Noir :
			g.setColor(Color.BLACK);
			break;
		}
		g.fillOval(x+TAILLE/taille/20,y+TAILLE/taille/20,TAILLE/taille*9/10,TAILLE/taille*9/10);   //fois 9/10 pour que la taille de la pièce soit un peu plus petite que la case
		if (this instanceof Reine) {
			g.setColor(Color.RED);
			g.fillOval(x+TAILLE/taille/20*5,y+TAILLE/taille/20*5,TAILLE/taille*5/10,TAILLE/taille*5/10);
		}
	}
}
