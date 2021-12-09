import java.util.Scanner;

public class Menu {
	
	public Menu() {
	}

	public int typeDePartie() {
		int res=0;
		String c="";
		Scanner clavier = new Scanner(System.in);
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
		Scanner clavier = new Scanner(System.in);
		while ( (!c.equals("0")) && (!c.equals("2")) && (!c.equals("4")) && (!c.equals("6")) && (!c.equals("8")) && (!c.equals("10"))){
			System.out.println("Choisissez le niveau de l'ordi : (/10)");
			System.out.println("0- Ordi simple");
			System.out.println("2- facile");
			System.out.println("4- intermédiaire");
			System.out.println("6- difficile");
			System.out.println("8- très difficile");
			System.out.println("10- impossible");
			c = clavier.nextLine();
			System.out.println();
		}
		return Integer.parseInt(c)/2;
	}
	
	public int definirTaille() {
		int res=0;
		String c="";
		Scanner clavier = new Scanner(System.in);
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
		Scanner clavier = new Scanner(System.in);
		while (c.equals("")){
			System.out.print("Pseudo joueur "+numero+" (Blanc) : ");
			c = clavier.nextLine();
		}
		System.out.println();
		return c;
	}
	
}
