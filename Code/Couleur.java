
public enum Couleur {
	
	Blanc,
	Noir,
	;
	
	public Couleur inverser(Couleur couleur) {
		if (couleur==Couleur.Blanc) {
			return Couleur.Noir;
		}
		else {
			return Couleur.Blanc;
		}
	}
}