import java.util.ArrayList;

import javax.swing.JFrame;

public class Ordi extends Joueur {
	
	public Ordi(Couleur couleur, String pseudo) {
		super(couleur,pseudo);
	}
	
	public void tourOrdi(boolean tourBlanc,boolean peutMangerEnArriere, boolean obligerLesSauts) throws CloneNotSupportedException {
		
				
		boolean afficherMeilleurCoup = false;
		boolean afficherLesDamiers = false;
		Arbre arbreAffichage = null;
		
		if ((afficherMeilleurCoup)||(afficherLesDamiers)) {
			arbreAffichage = creerArbre(1,this.getDamier(),peutMangerEnArriere);
		}
			if (afficherMeilleurCoup) {
				ArrayList<Coup> meilleurCoup = algoMinMax(arbreAffichage,tourBlanc,peutMangerEnArriere) ;
				int indice=0;
				System.out.print(meilleurCoup.get(indice).getPieceAvantD().getC());
				while (indice<meilleurCoup.size()) {
					System.out.print(meilleurCoup.get(indice).getPieceApresD().getC());
					indice++;
				}
				System.out.println();
			}
		
		
			if (afficherLesDamiers) {
				for (int indice=0;indice<arbreAffichage.getRacine().getSuccesseurs().size();indice++) {	
					JFrame f = new JFrame(arbreAffichage.getRacine().getSuccesseurs(indice).getValeur().getName());
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setSize(this.getDamier().getTAILLE(),this.getDamier().getTAILLE()+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
					f.add(arbreAffichage.getRacine().getSuccesseurs(indice).getValeur());
					f.setVisible(true);
					int indice2=0;
					System.out.print(arbreAffichage.getRacine().getSuccesseurs(indice).getListeDeCoups().get(0).getPieceAvantD().getC());
					while (indice2<arbreAffichage.getRacine().getSuccesseurs(indice).getListeDeCoups().size()) {
						System.out.print("-"+arbreAffichage.getRacine().getSuccesseurs(indice).getListeDeCoups().get(indice2).getPieceApresD().getC());
						indice2++;
					}
					System.out.println();
				}
			}
		
		boolean IA = true;
		if (!IA) {
			ordiBeteEtMechant(tourBlanc,peutMangerEnArriere,obligerLesSauts); //joue le premier coup qu'il peut jouer
		}else {
			Arbre arbre = creerArbre(1,this.getDamier(),peutMangerEnArriere);
			ArrayList<Coup> meilleurCoup = algoMinMax(arbre,tourBlanc,peutMangerEnArriere) ;
			int indice=0;
			this.Ajoue(meilleurCoup.get(0).getPieceAvantD().getC().X(), meilleurCoup.get(0).getPieceAvantD().getC().Y(), tourBlanc, peutMangerEnArriere, obligerLesSauts);
			while (indice<meilleurCoup.size()) {
				this.Ajoue(meilleurCoup.get(indice).getPieceApresD().getC().X(), meilleurCoup.get(indice).getPieceApresD().getC().Y(), tourBlanc, peutMangerEnArriere, obligerLesSauts);
				indice++;
			}
		}
	}

	private void ordiBeteEtMechant(boolean tourBlanc,boolean peutMangerEnArriere,boolean obligerLesSauts) {
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
	
	public Coordonnees[] ListeDesCoupsPossibles(Piece piece, boolean peutMangerEnArriere, boolean sautMultiple) throws CloneNotSupportedException {
		Coordonnees[] res = new Coordonnees[(this.getDamier().getTaille()-1)*2];
		for (int k=0;k<(this.getDamier().getTaille()-1)*2;k++) {
			res[k]=null;
		}
		//Creation d'un damier et d'une pièce copies de test
		Damier damierTest = (Damier) piece.getDamier().clone();
		Piece pieceTest = new Piece(piece.getCouleur(),piece.getC(),damierTest);		
		
		boolean tourBlanc=false;
		if (pieceTest.getCouleur()==Couleur.Blanc) {
			tourBlanc=true;
		}
		
		pieceTest.afficherDeplacement(tourBlanc, peutMangerEnArriere);
		
		int indice = 0;
		for (int i=0;i<damierTest.getTaille();i++) {
			for (int j=0;j<damierTest.getTaille();j++) {
				
				if ( ((damierTest.getCases()[i][j].getPossibleClique())&&(!sautMultiple))||(damierTest.getCases()[i][j].getSaut()) ) {
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
	
	private Arbre creerArbre(int profondeur,Damier damier, boolean peutMangerEnArriere) throws CloneNotSupportedException {
		
		//On travaille avec un copie du tableau de pièce pour ne pas changer les valeurs du vrai tableau
		Damier damierCopie = (Damier)this.getDamier().clone();
		TableauPiece piecesTemp = new TableauPiece(damierCopie,damierCopie.getTaille(),this.getCouleur());
		
		NoeudDame racine = new NoeudDame(damierCopie);
		racine.setProfondeur(0);
		
		//affectation de toutes les pièces
		for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
			if (this.getPieces(i)==null) {
				piecesTemp.setPiece(null,i);
			}else {
				piecesTemp.setPiece((Piece) this.getPieces(i).clone(), i);
			}
		}
		
	
		for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
			if (piecesTemp.getPiece(i)!=null) {				
				this.genererArbreParPiece(racine,piecesTemp,i,damierCopie,null,true, false,peutMangerEnArriere);
			}
		}
		Arbre res = new Arbre(profondeur,racine);
		return res;
	}
	
	private void genererArbreParPiece(NoeudDame racine,TableauPiece piecesTemp,int i, Damier damierCopie,ArrayList<Coup> listeDeCoordonnees,boolean premierCoup,boolean sautMultiple,boolean peutMangerEnArriere) throws CloneNotSupportedException {
		boolean premierCoupCopie=premierCoup;
		if (sautMultiple) {
			damierCopie.getCase(piecesTemp.getPiece(i).getC().X(),piecesTemp.getPiece(i).getC().Y()).setSaut(false);
		}

		if ((sautMultiple)||(premierCoupCopie)){
			
			Coordonnees[] listeDeCoupPossible = this.ListeDesCoupsPossibles(piecesTemp.getPiece(i), peutMangerEnArriere,sautMultiple);
			int indice=0;
			while (listeDeCoupPossible[indice]!=null) {
				
				Damier damierIndice = null;
				TableauPiece piecesIndices = null;
				
				damierIndice = (Damier)damierCopie.clone();
				damierIndice.setName(""+racine.getSuccesseurs().size());
				piecesIndices = (TableauPiece)piecesTemp.clone();
				if (this.getCouleur()==Couleur.Blanc) {
					damierIndice.setPiecesBlanches(piecesIndices);
				}else {
					damierIndice.setPiecesNoires(piecesIndices);
				}
				piecesIndices.setDamier(damierIndice);
				
				ArrayList<Coup> listeDeCoordonneesIndices = null;
				if (premierCoupCopie) {
					Piece pieceInitiale=(Piece) piecesTemp.getPiece(i).clone();
					listeDeCoordonneesIndices = new ArrayList<Coup>();
					listeDeCoordonneesIndices.add(new Coup(pieceInitiale,null));
				}else {
					listeDeCoordonneesIndices=listeDeCoordonnees;
				}
				
				boolean pion=(damierIndice.getPiece(piecesIndices.getPiece(i).getC().X(),piecesIndices.getPiece(i).getC().Y()) instanceof Pion);
				boolean b=false;
				boolean tourBlanc=false;
				if (this.getCouleur()==Couleur.Blanc) {
					tourBlanc=true;
				}
				
				//garder en mémoire les coordonnees de la pièce avant qu'elle se déplace
				int x = piecesIndices.getPiece(i).getC().X();
				int y = piecesIndices.getPiece(i).getC().Y();
				
				piecesIndices.deplacer(x, y, listeDeCoupPossible[indice].X(), listeDeCoupPossible[indice].Y(), tourBlanc);
				
				//si une pièce a été mangée il faut la supprimer
				
				Coordonnees c = piecesIndices.pieceMangeeLorsDunSaut(x,y,piecesIndices.getPiece(i).getC().X(),piecesIndices.getPiece(i).getC().Y(),tourBlanc);	//savoir s'il y a eu une pièce mangée ou non
				if (c.X()!=-1) {		//il y a eu une pièce mangée
					damierIndice.getCase(c.X(),c.Y()).setPiece(null);  //enlever la pièce mangée
					if (tourBlanc) {
						damierIndice.getPiecesNoires().setPiece(null, damierIndice.getPiecesNoires().trouverIndice(c));
					}else {
						damierIndice.getPiecesBlanches().setPiece(null, damierIndice.getPiecesBlanches().trouverIndice(c));
					}
					if (!( (pion)&&(damierIndice.getCase(piecesIndices.getPiece(i).getC().X(),piecesIndices.getPiece(i).getC().Y()).getPiece() instanceof Reine) )){   //vérifier qu'il ne peut pas continuer à manger s'il vient d'obtenir une reine
						b = damierIndice.getCase(piecesIndices.getPiece(i).getC().X(),piecesIndices.getPiece(i).getC().Y()).getPiece().sautPossible(tourBlanc,peutMangerEnArriere,true);		//si b=true alors le joueur peut continuer à sauter
					}
				}
				
				if (this.getCouleur()==Couleur.Blanc) {
					damierIndice.setPiecesBlanches(piecesIndices);
				}else {
					damierIndice.setPiecesNoires(piecesIndices);
				}			
				Piece pieceCopie = (Piece) piecesIndices.getPiece(i).clone();
				if (b) {
					listeDeCoordonneesIndices.get(listeDeCoordonneesIndices.size()-1).setPieceApresD(pieceCopie);
					listeDeCoordonneesIndices.add(new Coup(pieceCopie,null));
					this.genererArbreParPiece(racine, piecesIndices, i, damierIndice,listeDeCoordonneesIndices,false, true, peutMangerEnArriere);
					
				}else {
					damierIndice.getCase(piecesIndices.getPiece(i).getC().X(), piecesIndices.getPiece(i).getC().Y()).setSaut(false);
					listeDeCoordonneesIndices.get(listeDeCoordonneesIndices.size()-1).setPieceApresD(pieceCopie);
					NoeudDame nouveauNoeud = new NoeudDame(damierIndice,listeDeCoordonneesIndices);
					racine.ajouterSuccesseur(nouveauNoeud);
				}
				indice++;
			}
			
		}
	}
	
	private ArrayList<Coup> algoMinMax(Arbre arbre, boolean tourBlanc,boolean peutMangerEnArriere){ //besoin de la profondeur, noeud, créer la classe feuille 
		ArrayList<Coup> res =minMax(arbre.getRacine(),arbre.getProfondeur(),tourBlanc,peutMangerEnArriere).getListeDeCoup();
		return res;
	}
		
	
	private Resultat_minMax minMax(NoeudDame noeud,int profondeurArbre,boolean tourBlanc,boolean peutMangerEnArriere) {
		Resultat_minMax res=new Resultat_minMax();
		res.setValeur(0); 	//val=0

		if (noeud.getProfondeur()==profondeurArbre) {
			res.setValeur(noeud.Heuristique(peutMangerEnArriere));
		}
		else {
			if (  ((tourBlanc)&&(this.getCouleur()==Couleur.Blanc)) || ((!tourBlanc)&&(this.getCouleur()==Couleur.Noir)) ) {          //faudra remplacer si c'est l'IA qui est en train de jouer ou le joueur
				res.setValeur(-100000);
				for(int i=0;i<noeud.getSuccesseurs().size();i++){
					//res.setValeur(max(res.getValeur(), minMax(noeud.getSuccesseurs(j),profondeurArbre,!tourBlanc).getValeur()));
					int tmp=minMax(noeud.getSuccesseurs(i),profondeurArbre,tourBlanc,peutMangerEnArriere).getValeur();
					if (res.getValeur()<tmp) {
						res.setValeur(tmp);
						if (noeud.getSuccesseurs(i).getProfondeur()==1) {
							res.setListeDeCoup(noeud.getSuccesseurs(i).getListeDeCoups());
						}
					}
					//
				}
			}
			else {
				res.setValeur(100000);
				for(int j=0;j<noeud.getSuccesseurs().size();j++){
					res.setValeur(min(res.getValeur(), minMax(noeud.getSuccesseurs(j),profondeurArbre,!tourBlanc,peutMangerEnArriere).getValeur()));
				}
			}
		}
		return res;
	}
	
	private int max(int i,int j) {
		if (i>j) {
			return i;
		}else {
			return j;
		}
	}
	
	private int min(int i,int j) {
		return -max(-i,-j);
	}
	
}
