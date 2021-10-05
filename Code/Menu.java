import java.util.Scanner;

public class Menu {

	public Menu() {
	}
		
	public int definirTaille() {
		int res=0;
		String c="";
		Scanner clavier = new Scanner(System.in);
		while ( (!c.equals("a")) && (!c.equals("b")) && (!c.equals("c")) ){
			System.out.println("Quel type de partie voulez-vous jouer ? (a/b/c)");
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
