import java.awt.*;

public class Case {

	private Coordonnees c;
	private Couleur couleur;
	private Piece piece;
	private boolean saut;
	private boolean clique;		//case cliquée
	private boolean possibleClique;	
	
	public Case(Couleur couleur, Coordonnees c) {
		this.c=c;
		this.couleur=couleur;
		piece = null;
		saut = false;
		clique = false;
		possibleClique=false;
	}
	
	public Case(Damier damier,Couleur couleurCase,Coordonnees c,Couleur couleurPiece) {
		super();
		this.c=c;
		this.couleur=couleurCase;
		piece = new Pion(couleurPiece,c,damier);
		saut = false;
		clique = false;
		possibleClique = false;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public boolean getClique() {
		return clique;
	}
	
	public boolean getPossibleClique() {
		return possibleClique;
	}
	
	public boolean getSaut(){
		return saut;
	}
	
	public void setCouleur(Couleur couleur) {
		this.couleur=couleur;
	}
	
	public void setPossibleClique(boolean b) {
		this.possibleClique=b;
	}
	
	public void setPiece(Piece piece) {
		this.piece=piece;
	}
	
	public void setClique(boolean b) {
		this.clique=b;
	}
	
	public void setSaut(boolean b){
		this.saut=b;
	}
	
	public void dessinerCase(Graphics g, int TAILLE,int taille, boolean tourBlanc) {
		int x=c.getX()*TAILLE/taille;
		int y=c.getY()*TAILLE/taille;
		switch(couleur) {
		case Blanc : 
			g.setColor(Color.WHITE);
			break;
		case Noir :
			g.setColor(Color.GRAY);
			break;
		}
		if (clique) {
			if (piece!=null) {
				if ( ((tourBlanc)&&(piece.getCouleur()==Couleur.Blanc)) || ((!tourBlanc)&&(piece.getCouleur()==Couleur.Noir)) ) {
					g.setColor(Color.green);
				}
				else {
					g.setColor(Color.red);
				}
			}
		}
		if (saut) {
			g.setColor(new Color(255,150,150));
		}
		if (possibleClique) {
			g.setColor(new Color(150,150,255));
		}
		g.fillRect(x,y,TAILLE/taille,TAILLE/taille);
	}
	
	public void click() {
		clique=!clique;
	}
	
}
