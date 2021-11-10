import java.awt.*;
import javax.swing.*;

public class Damier extends JPanel{
	
	private int TAILLE; //taille de la fenêtre
	private int taille; //taille=8 pour un plateau 8*8 par exemple
	private Case[][] grille;  //tableau de cases
	private boolean tourBlanc;  //savoir à qui est le tour
	private boolean tourFini;	//savoir quand le tour du joueur est fini
	private boolean sautObligatoire;	//savoir si le joueur a un saut obligatoire en cours
	private boolean sautMultiple;	//savoir si le joueur est dans une situation de saut multiple ou non
	private boolean obligerLesSauts;
	private boolean peutMangerEnArriere;
	private TableauPiece PiecesBlanches;
	private TableauPiece PiecesNoires;


	public Damier(int TAILLE,int taille,boolean obligerLesSauts,boolean peutMangerEnArriere,TableauPiece PiecesBlanches,TableauPiece PiecesNoires) {
		this.TAILLE=TAILLE;
		this.taille=taille;
		this.obligerLesSauts=obligerLesSauts;
		this.peutMangerEnArriere=peutMangerEnArriere;
		this.tourBlanc=true;
		this.tourFini=false;
		this.sautObligatoire=false;
		this.sautMultiple=false;
		this.PiecesBlanches=PiecesBlanches;
		this.PiecesNoires=PiecesNoires;
		this.grille = new Case[taille][taille];
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				Coordonnees c = new Coordonnees(i,j);
				if ( (i+j)%2==0 ) {
					grille[i][j]= new Case(Couleur.Blanc,c,this,grille);
				}
				else {
					if (j<=(taille-2)/2-1) {
						grille[i][j]= new Case(Couleur.Noir,c,Couleur.Noir,this,grille);
					}
					else {
						if (j>=taille/2+1) {
							grille[i][j]= new Case(Couleur.Noir,c,Couleur.Blanc,this,grille);
						}
						else {
							grille[i][j]= new Case(Couleur.Noir,c,this,grille);
						}
					}
				}
			}
		}
	}
	
		
	public boolean getPeutMangerEnArriere() {
		return peutMangerEnArriere;
	}

	public void setPeutMangerEnArriere(boolean peutMangerEnArriere) {
		this.peutMangerEnArriere = peutMangerEnArriere;
	}
	
	public boolean isTourFini() {
		return tourFini;
	}

	public void setTourFini(boolean tourFini) {
		this.tourFini = tourFini;
	}

	public boolean getSautMultiple() {
		return sautMultiple;
	}

	public void setSautMultiple(boolean sautMultiple) {
		this.sautMultiple = sautMultiple;
	}

	public boolean getObligerLesSauts() {
		return obligerLesSauts;
	}

	public void setObligerLesSauts(boolean obligerLesSauts) {
		this.obligerLesSauts = obligerLesSauts;
	}

	public boolean getSautObligatoire() {
		return sautObligatoire;
	}

	public void setSautObligatoire(boolean sautObligatoire) {
		this.sautObligatoire = sautObligatoire;
	}

	public int getTaille() {
		return taille;
	}
	
	public int getTAILLE() {
		return TAILLE;
	}
	
	public Case[][] getGrille(){
		return grille;
	}
	
	public boolean getTourBlanc() {
		return tourBlanc;
	}

	public void setTourBlanc(boolean tourBlanc) {
		this.tourBlanc = tourBlanc;
	}
		

		
		
	
	
	public void changementTour() {
		this.tourBlanc=!tourBlanc;
	}
	
	
	public void paintComponent(Graphics g) {
	//cases
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				grille[i][j].dessinerCase(g,i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				if (grille[i][j].getPiece()!=null) {
					grille[i][j].getPiece().dessinerPiece(g, i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				}
			}
		}
		/*for (int k=0;k<this.PiecesBlanches.getTaille();k++) {
			int x= this.PiecesBlanches.getPiece(k).getCoordonnees().getX();
			int y= this.PiecesBlanches.getPiece(k).getCoordonnees().getY();
			this.PiecesBlanches.getPiece(k).dessinerPiece(g,x*TAILLE/taille,y*TAILLE/taille, TAILLE, taille);
			x= this.PiecesNoires.getPiece(k).getCoordonnees().getX();
			y= this.PiecesNoires.getPiece(k).getCoordonnees().getY();
			this.PiecesNoires.getPiece(k).dessinerPiece(g,x*TAILLE/taille,y*TAILLE/taille, TAILLE, taille);
		}*/
	}
	
}
