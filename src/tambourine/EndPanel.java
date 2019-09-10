package tambourine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

public class EndPanel extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -7911693813051197282L;
	BufferedImage Gold, Silver, Bronze;
	JButton retButton;
	JFrame parent;
	double acc;
	int score;
	int overall;
	
	public EndPanel(int x, int y, JFrame frame, double acc, int score)
	{
		this.setPreferredSize(new Dimension(x, y));
		//load in the images
		try{
			Gold = ImageIO.read(getClass().getResource("/menu/Gold.png"));
			Silver = ImageIO.read(getClass().getResource("/menu/Silver.png"));
			Bronze = ImageIO.read(getClass().getResource("/menu/Bronze.png"));
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		parent = frame;
		retButton = new JButton("Return to Menu");
		
		MouseListeners();
		
		retButton.setBounds(70,330,200,30);
		
		this.add(retButton);
		
		//calculate score based on accuracy
		this.score = score;
		this.acc = acc;
		overall = (int)(score*(acc/100));

	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		//draw the medals and text
		Color c = new Color(65,65,65);
		g.setColor(c);
		g.fillRect(0, 0, 650, 800);
		//display a medal based on what score the player got
		if(overall>12000)
		{
			g.drawImage(Gold, 200, 40, this);
		}
		else if(overall>10000)
		{
			g.drawImage(Silver, 200, 40, this);
		}
		else if(overall>8000)
		{
			g.drawImage(Bronze, 200, 40, this);
		}
		else{
			g.drawString("Get. Off. The. Field.", 200, 40);
		}
		//paint the rest of the screen
		System.out.println("PaintComponent");
		g.setColor(Color.WHITE);
		Font f = new Font("Consolas", Font.BOLD, 24);
		g.setFont(f);
		g.drawString("Score: " + score, 230, 530);
		g.drawString("Accuracy: " + acc, 230, 560);
		g.drawString("Final Score: " + overall, 230, 590);
	}
	
	public void click(int x, int y)
	{
		System.out.println("Click: " + x + " " + y);
	}

	
	public void MouseListeners()
	{
		//MouseListener for the return to main screen button
		retButton.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				parent.getContentPane().removeAll();
				MenuPanel menu = new MenuPanel(650,800,parent);
				menu.setBackground(new Color(220, 221, 242));
				parent.add(menu);
				parent.pack();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}