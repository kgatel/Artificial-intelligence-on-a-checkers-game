import java.util.Scanner;

public class Menu {

	private Damier damier;
	private Case[][] grille;
	
	public Menu() {
		this.damier=null;
		this.grille=null;
	}
	
	
	public Damier getDamier() {
		return damier;
	}

	public void setDamier(Damier damier) {
		this.damier = damier;
	}

	public Case[][] getGrille() {
		return grille;
	}

	public void setGrille(Case[][] grille) {
		this.grille = grille;
	}


	public int typeDePartie() {
		int res;
		String c="";
		Scanner clavier = new Scanner(System.in);
		while ( (!c.equals("1")) && (!c.equals("2")) && (!c.equals("3")) ){
			System.out.println("Choisissez votre type de partie : (1/2/3)");
			System.out.println("1- J1 vs J2");
			System.out.println("2- J1 vs IA");
			System.out.println("3- IA vs IA");
			c = clavier.nextLine();
			System.out.println();
		}
		res=Integer.valueOf(c);
		return res;
	}
	
	public int definirTaille() {
		int res=0;
		String c="";
		Scanner clavier = new Scanner(System.in);
		while ( (!c.equals("a")) && (!c.equals("b")) && (!c.equals("c")) ){
			System.out.println("Quel taille de plateau voulez-vous jouer ? (a/b/c)");
			System.out.println("a- Rapide (8*8)");
			System.out.println("b- Classique (10*10)");
			System.out.println("c- Longue (12*12)");
			c = clavier.nextLine();
			System.out.println();
		}
		if (c.equals("a")) {
			res=8;
		}
		if (c.equals("b")) {
			res=10;
		}
		if (c.equals("c")) {
			res=12;
		}
		return res;
	}
	
	public Pion[] Pieces(int taille,Couleur couleur) {
		Pion[] rep = new Pion[taille+taille/2];
		int compteurB=0,compteurN=0;
		
		for (int j=taille-1;j>=0;j--) {
			for (int i=0;i<taille;i++) {
				if ( ((i+j)%2)==1) {
					Coordonnees c = new Coordonnees(i,j);
					if ((j>=taille/2+1)&&(couleur==Couleur.Blanc)) {
						rep[compteurB]=new Pion(Couleur.Blanc,c,damier,grille);
						compteurB++;
					}
					if ((j<=taille/2-2)&&(couleur==Couleur.Noir)) {
						rep[compteurN]=new Pion(Couleur.Noir,c,damier,grille);
						compteurN++;
					}
				}
			}
		}
		
		return rep;
	}
	
	public boolean obligerLesSauts() {
		boolean b=false;
		String c="";
		Scanner clavier = new Scanner(System.in);
		while ( (!c.equals("o")) && (!c.equals("n")) ){
			System.out.println("Voulez-vous rendre les sauts obligatoires ? (o/n)");
			c = clavier.nextLine();
			System.out.println();
		}
		if (c.equals("o")) {
			b=true;
		}
		
		return b;
	}
	
	public boolean peutMangerEnArriere() {
		boolean b=false;
		String c="";
		Scanner clavier = new Scanner(System.in);
		while ( (!c.equals("o")) && (!c.equals("n")) ){
			System.out.println("Voulez-vous rautoriser les sauts en arrière ? (o/n)");
			c = clavier.nextLine();
			System.out.println();
		}
		if (c.equals("o")) {
			b=true;
		}
		
		return b;
	}
	
	public String pseudoJoueur1() {
		String c="";
		Scanner clavier = new Scanner(System.in);
		while (c.equals("")){
			System.out.print("Pseudo joueur 1 (Blanc) : ");
			c = clavier.nextLine();
		}
		return c;
	}
	
	public String pseudoJoueur2() {
		String c="";
		Scanner clavier = new Scanner(System.in);
		while (c.equals("")){
			System.out.print("Pseudo joueur 2 (Noir) : ");
			c = clavier.nextLine();
		}
		System.out.println("");
		return c;
	}
	
}
