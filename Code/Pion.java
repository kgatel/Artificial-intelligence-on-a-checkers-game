import java.awt.Color;
import java.awt.Graphics;

public class Pion extends Piece{

	//attributs
	Coordonnees coordonnees;
	
	
	public Pion(Couleur couleur) {
		super(couleur);
	}
	
	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees c) {
		this.coordonnees = c;
	}


}
