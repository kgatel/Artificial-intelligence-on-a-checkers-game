
public class Ordi extends Joueur {
	
	public Ordi(Couleur couleur, String pseudo) {
		super(couleur,pseudo);
	}
	
	public void tourOrdi(boolean tourBlanc,boolean peutMangerEnArriere, boolean obligerLesSauts) {
		//AlgoMinMax IA;
		
		int compteur=0,compteur2=0,compteur3=0;
		for (int j=0;j<this.getDamier().getTaille();j++) { 
			for (int i=0;i<this.getDamier().getTaille();i++) {
				if (this.getDamier().getCases()[i][j].getPiece()!=null) {
					if ((this.getDamier().getCases()[i][j].getPiece().getCouleur()==this.getCouleur())&&(compteur<1)) {
						this.Ajoue(i,j,tourBlanc,peutMangerEnArriere,obligerLesSauts); //true pcq c'est l'ordi qui joue
						for (int jj=0;jj<this.getDamier().getTaille();jj++) { 
							for (int ii=0;ii<this.getDamier().getTaille();ii++) {
								if ((this.getDamier().getCases()[ii][jj].getPossibleClique())||(this.getDamier().getCases()[ii][jj].getSaut())&&(compteur2<1)) {
									compteur2++;
									this.Ajoue(ii,jj,tourBlanc,peutMangerEnArriere,obligerLesSauts); //true pcq c'est l'ordi qui joue
									if (this.getDamier().getSautMultiple()) {
										//attendre(500);
									}
									while (this.getDamier().getSautMultiple()) {
										for (int jjj=0;jjj<this.getDamier().getTaille();jjj++) { 
											for (int iii=0;iii<this.getDamier().getTaille();iii++) {
												if (this.getDamier().getCases()[iii][jjj].getSaut()&&(compteur3<1)) {
													this.Ajoue(iii,jjj,tourBlanc,peutMangerEnArriere,obligerLesSauts);
													compteur3++;
												}
											}
										}
									}
									compteur++;
								}
								
							}
						}
					}
				}
			}
		}
	}

	public Coordonnees[] ListeDesCoupsPossibles(Piece piece, boolean peutMangerEnArriere, boolean obligerLesSauts) {
		Coordonnees[] res = new Coordonnees[(this.getDamier().getTaille()-1)*2];
		for (int k=0;k<(this.getDamier().getTaille()-1)*2;k++) {
			res[k]=null;
		}
		Damier damierTest = this.getDamier();	
		piece.setDamier(damierTest);	//pour pas qu'on modifie notre damier
		boolean tourBlanc=false;
		if (piece.getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}
		piece.afficherDeplacement(tourBlanc, peutMangerEnArriere, obligerLesSauts);
		int indice = 0;
		for (int i=0;i<damierTest.getTaille();i++) {
			for (int j=0;j<damierTest.getTaille();j++) {
				
				if ( (damierTest.getCases()[i][j].getPossibleClique())||(damierTest.getCases()[i][j].getSaut()) ) {
					Coordonnees c = new Coordonnees(i,j);
					res[indice]=c;
					indice++;
				}
				damierTest.getCases()[i][j].setPossibleClique(false);
				damierTest.getCases()[i][j].setSaut(false);
				damierTest.getCases()[i][j].setClique(false);
			}
		}
		
		return res;
	}
	
	
	
}
