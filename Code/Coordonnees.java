
public class Coordonnees {

	private int x,y;
	
	public Coordonnees() {
		x=-1;
		y=-1;
	}
	
	public Coordonnees(int x, int y) {
		this.x=x;
		this.y=y;
	}

	public int X() {
		return x;
	}
		
	public void setX(int x) {
		this.x = x;
	}

	public int Y() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "("+this.x+","+this.y+")";
	}
	
	
}
