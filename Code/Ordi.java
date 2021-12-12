import java.util.ArrayList;
import javax.swing.JFrame;

public class Ordi extends Joueur {
	
	public Ordi(Couleur couleur, String pseudo, boolean peutMangerEnArriere, boolean obligerLesSauts) {
		super(couleur,pseudo,peutMangerEnArriere,obligerLesSauts);
	}
	
	public void tourOrdi(int difficulte,boolean tourBlanc) throws CloneNotSupportedException {
		
		int profondeurArbre = difficulte;
		
		boolean afficherMeilleurCoup = false;
		boolean afficherLesDamiers = false;
		boolean afficherLesCoups = false;
		int profondeurAffichage = 3;		//pas plus de 5 sinon ça prend beauuucoup de temps
		Arbre arbreAffichage = null;
		
		if ((afficherMeilleurCoup)||(afficherLesDamiers)||(afficherLesCoups)) {
			arbreAffichage = new Arbre(profondeurAffichage,this.getCouleur(),this.getDamier(),this.getPeutMangerEnArriere(),this.getObligerLesSauts());
		}
		if (afficherMeilleurCoup) {
			ArrayList<Coup> meilleurCoup = algoMinMax(arbreAffichage,tourBlanc,this.getPeutMangerEnArriere(),this.getObligerLesSauts()) ;
			int indice=0;
			System.out.print(meilleurCoup.get(indice).getPieceAvantD().getC());
			while (indice<meilleurCoup.size()) {
				System.out.print(meilleurCoup.get(indice).getPieceApresD().getC());
				indice++;
			}
			System.out.println();
			
		}
		if (afficherLesDamiers) {
			for (int i=0;i<arbreAffichage.getRacine().getSuccesseurs().size();i++) {
				ArrayList<NoeudDame> ListeNoeudaAfficher=arbreAffichage.getRacine().getSuccesseurs();
				for (int profondeur=1;profondeur<profondeurAffichage;profondeur++) {
					ListeNoeudaAfficher=ListeNoeudaAfficher.get(i).getSuccesseurs();
				}
				for (int indice=0;indice<ListeNoeudaAfficher.size();indice++) {	
					JFrame f = new JFrame(ListeNoeudaAfficher.get(indice).getValeur().getName()+" : "+ListeNoeudaAfficher.get(indice).Heuristique(this.getCouleur()));
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setSize(this.getDamier().getTAILLE(),this.getDamier().getTAILLE()+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
					f.add(ListeNoeudaAfficher.get(indice).getValeur());
					f.setVisible(true);
				}
			}
		}
		if (afficherLesCoups) {
			ArrayList<NoeudDame> ListeNoeudaAfficher=arbreAffichage.getRacine().getSuccesseurs();
			for (int profondeur=1;profondeur<profondeurAffichage;profondeur++) {
				ListeNoeudaAfficher=ListeNoeudaAfficher.get(2).getSuccesseurs();
			}
			for (int indice=0;indice<ListeNoeudaAfficher.size();indice++) {	
				if (ListeNoeudaAfficher.get(indice).getListeDeCoups()!=null) {
					for (int j=0;j<ListeNoeudaAfficher.get(indice).getListeDeCoups().size();j++) {
						System.out.print("-"+ListeNoeudaAfficher.get(indice).getListeDeCoups().get(j).getPieceApresD().getC());
					}
				}
				System.out.println();
			}
		}

		
		
		if (profondeurArbre==0) {
			ordiBeteEtMechant(tourBlanc,this.getPeutMangerEnArriere(),this.getObligerLesSauts()); //joue le premier coup qu'il peut jouer
		}else {
			Arbre arbre = new Arbre(profondeurArbre,this.getCouleur(),this.getDamier(),this.getPeutMangerEnArriere(),this.getObligerLesSauts());
			ArrayList<Coup> meilleurCoup = algoMinMax(arbre,tourBlanc,this.getPeutMangerEnArriere(),this.getObligerLesSauts()) ;
			int indice=0;
			//System.out.print(meilleurCoup.get(indice).getPieceAvantD().getC());
			this.Ajoue(meilleurCoup.get(0).getPieceAvantD().getC().X(), meilleurCoup.get(0).getPieceAvantD().getC().Y(), tourBlanc, this.getPeutMangerEnArriere(), this.getObligerLesSauts());
			while (indice<meilleurCoup.size()) {
				this.Ajoue(meilleurCoup.get(indice).getPieceApresD().getC().X(), meilleurCoup.get(indice).getPieceApresD().getC().Y(), tourBlanc, this.getPeutMangerEnArriere(), this.getObligerLesSauts());
				//System.out.print(meilleurCoup.get(indice).getPieceApresD().getC());
				indice++;
			}
			System.out.println();
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
	
	
	private ArrayList<Coup> algoMinMax(Arbre arbre, boolean tourBlanc,boolean peutMangerEnArriere,boolean obligerLesSauts){ //besoin de la profondeur, noeud, créer la classe feuille 
		return minMax(arbre.getRacine(),arbre.getProfondeur(),peutMangerEnArriere,obligerLesSauts).getListeDeCoup();
	}
	
	private Resultat_minMax minMax(NoeudDame noeud,int profondeurArbre,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		Resultat_minMax res=new Resultat_minMax();
		if (noeud.getProfondeur()==0) {
			res.setListeDeCoup(noeud.getSuccesseurs(0).getListeDeCoups());
		}
		if (feuille(noeud,profondeurArbre)) {
			res.setValeur(noeud.Heuristique(this.getCouleur()));
		}
		else {
			if (noeud.getProfondeur()%2 ==0) {          //faudra remplacer si c'est l'IA qui est en train de jouer ou le joueur
				res.setValeur(-100000);
				for(int i=0;i<noeud.getSuccesseurs().size();i++){
					int tmp=minMax(noeud.getSuccesseurs(i),profondeurArbre,peutMangerEnArriere,obligerLesSauts).getValeur();
					
					if (tmp>res.getValeur()) {
						res.setValeur(tmp);
						//System.out.println(noeud.getSuccesseurs(i).getProfondeur());
						if (noeud.getSuccesseurs(i).getProfondeur()==1) {
							//System.out.println(noeud.getSuccesseurs(i).getListeDeCoups()==null);
							res.setListeDeCoup(noeud.getSuccesseurs(i).getListeDeCoups());
						}
					}
					//
				}
			}
			else {
				res.setValeur(100000);
				for(int j=0;j<noeud.getSuccesseurs().size();j++){
					res.setValeur(min(res.getValeur(), minMax(noeud.getSuccesseurs(j),profondeurArbre,peutMangerEnArriere,obligerLesSauts).getValeur()));
				}
			}
		}
		//System.out.println(res.getListeDeCoup()==null);
		return res;
	}
	
	
	
	/*private Resultat_minMax minMax2(NoeudDame noeud,int profondeurArbre,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		Resultat_minMax res = new Resultat_minMax() ;
		
		if (feuille(noeud,profondeurArbre)) {
			res.setValeur(noeud.Heuristique(this.getCouleur()));
		}
		else {
			if (noeud.getProfondeur()%2==0) { //Quand on est à une profondeur paire on cherche à avoir le max des fils
				res = maxsucceseurs(noeud,profondeurArbre,peutMangerEnArriere,obligerLesSauts);
			}
			else { // Quand on est à une profondeur impaire on cherche à obtenir le min des fils
				res = minsuccesseurs(noeud,profondeurArbre,peutMangerEnArriere,obligerLesSauts);
			}
		}
		return res;
	}
	
	
	private Resultat_minMax maxsucceseurs(NoeudDame noeud,int profondeurArbre,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		int i;
		Resultat_minMax res,tmp;
		//System.out.println(noeud.getProfondeur());
		res=minMax2(noeud.getSuccesseurs(0),profondeurArbre,peutMangerEnArriere,obligerLesSauts);
		res.setListeDeCoup(noeud.getSuccesseurs(0).getListeDeCoups());
		for (i=0;i<noeud.getSuccesseurs().size();i++) {
			tmp=minMax2(noeud.getSuccesseurs(i),profondeurArbre,peutMangerEnArriere,obligerLesSauts);
			if (tmp.getValeur()>res.getValeur()) {
				res.setValeur(tmp.getValeur());
				res.setListeDeCoup(noeud.getSuccesseurs(i).getListeDeCoups());
			}
		}
		return res;
	}
	
	private Resultat_minMax minsuccesseurs(NoeudDame noeud,int profondeurArbre,boolean peutMangerEnArriere,boolean obligerLesSauts) {
		int i;
		Resultat_minMax res,tmp;
		res=minMax2(noeud.getSuccesseurs(0),profondeurArbre,peutMangerEnArriere,obligerLesSauts);
		res.setListeDeCoup(noeud.getSuccesseurs(0).getListeDeCoups());
		for (i=1;i<noeud.getSuccesseurs().size();i++) {
			tmp=minMax2(noeud.getSuccesseurs(i),profondeurArbre,peutMangerEnArriere,obligerLesSauts);
			if (tmp.getValeur()<res.getValeur()) {
				res.setValeur(tmp.getValeur());
				res.setListeDeCoup(noeud.getSuccesseurs(i).getListeDeCoups());
			}
		}
		return res;
	}
	*/
	private boolean feuille(NoeudDame noeud,int profondeurarbre) {
		if (noeud.getProfondeur()==profondeurarbre) {
			return true;
		}else {
			return false;
		}
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
