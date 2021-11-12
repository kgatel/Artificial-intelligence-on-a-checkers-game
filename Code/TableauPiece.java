
public class TableauPiece {

	//Attributs
	private Damier damier;
	private Piece [] piece;
	private int tailleTabPiece;
	private Couleur couleur;
	
	
	public TableauPiece(Damier damier,int taille,Couleur couleur){
		this.damier=damier;
		this.piece=this.tableauPiece(taille, couleur);
		this.tailleTabPiece=(2+(taille-4)/2)*((taille-4)/2+1);
		this.couleur=couleur;
	}
	
	//Constructeurs
		
	public int getTailleTabPiece() {
		return tailleTabPiece;
	}

	public void setTailleTabPiece(int tailleTabPiece) {
		this.tailleTabPiece = tailleTabPiece;
	}	
	
	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
		for (int i=0;i<tailleTabPiece;i++) {
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


	//Méthodes
	private Piece[] tableauPiece(int taille,Couleur couleur) {
		Piece[] res = new Piece[(2+(taille-4)/2)*((taille-4)/2+1)];
		int compteurB=0,compteurN=0;
		
		for (int j=taille-1;j>=0;j--) {
			for (int i=0;i<taille;i++) {
				if ( ((i+j)%2)==1) {
					Coordonnees c = new Coordonnees(i,j);
					if ((j>=taille/2+1)&&(couleur==Couleur.Blanc)) {
						res[compteurB]=new Pion(Couleur.Blanc,c,damier);
						compteurB++;
					}
					if ((j<=taille/2-2)&&(couleur==Couleur.Noir)) {
						res[compteurN]=new Pion(Couleur.Noir,c,damier);
						compteurN++;
					}
				}
			}
		}
		return res;
	}
	
	
	public int trouverIndice(Coordonnees c) {
		int res = -1;
		int i=0;
		while ((res==-1)&&(i<this.tailleTabPiece)) {
			if (piece[i]!=null) {
				if ((this.piece[i].getCoordonnees().X()==c.X())&&(this.piece[i].getCoordonnees().Y()==c.Y())) {
					res=i;
				}
			}
			i++;
		}
		return res;
	}

	public void deplacer(int i, int j, int x, int y, boolean tourBlanc) { //déplacer la pièce de (i,j) à (x,y)		
		boolean reine = (this.getDamier().getCases()[i][j].getPiece() instanceof Reine);
		Coordonnees c1 = new Coordonnees(i,j);
		Coordonnees c2 = new Coordonnees(x,y);
		
		int indice = trouverIndice(c1);
		if (tourBlanc) {	//piece blanche
			if ((y==0)||(reine)) {
				this.setPiece(new Reine(Couleur.Blanc,c2,damier), indice);
				this.getDamier().getCases()[x][y].setPiece(new Reine(Couleur.Blanc,c2,damier));
				//on vient d'obtenir une reine
			}
			else {
				this.setPiece(new Pion(Couleur.Blanc,c2,damier), indice);
				this.getDamier().getCases()[x][y].setPiece(new Pion(Couleur.Blanc,c2,damier));
			}
		}
		else {	//piece noire
			if ((y==this.getDamier().getTaille()-1)||(reine)) {
				this.setPiece(new Reine(Couleur.Noir,c2,damier), indice);
				this.getDamier().getCases()[x][y].setPiece(new Reine(Couleur.Noir,c2,damier));
				//on vient d'obtenir une reine
			}
			else {
				this.setPiece(new Pion(Couleur.Noir,c2,damier), indice);
				this.getDamier().getCases()[x][y].setPiece(new Pion(Couleur.Noir,c2,damier));
			}
		}
		
		for (int ii=0;ii<this.damier.getTaille();ii++) {
			for (int jj=0;jj<this.damier.getTaille();jj++) {
				if (this.getDamier().getCases()[ii][jj].getPossibleClique()) {		//rénitialiser toutes les cases sur lesquelles le pion pouvait bouger
					this.getDamier().getCases()[ii][jj].setPossibleClique(false);
				}
			}
		}
		this.getDamier().getCases()[i][j].setPiece(null);
				
	}
	
	
}
