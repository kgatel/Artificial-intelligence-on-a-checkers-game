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
		while ( (!c.equals("a")) && (!c.equals("b")) && (!c.equals("c")) && (!c.equals("d")) ){
			System.out.println("Quel taille de plateau voulez-vous jouer ? (a/b/c)");
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
	
	public TableauPiece tableauPiece(int taille,Couleur couleur) {
		Pion[] tableauPion = new Pion[(2+(taille-4)/2)*((taille-4)/2+1)];
		int compteurB=0,compteurN=0;
		
		for (int j=taille-1;j>=0;j--) {
			for (int i=0;i<taille;i++) {
				if ( ((i+j)%2)==1) {
					Coordonnees c = new Coordonnees(i,j);
					if ((j>=taille/2+1)&&(couleur==Couleur.Blanc)) {
						tableauPion[compteurB]=new Pion(Couleur.Blanc,c,damier,grille);
						compteurB++;
					}
					if ((j<=taille/2-2)&&(couleur==Couleur.Noir)) {
						tableauPion[compteurN]=new Pion(Couleur.Noir,c,damier,grille);
						compteurN++;
					}
				}
			}
		}
		TableauPiece res = new TableauPiece(this.damier,this.grille,tableauPion,(2+(taille-4)/2)*((taille-4)/2+1),couleur);
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
