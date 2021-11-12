
public class Ordi extends Joueur {
	
	public Ordi(Couleur couleur, String pseudo) {
		super(couleur,pseudo);
	}
	
	public void tourOrdi(boolean tourBlanc,boolean peutMangerEnArriere, boolean obligerLesSauts) throws CloneNotSupportedException {
		//AlgoMinMax IA;
		//Arbre arbre = creerArbre(this.getDamier(),peutMangerEnArriere);
		
		int compteur=0,compteur2=0,compteur3=0;
		for (int j=0;j<this.getDamier().getTaille();j++) { 
			for (int i=0;i<this.getDamier().getTaille();i++) {
				if (this.getDamier().getCases()[i][j].getPiece()!=null) {
					if ((this.getDamier().getCases()[i][j].getPiece().getCouleur()==this.getCouleur())&&(compteur<1)) {
						this.Ajoue(i,j,tourBlanc,peutMangerEnArriere,obligerLesSauts);
						for (int jj=0;jj<this.getDamier().getTaille();jj++) { 
							for (int ii=0;ii<this.getDamier().getTaille();ii++) {
								if ((this.getDamier().getCases()[ii][jj].getPossibleClique())||(this.getDamier().getCases()[ii][jj].getSaut())&&(compteur2<1)) {
									compteur2++;
									this.Ajoue(ii,jj,tourBlanc,peutMangerEnArriere,obligerLesSauts);
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

	public Coordonnees[] ListeDesCoupsPossibles(Piece piece, boolean peutMangerEnArriere) throws CloneNotSupportedException {
		Coordonnees[] res = new Coordonnees[(this.getDamier().getTaille()-1)*2];
		for (int k=0;k<(this.getDamier().getTaille()-1)*2;k++) {
			res[k]=null;
		}
		//Creation d'un damier et d'une pièce copies de test
		Damier damierTest = this.getDamier().clone();
		Piece pieceTest = new Piece(piece.getCouleur(),piece.getCoordonnees(),damierTest);
		
		/*System.out.println(pieceTest.getDamier()==this.getDamier());
		System.out.println(pieceTest.getDamier().getCases()==this.getDamier().getCases());
		//System.out.println(pieceTest.getDamier().getPiecesBlanches()==this.getDamier().getPiecesBlanches());
		System.out.println();
		System.out.println(this.getDamier().getCase(1,1).getSaut());
		pieceTest.getDamier().getCase(1,1).setSaut(true);
		System.out.println(this.getDamier().getCase(1,1).getSaut());
		//piece
		System.out.println();*/
		
		System.out.println(this.getDamier().getName());
		System.out.println(pieceTest.getDamier().getName());
		pieceTest.getDamier().setName("wow");
		System.out.println(this.getDamier().getName());
		
		
		
		boolean tourBlanc=false;
		if (pieceTest.getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}
		
		pieceTest.afficherDeplacement(tourBlanc, peutMangerEnArriere);
		
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
	
	public Arbre creerArbre(Damier damier, boolean peutMangerEnArriere) throws CloneNotSupportedException {
		NoeudDame racine= new NoeudDame(damier);
		/*int indice=0;
		int indiceCumules=0;
		Damier damierTest = (Damier)this.getDamier().clone();
		//On travaille avec un copie du tableau de pièce pour ne pas changer les valeurs du vrai tableau
		TableauPiece piecesTemp = new TableauPiece(damierTest,damierTest.getTaille(),this.getCouleur());
		//affectation de toutes les pièces
		for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
			piecesTemp.setPiece(this.getPieces(i), i);
		}
		
		for (int i=0;i<this.getPieces().getTailleTabPiece();i++) {
			if (piecesTemp.getPiece(i)!=null) {
				Coordonnees[] listeDeCoupPossible = this.ListeDesCoupsPossibles(piecesTemp.getPiece(i), peutMangerEnArriere);
				indice=0;
				while (listeDeCoupPossible[indice]!=null) {
					Damier damierIndice = new Damier(damierTest.getTAILLE(),damierTest.getTaille(),"damier"+indiceCumules);
					damierIndice.setCases(damierTest.getCases());
					damierIndice.setTourBlanc(damierTest.getTourBlanc());
					damierIndice.setPiecesBlanches(damierTest.getPiecesBlanches());
					damierIndice.setPiecesNoires(damierTest.getPiecesNoires());	
					//System.out.println(damierIndice.getName());
					//System.out.println(piecesTemp.getDamier().getName());
					piecesTemp.deplacer(piecesTemp.getPiece(i).getCoordonnees().X(), piecesTemp.getPiece(i).getCoordonnees().Y(), listeDeCoupPossible[indice].X(), listeDeCoupPossible[indice].Y(), peutMangerEnArriere);
					//NoeudDame noeudDame = new
					indice++;
					indiceCumules++;
					indice++;
				}
			}
		}*/
		
		Arbre res = new Arbre(racine);
		return res;
	}
	
}
