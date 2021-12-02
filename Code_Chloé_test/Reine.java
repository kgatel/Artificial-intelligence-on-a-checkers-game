import java.awt.Color;
import java.awt.Graphics;

public class Reine extends Piece{

	public Reine(Couleur couleur,Coordonnees c, Damier damier) {
		super(couleur,c,damier);
	}
	
	public String toString() {
		return "reine";
	}
}