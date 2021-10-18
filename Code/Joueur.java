
public class Joueur {

	private String pseudo;
	private Couleur couleur;
	private Damier damier;  //tableau de cases
	
	public Joueur(Couleur couleur, String pseudo) {
		this.couleur=couleur;
		this.pseudo=pseudo;
		damier=null;
	}
	
	//getters and setters
	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
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
	
	public void Ajoue(int x, int y, boolean ordi2) {
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
					
					this.getDamier().deplacer(ii,jj,x,y);   //selection de la case où la pièce veut bouger
					
					Coordonnees c = new Coordonnees(); //coordonnées de la pièce sautée
					boolean b=false;
					
					c=this.getDamier().pieceMangee(x,y,ii,jj,this.getDamier().getTourBlanc());	//savoir s'il y a eu une pièce mangée ou non
					
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
										if ( ((this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Blanc)&&(this.getDamier().getTourBlanc())) || ((this.getDamier().getGrille()[i][j].getPiece().getCouleur()==Couleur.Noir)&&(!(this.getDamier().getTourBlanc()))) ) {
											if (this.getDamier().peutEtreMange(i,j)) {
												ilYaUnPionQuiPeutSauter=true;
											}
										}
									}
								}
							}
							this.getDamier().setSautObligatoire(ilYaUnPionQuiPeutSauter);
						}
						this.getDamier().changementTour();
						if (this.getDamier().partieFinie()) {
							this.getDamier().setGameOver(true);
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
	
	
	public void attendre(int ms) {
		try { Thread.sleep (ms); } 
        catch (InterruptedException e)  {  }
	}
	
	
	
}
