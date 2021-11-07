import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Souris implements MouseListener {

	private Damier damier;
	private boolean aClique;
	private Coordonnees coordonneesClique;
	
	public Souris(Damier damier) {
		super();
		this.damier = damier;
		this.aClique=false;
		this.coordonneesClique=null;
	}
	
	public boolean getAClique() {
		return aClique;
	}

	public void setAClique(boolean aClique) {
		this.aClique = aClique;
	}

	public Coordonnees getCoordonneesClique() {
		return coordonneesClique;
	}

	public void setCoordonneesClique(Coordonnees coordonneesClique) {
		this.coordonneesClique = coordonneesClique;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
//		System.out.println("in");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("out");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
				
		int x = arg0.getX()*damier.getTaille()/damier.getTAILLE();
		int y = (arg0.getY()-39)*damier.getTaille()/damier.getTAILLE();
		
		this.setAClique(true);		
		
		Coordonnees c = new Coordonnees (x,y);
		this.setCoordonneesClique(c);
		
		//this.damier.Ajoue(x,y,false); //false pcq c'est un joueur qui joue pas un ordi
		
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
