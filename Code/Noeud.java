
public abstract class Noeud {

	
	public abstract int Heuristique(Piece PieceB, boolean peutMangerEnArriere,boolean obligerLesSauts) ; //Donne la valeur du noeud 

	public abstract int TotalHeuristique(boolean peutMangerEnArriere,boolean obligerLesSauts, TableauPiece PiecesBlanches, TableauPiece PiecesNoires);
}
