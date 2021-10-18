import javax.swing.JFrame;

public class Lanceur extends JFrame{

	private static final int TAILLE=800;	//taille de la fenêtre	

	public static void attendre(int ms) {
		try { Thread.sleep (ms); } 
        catch (InterruptedException e)  {  }
	}
	
	public static int doubles(int a) {
		return 2*a;
	}
	
	public static void main(String[] args) {
		
//menu
		Menu m = new Menu();
		
		int typeDePartie=2;
		//int typeDePartie=m.typeDePartie();
		
		Joueur j1 = null;
		Joueur j2 = null;
		
		if (typeDePartie==1) {	//J1 vs J2
			j1 = new Humain(Couleur.Blanc,"Pascal");
			j2 = new Humain(Couleur.Noir,"Obispo");
			//j1 = new Humain(Couleur.Blanc,m.pseudoJoueur1());
			//j2 = new Humain(Couleur.Noir,m.pseudoJoueur2());
		}
		
		if (typeDePartie==2) {	//J1 vs ordi
			j1 = new Humain(Couleur.Blanc,"Pascal");
			//j1 = new Joueur(Couleur.Blanc,m.pseudoJoueur1());
			j2 = new Ordi(Couleur.Noir,"L'Ordinateur");
		}
		
		if (typeDePartie==3) {	//ordi vs ordi
			j1 = new Ordi(Couleur.Blanc,"L'Ordinateur 1");
			j2 = new Ordi(Couleur.Noir,"L'Ordinateur 2");
		}	
		
		int taille=4;						 //taille du coté du plateau 8*8 ou 10*10 ou 12*12
		//taille=m.definirTaille();
		
		boolean peutMangerEnArriere=false;
		//peutMangerEnArriere=m.peutMangerEnArriere();
		
		boolean obligerLesSauts=false; 
		//obligerLesSauts=m.obligerLesSauts();
	
		Piece [] PieceBlanche = m.Pieces(taille,Couleur.Blanc);
		Piece [] PieceNoire = m.Pieces(taille, Couleur.Noir);
		
//fin menu
						
		boolean tourBlanc=true; 					 //vrai quand le tour est au joueur 1
		boolean partieTerminee=false;
			
//Fenetre jeu de dames	
		JFrame f = new JFrame("Jeu de Dames");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(TAILLE,TAILLE+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
		Damier damier = new Damier(TAILLE,taille,obligerLesSauts,peutMangerEnArriere); //10 par 10 pour l'original
		f.add(damier);
		f.setVisible(true);
		
		Souris ecouteurDeSouris = new Souris(damier);
		//attendre(1000);
//fin fenêtre

		
//Début du jeu
		j1.setDamier(damier);
		j2.setDamier(damier);
		
		while (!damier.isGameOver()) {
			if (tourBlanc){
				while (!(damier.isTourFini())) {
					if (j1 instanceof Ordi) {
						attendre(250);
						((Ordi)j1).tourOrdi();
					}
					else {
						f.addMouseListener(ecouteurDeSouris);
						while (damier.getSourisAClique()==false) {
							//attend le clique de la souris
							System.out.print("");
						}
						damier.setSourisAClique(false);
						System.out.print(" ");
						int x= damier.getCoordoonnesSourisAClique().getX();
						int y= damier.getCoordoonnesSourisAClique().getY();
						j1.Ajoue(x,y,false);
						f.removeMouseListener(ecouteurDeSouris);
					}
				}	
				
				tourBlanc=false;
				damier.setTourFini(false);

				
			}else {
				
				while (!(damier.isTourFini())) {
					if (j2 instanceof Ordi) {
						attendre(250);
						((Ordi)j2).tourOrdi();
					}
					else {
						f.addMouseListener(ecouteurDeSouris);
						while (damier.getSourisAClique()==false) {
							//attend le clique de la souris
							System.out.print("");
						}
						damier.setSourisAClique(false);
						System.out.print(" ");
						int x= damier.getCoordoonnesSourisAClique().getX();
						int y= damier.getCoordoonnesSourisAClique().getY();
						j2.Ajoue(x,y,false);
						f.removeMouseListener(ecouteurDeSouris);
					}
				}	
				
				tourBlanc=true;
				damier.setTourFini(false);

			}
			
		}
		
		
	    if (tourBlanc) {
			System.out.println("\n"+j2.getPseudo()+" a remporté la partie");
		}
		else {
			System.out.println("\n"+j1.getPseudo()+" a remporté la partie");
		}
	    
		attendre(1000);
		
//attente du clique pour fermer
		f.addMouseListener(ecouteurDeSouris);
		while (damier.getSourisAClique()==false) {
			System.out.print("");
		}
		f.removeMouseListener(ecouteurDeSouris);
//fin	
	    f.dispose();
		System.out.println("Fin.");
	}
	
	
}
