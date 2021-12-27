import java.util.Scanner;

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
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		//paramètres variables si pas de menu
		int typeDePartie=2;
		int difficulteOrdi1=1;
		int difficulteOrdi2=2;
		int taille=10;
		boolean peutMangerEnArriere=true;
		boolean obligerLesSauts=true;
		
		
		TableauPiece PiecesBlanches = null;
		TableauPiece PiecesNoires = null;
		Damier damier = null;
		Joueur j1 = null;
		Joueur j2 = null;
		
//Menu
		boolean menu=true;		//savoir si on veut un menu ou non
		
		if (menu) {
			Scanner clavier = new Scanner(System.in);
			
			Menu m = new Menu(clavier);
			
			typeDePartie=m.typeDePartie();
			
			
			
			if (typeDePartie==1) {	//J1 vs J2
				j1 = new Humain(Couleur.Blanc,m.pseudoJoueur(1),peutMangerEnArriere,obligerLesSauts);
				j2 = new Humain(Couleur.Noir,m.pseudoJoueur(2),peutMangerEnArriere,obligerLesSauts);
			}
			if (typeDePartie==2) {	//J1 vs ordi
				j1 = new Joueur(Couleur.Blanc,m.pseudoJoueur(1),peutMangerEnArriere,obligerLesSauts);
				j2 = new IA(Couleur.Noir,"L'Ordinateur",peutMangerEnArriere,obligerLesSauts);
			}
			if (typeDePartie==3) {	//ordi vs ordi
				j1 = new IA(Couleur.Blanc,"L'Ordinateur 1",peutMangerEnArriere,obligerLesSauts);
				j2 = new IA(Couleur.Noir,"L'Ordinateur 2",peutMangerEnArriere,obligerLesSauts);
			}
			
			if (typeDePartie==2) {
				difficulteOrdi1=m.choixNiveauOrdi();
				difficulteOrdi2=difficulteOrdi1;
			}
			
			if (typeDePartie==3) {
				System.out.println("Ordi1");
				difficulteOrdi1=m.choixNiveauOrdi();
				System.out.println("Ordi2");
				difficulteOrdi2=m.choixNiveauOrdi();
			}
			
			taille=m.definirTaille();
			peutMangerEnArriere=m.peutMangerEnArriere();
			obligerLesSauts=m.obligerLesSauts();
			
			j1.setPeutMangerEnArriere(peutMangerEnArriere);
			j1.setObligerLesSauts(obligerLesSauts);
			j2.setPeutMangerEnArriere(peutMangerEnArriere);
			j2.setObligerLesSauts(obligerLesSauts);
			
			
		}else {
			
			if (typeDePartie==1) {	//J1 vs J2
				j1 = new Humain(Couleur.Blanc,"Joueur1",peutMangerEnArriere,obligerLesSauts);
				j2 = new Humain(Couleur.Noir,"Joueur2",peutMangerEnArriere,obligerLesSauts);
			}
			if (typeDePartie==2) {	//J1 vs ordi
				j1 = new Humain(Couleur.Blanc,"Joueur1",peutMangerEnArriere,obligerLesSauts);
				j2 = new IA(Couleur.Noir,"L'Ordinateur",peutMangerEnArriere,obligerLesSauts);
			}
			if (typeDePartie==3) {	//ordi vs ordi
				j1 = new IA(Couleur.Blanc,"L'Ordinateur 1",peutMangerEnArriere,obligerLesSauts);
				j2 = new IA(Couleur.Noir,"L'Ordinateur 2",peutMangerEnArriere,obligerLesSauts);
			}	
			
		}
//fin menu
			
		damier = new Damier(TAILLE,taille,"damier"); //10 par 10 pour l'original
		
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
		while (!partieTerminee) {
			if (tourBlanc){
				while (!(damier.isTourFini())) {
					if (j1 instanceof IA) {
						attendre(250);
						((IA)j1).tourOrdiIA(difficulteOrdi1,tourBlanc);
					}
					else {
						f.addMouseListener(ecouteurDeSouris);
						while (ecouteurDeSouris.getAClique()==false) {
							//attend le clique de la souris
							System.out.print("");
						}
						ecouteurDeSouris.setAClique(false);
						System.out.print(" ");
						int x= ecouteurDeSouris.getCoordonneesClique().X();
						int y= ecouteurDeSouris.getCoordonneesClique().Y();
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
					if (j2 instanceof IA) {
						attendre(250);
						((IA)j2).tourOrdiIA(difficulteOrdi2,tourBlanc);
					}
					else {
						f.addMouseListener(ecouteurDeSouris);
						while (ecouteurDeSouris.getAClique()==false) {
							//attend le clique de la souris
							System.out.print("");
						}
						ecouteurDeSouris.setAClique(false);
						System.out.print(" ");
						int x= ecouteurDeSouris.getCoordonneesClique().X();
						int y= ecouteurDeSouris.getCoordonneesClique().Y();
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
	    attendre(200);
		System.out.println("Fin.");
	}
	
	
}
