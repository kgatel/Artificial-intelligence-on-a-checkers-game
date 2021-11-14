import javax.swing.JFrame;

public class Ordi extends Joueur {
	
	public Ordi(Couleur couleur, String pseudo) {
		super(couleur,pseudo);
	}
	
	public void tourOrdi(boolean tourBlanc,boolean peutMangerEnArriere, boolean obligerLesSauts) throws CloneNotSupportedException {
		//AlgoMinMax IA;
		Arbre arbre = creerArbreCoupPossible(this.getDamier(),peutMangerEnArriere);
		//attendre(100000);
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
		Damier damierTest = (Damier) this.getDamier().clone();
		Piece pieceTest = new Piece(piece.getCouleur(),piece.getCoordonnees(),damierTest);		
		
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
				//remettre à neuf le damier à chaque fois
				damierTest.getCases()[i][j].setPossibleClique(false);
				damierTest.getCases()[i][j].setSaut(false);
				damierTest.getCases()[i][j].setClique(false);
				//
			}
		}
		
		return res;
	}
	
	public Arbre creerArbreCoupPossible(Damier damier, boolean peutMangerEnArriere) throws CloneNotSupportedException {
		int indice=0;
		int indicesCumules=0;
		
		//On travaille avec un copie du tableau de pièce pour ne pas changer les valeurs du vrai tableau
		Damier damierCopie = (Damier)this.getDamier().clone();
		TableauPiece piecesTemp = new TableauPiece(damierCopie,damierCopie.getTaille(),this.getCouleur());
		
		NoeudDame racine = new NoeudDame(damierCopie);
		//affectation de toutes les pièces
		for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
			piecesTemp.setPiece(this.getPieces(i), i);
		}
		
		for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
			if (piecesTemp.getPiece(i)!=null) {
				Coordonnees[] listeDeCoupPossible = this.ListeDesCoupsPossibles(piecesTemp.getPiece(i), peutMangerEnArriere);
				indice=0;
				while (listeDeCoupPossible[indice]!=null) {
					Damier damierIndice = (Damier)damierCopie.clone();
					damierIndice.setName(""+indicesCumules);
					TableauPiece piecesIndices = (TableauPiece)piecesTemp.clone();
					if (this.getCouleur()==Couleur.Blanc) {
						damierIndice.setPiecesBlanches(piecesIndices);
					}else {
						damierIndice.setPiecesNoires(piecesIndices);
					}					
					piecesIndices.setDamier(damierIndice);
					piecesIndices.deplacer(piecesIndices.getPiece(i).getCoordonnees().X(), piecesIndices.getPiece(i).getCoordonnees().Y(), listeDeCoupPossible[indice].X(), listeDeCoupPossible[indice].Y(), peutMangerEnArriere);
					//si une pièce a été mangée il faut la supprimer
					boolean tourBlanc = false;
					if (this.getCouleur()==Couleur.Blanc) {
						tourBlanc=true;
					}
					Coordonnees c = piecesIndices.pieceMangeeLorsDunSaut(listeDeCoupPossible[indice].X(),listeDeCoupPossible[indice].Y(),piecesIndices.getPiece(i).getCoordonnees().X(),piecesIndices.getPiece(i).getCoordonnees().Y(),tourBlanc);	//savoir s'il y a eu une pièce mangée ou non
					if (c.X()!=-1) {		//il y a eu une pièce mangée
						System.out.println(""+indicesCumules);
						damierIndice.getCase(c.X(),c.Y()).setPiece(null);  //enlever la pièce mangée
						if (tourBlanc) {
							damierIndice.getPiecesNoires().setPiece(null, damierIndice.getPiecesNoires().trouverIndice(c));
						}else {
							damierIndice.getPiecesBlanches().setPiece(null, damierIndice.getPiecesBlanches().trouverIndice(c));
						}
					}
					//
					
					//Ajout de la fenetre pour voir si tout fonctionne
					JFrame f = new JFrame(damierIndice.getName());
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setSize(this.getDamier().getTAILLE(),this.getDamier().getTAILLE()+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
					f.add(damierIndice);
					f.setVisible(true);
					//fin fenêtre
					indice++;
					indicesCumules++;
					NoeudDame nouveauNoeud = new NoeudDame(damierIndice);
					racine.ajouterSuccesseur(nouveauNoeud);
				}
			}
		}
		
		Arbre res = new Arbre(racine);
		return res;
	}
	
}
