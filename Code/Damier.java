import java.awt.*;
import javax.swing.*;

public class Damier extends JPanel{
	
	
	private static final long serialVersionUID = 1L;
	
	private Case[][] cases;  //tableau de cases
	
	private int TAILLE; //taille de la fenêtre
	private int taille; //taille=8 pour un plateau 8*8 par exemple
	public boolean tourBlanc;
	private boolean tourFini;	//savoir quand le tour du joueur est fini
	private boolean sautObligatoire;	//savoir si le joueur a un saut obligatoire en cours (dans le cas ou on a l'obligation des sauts)
	private boolean sautMultiple;	//savoir si le joueur est dans une situation de saut multiple ou non
	private TableauPiece PiecesBlanches;
	private TableauPiece PiecesNoires;
	private String name;

	public Damier(int TAILLE,int taille,String name) {
		this.TAILLE=TAILLE;
		this.taille=taille;
		this.name=name;
		this.tourBlanc=true;
		this.tourFini=false;
		this.sautObligatoire=false;
		this.sautMultiple=false;
		this.cases = new Case[taille][taille];
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				Coordonnees c = new Coordonnees(i,j);
				if ( (i+j)%2==0 ) {
					cases[i][j]= new Case(Couleur.Blanc,c);
				}
				else {
					if (j<=(taille-2)/2-1) {
						cases[i][j]= new Case(this,Couleur.Noir,c,Couleur.Noir);
					}
					else {
						if (j>=taille/2+1) {
							cases[i][j]= new Case(this,Couleur.Noir,c,Couleur.Blanc);
						}
						else {
							cases[i][j]= new Case(Couleur.Noir,c);
						}
					}
				}
			}
		}
	}
	
	
	public Damier clone() {
		Damier res = new Damier (TAILLE,taille,name);
		
		Case[][] cases = new Case[taille][taille];
		for (int i=0;i<taille;i++) {
			for (int j=0;j<taille;j++) {
				cases[i][j]=this.cases[i][j];
			}
		}
		res.setCases(cases);
		res.setTourBlanc(tourBlanc);
		res.setTourFini(tourFini);
		res.setSautObligatoire(sautObligatoire);
		res.setSautMultiple(sautMultiple);
		
		TableauPiece PiecesBlanches = new TableauPiece(this,taille,Couleur.Blanc);
		TableauPiece PiecesNoires = new TableauPiece(this,taille,Couleur.Blanc);
		for (int i=0;i<PiecesBlanches.getTailleTabPiece();i++) {
			PiecesBlanches.setPiece(this.getPiecesBlanches().getPiece(i), i);
			PiecesNoires.setPiece(this.getPiecesNoires().getPiece(i), i);
		}
		
		for (int i=0;i<taille;i++) {
			for (int j=0;j<taille;j++) {
				if (cases[i][j].getPiece()!=null) {
					res.getCases()[i][j].getPiece().setDamier(res);
				}
			}
		}
		
		for (int i=0;i<PiecesBlanches.getTailleTabPiece();i++) {
			PiecesBlanches.setDamier(res);
			PiecesNoires.setDamier(res);
		}
		
		return res;
	}
	
	//Constructeurs
	public boolean getTourBlanc() {
		return tourBlanc;
	}

	public void setTourBlanc(boolean tourBlanc) {
		this.tourBlanc = tourBlanc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TableauPiece getPiecesBlanches() {
		return PiecesBlanches;
	}

	public void setPiecesBlanches(TableauPiece piecesBlanches) {
		PiecesBlanches = piecesBlanches;
	}

	public TableauPiece getPiecesNoires() {
		return PiecesNoires;
	}

	public void setPiecesNoires(TableauPiece piecesNoires) {
		PiecesNoires = piecesNoires;
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
	
	public Case[][] getCases(){
		return cases;
	}
	
	public Case getCase(int i,int j) {
		return cases[i][j];
	}
	
	public Piece getPiece(int i,int j) {
		return cases[i][j].getPiece();
	}
	
	public void setCases(Case[][] cases) {
		this.cases = cases;
	}

	//fin constructeurs
	
	public void changementTour() {
		this.tourBlanc=!this.tourBlanc;
	}
	
	
	public void paintComponent(Graphics g) {
	//cases
		for (int i=0; i<taille; i++) {
			for (int j=0; j<taille; j++) {
				cases[i][j].dessinerCase(g,TAILLE,taille,tourBlanc);
				if (cases[i][j].getPiece()!=null) {
					cases[i][j].getPiece().dessinerPiece(g, i*TAILLE/taille,j*TAILLE/taille,TAILLE,taille);
				}
			}
		}
		/*for (int k=0;k<this.PiecesBlanches.getTailleTabPiece();k++) {
			int x=-1;
			int y=-1;
			if (PiecesBlanches.getPiece(k)!=null) {
				x= this.PiecesBlanches.getPiece(k).getCoordonnees().X();
				y= this.PiecesBlanches.getPiece(k).getCoordonnees().Y();
				this.PiecesBlanches.getPiece(k).dessinerPiece(g,x*TAILLE/taille,y*TAILLE/taille, TAILLE, taille);
			}
			if (PiecesNoires.getPiece(k)!=null) {
				x= this.PiecesNoires.getPiece(k).getCoordonnees().X();
				y= this.PiecesNoires.getPiece(k).getCoordonnees().Y();
				this.PiecesNoires.getPiece(k).dessinerPiece(g,x*TAILLE/taille,y*TAILLE/taille, TAILLE, taille);
			}
		}*/
	}
	
}
