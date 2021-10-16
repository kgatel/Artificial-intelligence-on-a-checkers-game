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
		
		String pseudo1,pseudo2;
		pseudo1="Pascal";
		pseudo2="Obispo";
//		pseudo1=m.pseudoJoueur1();
//		pseudo2=m.pseudoJoueur2();
		boolean obligerLesSauts=false; 
		//obligerLesSauts=m.obligerLesSauts();
		boolean peutMangerEnArriere=false;
		//peutMangerEnArriere=m.peutMangerEnArriere();
		int taille=8; 						 //taille du coté du plateau 8*8 ou 10*10 ou 12*12
		//taille=m.definirTaille();
		
		//fin menu
		
		Joueur j1 = new Joueur(Couleur.Blanc,pseudo1);
		Joueur j2 = new Joueur(Couleur.Noir,pseudo2);
		
		int tour=1; 					 //1 si c'est le tour du joueur 1, 2 sinon
		boolean partieTerminee=false;
			
		//Fenetre jeu de dames	
		JFrame f = new JFrame("Jeu de Dames");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(TAILLE,TAILLE+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
		Damier damier = new Damier(TAILLE,taille,obligerLesSauts,peutMangerEnArriere); //10 par 10 pour l'original
		f.add(damier);
		f.setVisible(true);
		f.addMouseListener(new Souris(damier));
		attendre(1000);
		while (!damier.isGameOver()) {
			if (tour==1){
				if (damier.isTourFini()) {
					tour=2;
					damier.setTourFini(false);
					attendre(250);
				}
			}else {
				//damier.tourOrdi(j2);
				
				if (damier.isTourFini()) {
					tour=1;
					damier.setTourFini(false);
				}
			}
			System.out.print("");
			//System.out.println(tour);
		}
		
	    if (tour==1) {
			System.out.println(j1.getPseudo()+" a remporté la partie");
		}
		else {
			System.out.println(j2.getPseudo()+" a remporté la partie");
		}
	    
		attendre(100000);
	    
	    f.dispose();
		
	}
	
	
}
