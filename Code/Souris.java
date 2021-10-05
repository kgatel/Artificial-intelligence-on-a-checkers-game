import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Souris implements MouseListener {

	private Damier damier;
	private JFrame frame;
	
	public Souris(Damier damier) {
		super();
		this.damier = damier;
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
		
		this.damier.Ajoue(x,y);
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

}
