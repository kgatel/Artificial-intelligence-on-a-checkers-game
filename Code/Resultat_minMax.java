import java.util.ArrayList;

public class Resultat_minMax {

	//attributs
	ArrayList<Coup> listeDeCoup;
	int valeur;
	
	//Constructeurs
	public Resultat_minMax() {
		this.listeDeCoup=null;
		this.valeur=0;
	}
	
	public Resultat_minMax(ArrayList<Coup> listeDeCoup,int valeur) {
		this.listeDeCoup=listeDeCoup;
		this.valeur=valeur;
	}
	
	//Accesseurs
	public ArrayList<Coup> getListeDeCoup() {
		return listeDeCoup;
	}

	public void setListeDeCoup(ArrayList<Coup> listeDeCoup) {
		this.listeDeCoup = listeDeCoup;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	
}
