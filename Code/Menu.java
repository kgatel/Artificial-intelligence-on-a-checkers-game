import java.util.Scanner;

public class Menu {
	
	private Scanner clavier;
	
	public Menu(Scanner clavier) {
		this.clavier=clavier;
	}

	public int typeDePartie() {
		int res=0;
		String c="";
		while ( (!c.equals("a")) && (!c.equals("b")) && (!c.equals("c")) ){
			System.out.println("Choisissez votre type de partie : (a/b/c)");
			System.out.println("a- J1 vs J2");
			System.out.println("b- J1 vs IA");
			System.out.println("c- IA vs IA");
			c = clavier.nextLine();
			System.out.println();
		}
		if (c.equals("a")) {
			res=1;
		}
		if (c.equals("b")) {
			res=2;
		}
		if (c.equals("c")) {
			res=3;
		}
		return res;
	}
	
	public int choixNiveauOrdi() {
		String c="";
		while ( /*(!c.equals("0")) && */(!c.equals("1")) && (!c.equals("2")) && (!c.equals("3")) && (!c.equals("4")) && (!c.equals("5"))){
			System.out.println("Choisissez le niveau de l'ordi : (/5)");
			//System.out.println("0- Ordi simple");
			System.out.println("1- facile");
			System.out.println("2- intermédiaire");
			System.out.println("3- difficile");
			System.out.println("4- très difficile");
			System.out.println("5- impossible");
			c = clavier.nextLine();
			System.out.println();
		}
		return Integer.parseInt(c);
	}
	
	public int definirTaille() {
		int res=0;
		String c="";
		System.out.println();
		while ( (!c.equals("a")) && (!c.equals("b")) && (!c.equals("c")) && (!c.equals("d")) ){
			System.out.println("Quel taille de plateau voulez-vous jouer ? (a/b/c/d)");
			System.out.println("a - Expresso (6*6));");
			System.out.println("b- Rapide (8*8)");
			System.out.println("c- Classique (10*10)");
			System.out.println("d- Longue (12*12)");
			c = clavier.nextLine();
			System.out.println();
		}
		if (c.equals("a")) {
			res=6;
		}
		if (c.equals("b")) {
			res=8;
		}
		if (c.equals("c")) {
			res=10;
		}
		if (c.equals("d")) {
			res=12;
		}
		return res;
	}
	
	public boolean obligerLesSauts() {
		boolean b=false;
		String c="";
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
		while ( (!c.equals("o")) && (!c.equals("n")) ){
			System.out.println("Voulez-vous autoriser les sauts en arrière ? (o/n)");
			c = clavier.nextLine();
			System.out.println();
		}
		if (c.equals("o")) {
			b=true;
		}
		return b;
	}
	
	public String pseudoJoueur(int numero) {
		String c="";
		while (c.equals("")){
			System.out.print("Pseudo joueur "+numero+" (Blanc) : ");
			c = clavier.nextLine();
		}
		System.out.println();
		return c;
	}
	
}
