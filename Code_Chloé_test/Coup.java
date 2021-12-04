

public class Coup{
	private Coordonnees anciennes;
	private Piece piece;


//Constructeur

	public Coup(int a,int b, Piece piece1){
		anciennes.setX(a);
		anciennes.setY(b);
		piece= piece1;
	}
	
	
	public Coordonnees getAncien(){
		return(anciennes);
		
		
	}
	
		
		
	
	public Piece getPiece(){
		return(piece);
		
		
	}
}
		
		
		
		
			
			
		
		
		
	


