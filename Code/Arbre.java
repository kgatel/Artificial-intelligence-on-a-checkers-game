import java.util.ArrayList;

public class Arbre {
	
	private int profondeur;
	private NoeudDame racine;
	private Couleur couleurOrdi;
	
	public Arbre(int profondeur, Couleur couleurOrdi,Damier damier, boolean peutMangerEnArriere, boolean obligerLesSauts) throws CloneNotSupportedException {
		this.profondeur=profondeur;
		this.couleurOrdi=couleurOrdi;
		this.racine=new NoeudDame(damier,peutMangerEnArriere,obligerLesSauts);
		Damier damierCopie = (Damier)damier.clone();
		NoeudDame racine = new NoeudDame(damierCopie,peutMangerEnArriere,obligerLesSauts);
		racine.setProfondeur(0);
		genererArbreParNoeud(profondeur,racine);
		this.racine=racine;
	}
	
	
	//Accesseurs
	public NoeudDame getRacine() {
		return racine;
	}

	public void setRacine(NoeudDame racine) {
		this.racine = racine;
	}
	
	public int getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}
	
	//Méthodes
	private void genererArbreParNoeud(int profondeurArbre,NoeudDame noeud) throws CloneNotSupportedException {
		//On travaille avec un copie du tableau de pièce pour ne pas changer les valeurs du vrai tableau
			Damier damierCopie = (Damier)noeud.getDamier().clone();
			
			Couleur couleurPiece=Couleur.Blanc;	//couleur random juste pour initialiser la variable
			if (noeud.getProfondeur()%2==0) {
				couleurPiece = couleurOrdi;
			}else {
				couleurPiece = couleurOrdi.inverser(couleurOrdi);
			}
			TableauPiece piecesTemp = new TableauPiece(damierCopie,damierCopie.getTaille(),couleurPiece);
			
			//affectation de toutes les pièces
			for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
				if (couleurPiece==Couleur.Blanc) {
					if (damierCopie.getPiecesBlanches().getPiece(i)==null) {
						piecesTemp.setPiece(null,i);
					}else {
						piecesTemp.setPiece((Piece) damierCopie.getPiecesBlanches().getPiece(i).clone(), i);
					}
				}else {
					if (damierCopie.getPiecesNoires().getPiece(i)==null) {
						piecesTemp.setPiece(null,i);
					}else {
						piecesTemp.setPiece(  (Piece) (damierCopie.getPiecesNoires().getPiece(i)).clone(), i);
					}
				}
			}
			
			
			boolean sautPossible=false;
			
			boolean tourBlanc=false;
			if (couleurPiece==Couleur.Blanc) {
				tourBlanc=true;
			}
			
			if (noeud.getObligerLesSauts()){
				for (int k=0;k<piecesTemp.getTailleTabPiece();k++) {
					if (piecesTemp.getPiece(k)!=null) {
						piecesTemp.getPiece(k).setDamier(damierCopie);
						if (piecesTemp.getPiece(k).sautPossible(tourBlanc, noeud.getPeutMangerEnArriere(), true)){
							sautPossible=true;
						}
					}
				}
			}
			
			for (int i=0;i<piecesTemp.getTailleTabPiece();i++) {
				if (piecesTemp.getPiece(i)!=null) {
					if (!((sautPossible)&&(!piecesTemp.getPiece(i).sautPossible(tourBlanc, noeud.getPeutMangerEnArriere(), true)))) {
						this.genererArbreParPiece(profondeurArbre,noeud,piecesTemp,i,damierCopie,null,true, false,noeud.getPeutMangerEnArriere(),noeud.getObligerLesSauts());
					}
				}
			}
			
			
		}
		
		private void genererArbreParPiece(int profondeurArbre,NoeudDame noeud,TableauPiece piecesTemp,int i, Damier damierCopie,ArrayList<Coup> listeDeCoordonnees,boolean premierCoup,boolean sautMultiple,boolean peutMangerEnArriere,boolean obligerLesSauts) throws CloneNotSupportedException {
			boolean premierCoupCopie=premierCoup;
			if (sautMultiple) {
				damierCopie.getCase(piecesTemp.getPiece(i).getC().X(),piecesTemp.getPiece(i).getC().Y()).setSaut(false);
			}

			if ((sautMultiple)||(premierCoupCopie)){
				
				piecesTemp.getPiece(i).setDamier(damierCopie);
				Coordonnees[] listeDeCoupPossible = this.ListeDesCoupsPossibles(piecesTemp.getPiece(i), peutMangerEnArriere,obligerLesSauts,sautMultiple);
				for (int ii=0;ii<piecesTemp.getPiece(i).getDamier().getTaille();ii++) {
					for (int jj=0;jj<piecesTemp.getPiece(i).getDamier().getTaille();jj++) {
						piecesTemp.getPiece(i).getDamier().getCase(ii, jj).setSaut(false);
						piecesTemp.getPiece(i).getDamier().getCase(ii, jj).setClique(false);
						piecesTemp.getPiece(i).getDamier().getCase(ii, jj).setPossibleClique(false);
					}
				}

				
				
				int indice=0;
				while ((listeDeCoupPossible[indice]!=null)||(indice>=(racine.getDamier().getTaille()-1)*2)) {
					
					Damier damierIndice = null;
					TableauPiece piecesIndices = null;
					
					damierIndice = (Damier)damierCopie.clone();
					
					
					damierIndice.setName(noeud.getProfondeur()+1+"-"+noeud.getSuccesseurs().size());
					piecesIndices = (TableauPiece)piecesTemp.clone();
					if (piecesIndices.getCouleur()==Couleur.Blanc) {
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
					if (piecesIndices.getCouleur()==Couleur.Blanc) {
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
				
					
					Piece pieceCopie = (Piece) piecesIndices.getPiece(i).clone();
					if (b) {
						listeDeCoordonneesIndices.get(listeDeCoordonneesIndices.size()-1).setPieceApresD(pieceCopie);
						listeDeCoordonneesIndices.add(new Coup(pieceCopie,null));
						this.genererArbreParPiece(profondeurArbre,noeud, piecesIndices, i, damierIndice,listeDeCoordonneesIndices,false, true, peutMangerEnArriere,obligerLesSauts);
						
					}else {
						damierIndice.getCase(piecesIndices.getPiece(i).getC().X(), piecesIndices.getPiece(i).getC().Y()).setSaut(false);
						listeDeCoordonneesIndices.get(listeDeCoordonneesIndices.size()-1).setPieceApresD(pieceCopie);
						NoeudDame nouveauNoeud = new NoeudDame(damierIndice,listeDeCoordonneesIndices,peutMangerEnArriere,obligerLesSauts);
						
						noeud.ajouterSuccesseur(nouveauNoeud);
						
						if (profondeurArbre!=nouveauNoeud.getProfondeur()) {
							genererArbreParNoeud(profondeurArbre,nouveauNoeud);				
						}
						
						
					}
					indice++;
				}
				
			}
		}
		
		public Coordonnees[] ListeDesCoupsPossibles(Piece piece, boolean peutMangerEnArriere, boolean obligerLesSauts,boolean sautMultiple) throws CloneNotSupportedException {
			Coordonnees[] res = new Coordonnees[(racine.getDamier().getTaille()-1)*2];
			for (int k=0;k<(racine.getDamier().getTaille()-1)*2;k++) {
				res[k]=null;
			}
			//Creation d'un damier et d'une pièce copies de test
			Damier damierTest = (Damier) piece.getDamier().clone();
			Piece pieceTest = new Piece(piece.getCouleur(),piece.getC(),damierTest);		
			
			boolean tourBlanc=false;
			if (pieceTest.getCouleur()==Couleur.Blanc) {
				tourBlanc=true;
			}
			
			pieceTest.afficherDeplacement(tourBlanc, peutMangerEnArriere,obligerLesSauts,true);
			
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
		
		
}
