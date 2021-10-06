import javax.swing.JFrame;

public class Lanceur extends JFrame{

	private static final int TAILLE=800;	//taille de la fenêtre
	
	public static void main(String[] args) {
		
		//menu
		Menu m = new Menu();
		

		boolean obligerLesSauts=false;;
		int taille=8;  //taille du coté du plateau 8*8 ou 10*10 ou 12*12
		boolean sauterNEstPasJoue=false;	//si je me chauffe j'élimine le pion s'il a possibilité de manger et qu'il ne le fait pas
		
//		taille=m.definirTaille();
//		obligerLesSauts = m.obligerLesSauts();   //jeu avec sauts obligatoires si vrai
		String pseudo1,pseudo2;
//		pseudo1=m.pseudoJoueur1();
//		pseudo2=m.pseudoJoueur2();
		pseudo1="Pascal";
		pseudo2="Obispo";
		Joueur j1 = new Joueur(Couleur.Blanc,pseudo1);
		Joueur j2 = new Joueur(Couleur.Noir,pseudo2);
		
		JFrame f = new JFrame("Jeu de Dames");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(TAILLE,TAILLE+37);  //le +37 est nécessaire à l'affichage de la dernière ligne
		Damier damier = new Damier(f,TAILLE,taille,obligerLesSauts,sauterNEstPasJoue,j1,j2); //10 par 10 pour l'original
		f.add(damier);
		f.setVisible(true);
		f.addMouseListener(new Souris(damier));
		//tourOrdi si ordi commence
	}
	
}
