import javax.swing.JFrame;

public class Lanceur extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private static final int TAILLE=800;	//taille de la fenêtre	

	public static void attendre(int ms) {
		try { Thread.sleep (ms); } 
        catch (InterruptedException e)  {  }
	}
	
	public static int doubles(int a) {
		return 2*a;
	}
	
	public static void main(String[] args) {
		
		int typeDePartie=0;
		Joueur j1 = null;
		Joueur j2 = null;
		int taille=0;
		boolean peutMangerEnArriere=false;
		boolean obligerLesSauts=false;
		TableauPiece PiecesBlanches = null;
		TableauPiece PiecesNoires = null;
		Damier damier = null;
		
//Menu
		boolean menu=false;		//savoir si on veut un menu ou non
		
		if (menu) {
			Menu m = new Menu();
			
			typeDePartie=m.typeDePartie();

			if (typeDePartie==1) {	//J1 vs J2
				j1 = new Humain(Couleur.Blanc,m.pseudoJoueur(1));
				j2 = new Humain(Couleur.Noir,m.pseudoJoueur(2));
			}
			if (typeDePartie==2) {	//J1 vs ordi
				j1 = new Joueur(Couleur.Blanc,m.pseudoJoueur(1));
				j2 = new Ordi(Couleur.Noir,"L'Ordinateur");
			}
			if (typeDePartie==3) {	//ordi vs ordi
				j1 = new Ordi(Couleur.Blanc,"L'Ordinateur 1");
				j2 = new Ordi(Couleur.Noir,"L'Ordinateur 2");
			}
			
			taille=m.definirTaille();
			peutMangerEnArriere=m.peutMangerEnArriere();
			obligerLesSauts=m.obligerLesSauts();
			
		}else {
			typeDePartie=1;
			
			if (typeDePartie==1) {	//J1 vs J2
				j1 = new Humain(Couleur.Blanc,"Pascal");
				j2 = new Humain(Couleur.Noir,"Obispo");
			}
			if (typeDePartie==2) {	//J1 vs ordi
				j1 = new Humain(Couleur.Blanc,"Pascal");
				j2 = new Ordi(Couleur.Noir,"L'Ordinateur");
			}
			if (typeDePartie==3) {	//ordi vs ordi
				j1 = new Ordi(Couleur.Blanc,"L'Ordinateur 1");
				j2 = new Ordi(Couleur.Noir,"L'Ordinateur 2");
			}	
			
			taille=8;		 //taille du coté du plateau 6*6 ou 8*8 ou 10*10 ou 12*12
			peutMangerEnArriere=false;
			obligerLesSauts=false;
			
		}
//fin menu
			
		damier = new Damier(TAILLE,taille); //10 par 10 pour l'original
		
		PiecesBlanches = new TableauPiece(damier,taille,Couleur.Blanc);
		PiecesNoires = new TableauPiece(damier,taille,Couleur.Noir);
		
		damier.setPiecesBlanches(PiecesBlanches);
		damier.setPiecesNoires(PiecesNoires);
		
		j1.setPieces(PiecesBlanches);
		j2.setPieces(PiecesNoires);
		j1.setDamier(damier);
		j2.setDamier(damier);
		
			
//Fenetre jeu de dames	
		JFrame f = new JFrame("Jeu de Dames");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(TAILLE,TAILLE+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
		
		f.add(damier);
		f.setVisible(true);
		
		Souris ecouteurDeSouris = new Souris(damier);
//fin fenêtre

		
//Début du jeu
		boolean partieTerminee=false;
		boolean tourBlanc=true; 	//vrai quand le tour est au joueur 1
		
		/*Ordi j3 = new Ordi(Couleur.Noir,"L'Ordinateur");
		j3.setDamier(damier);
		Coordonnees[] test = j3.ListeDesCoupsPossibles(PiecesBlanches.getPiece(1), peutMangerEnArriere, obligerLesSauts);
		System.out.println(test[0]);*/
		
		
		while (!partieTerminee) {
		//while ( (!(j1.aGagne(tourBlanc,peutMangerEnArriere))) && (!(j2.aGagne(!tourBlanc,peutMangerEnArriere))) ) {
			if (tourBlanc){
				while (!(damier.isTourFini())) {
					if (j1 instanceof Ordi) {
						attendre(250);
						((Ordi)j1).tourOrdi(tourBlanc,peutMangerEnArriere,obligerLesSauts);
					}
					else {
						f.addMouseListener(ecouteurDeSouris);
						while (ecouteurDeSouris.getAClique()==false) {
							//attend le clique de la souris
							System.out.print("");
						}
						ecouteurDeSouris.setAClique(false);
						System.out.print(" ");
						int x= ecouteurDeSouris.getCoordonneesClique().getX();
						int y= ecouteurDeSouris.getCoordonneesClique().getY();
						j1.Ajoue(x,y,tourBlanc,peutMangerEnArriere,obligerLesSauts);
												
						f.removeMouseListener(ecouteurDeSouris);
					}
				}
				
				damier.setTourFini(false);
				
				partieTerminee=j2.APerdu(peutMangerEnArriere);
				if (!partieTerminee) {
					damier.changementTour();
					tourBlanc=false;
				}
				
			}else {
				
				while (!(damier.isTourFini())) {
					if (j2 instanceof Ordi) {
						attendre(250);
						((Ordi)j2).tourOrdi(tourBlanc,peutMangerEnArriere,obligerLesSauts);
					}
					else {
						f.addMouseListener(ecouteurDeSouris);
						while (ecouteurDeSouris.getAClique()==false) {
							//attend le clique de la souris
							System.out.print("");
						}
						ecouteurDeSouris.setAClique(false);
						System.out.print(" ");
						int x= ecouteurDeSouris.getCoordonneesClique().getX();
						int y= ecouteurDeSouris.getCoordonneesClique().getY();
						j2.Ajoue(x,y,tourBlanc,peutMangerEnArriere,obligerLesSauts);
						f.removeMouseListener(ecouteurDeSouris);
					}
				}
				damier.setTourFini(false);
				
				partieTerminee=j1.APerdu(peutMangerEnArriere);
				if (!partieTerminee) {
					damier.changementTour();
					tourBlanc=true;
				}
				
			}
			
		}
		
		
	    if (tourBlanc) {
			System.out.println("\n"+j1.getPseudo()+" a remporté la partie");
		}
		else {
			System.out.println("\n"+j2.getPseudo()+" a remporté la partie");
		}
	    
		attendre(1000);
		
//attente du clique pour fermer
		f.addMouseListener(ecouteurDeSouris);
		while (ecouteurDeSouris.getAClique()==false) {
			System.out.print("");
		}
		f.removeMouseListener(ecouteurDeSouris);
//fin	
	    f.dispose();
		System.out.println("Fin.");
	}
	
	
}
