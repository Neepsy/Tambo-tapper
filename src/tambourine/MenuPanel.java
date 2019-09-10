package tambourine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements KeyListener{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage title;
	ImageIcon nep,nepStart,nepOps,nepHelp;
	String str;
	boolean go = false;
	JButton startButton, helpButton, optionsButton;
	JLabel picture, help;
	JFrame parentFrame;
	OptionPanel ops;
	
	int time, diff;

		public MenuPanel(int x, int y,JFrame f){
			
			//get a reference to the JFrame so we can add/remove stuff
			parentFrame = f;
			
			//load in the images
			try{
				nep = new ImageIcon(getClass().getResource("/menu/nep.png"));
				nepStart = new ImageIcon(getClass().getResource("/menu/nepstart.png"));
				nepOps = new ImageIcon(getClass().getResource("/menu/nepoptions.png"));
				nepHelp = new ImageIcon(getClass().getResource("/menu/nephelp.png"));
			}
			catch(Exception e){
				System.out.println(e);
			}
			//allow absolute positioning of components
			this.setLayout(null);
			
			startButton = new JButton("Play!");
			helpButton = new JButton("Help");
			optionsButton = new JButton("Options");
			
			//setup OptionPanel
			ops = new OptionPanel(this);
			ops.setBounds(70, 530, 200, 200);
			ops.setVisible(false);
			
			//this sets up the event listeners for each of the buttons
			//done in a seperate method to keep the code neat.
			setUpEventListeners();
			
			picture = new JLabel();
			help = new JLabel();
			help.setIcon(new ImageIcon(getClass().getResource("/menu/help.png")));
			picture.setIcon(nep);
			
			picture.setBounds(320, 500, 300, 225);
			help.setBounds(70,530,250,250);
			help.setVisible(false);
			
			startButton.setBounds(70,370,200,30);
			helpButton.setBounds(70,410,200,30);
			optionsButton.setBounds(70,450,200,30);
			
			this.add(startButton);
			this.add(helpButton);
			this.add(optionsButton);
			this.add(picture);
			this.add(help);
			this.add(ops);
			
			time = 60;
			diff = 4;
			str = "";
			this.setPreferredSize(new Dimension(x, y));
			repaint();
			try{
				title = ImageIO.read(getClass().getResource("/game/title.png"));
			}
			catch(Exception e){
				System.out.println(e);
			}
			this.grabFocus();
			
		}
		
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(title, 45, 10,this);
			g.drawString("Game by Eric Li and Daniel Wang", 400,775);
			g.drawString("Nep belongs to IF. Plz no sue", 400,790);
			
		}
		
		
		
		public void run(){
			//deprecated
			System.out.println("TEST V1.2");
			repaint();
			Scanner s = new Scanner(System.in);
			String str = "";
			while(!str.equals("go")){
				str = s.nextLine();
			}
			System.out.println("going");
			s.close();
			Main.done = true;
			
		
		}
		
		public int getTime(){
			System.out.println(time);
			return time;
		}
		
		//these two methods let option panel set the time/difficulty settings
		public void setTime(int x){
			time = x;
		}
		
		public void setDiff(int x){
			diff = x;
		}
		
		public void setUpEventListeners(){
			//using mouseListener instead of actionListener because there is mouse enter/exit code too
			
			optionsButton.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Clicked Options!");
					help.setVisible(false);
					ops.setVisible(!ops.isVisible());
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					picture.setIcon(nepOps);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					picture.setIcon(nep);
				}
				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
			
			helpButton.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					ops.setVisible(false);
					help.setVisible(!help.isVisible());
					
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					picture.setIcon(nepHelp);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					picture.setIcon(nep);
				}
				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
			
			startButton.addMouseListener(new MouseListener(){
				@Override
				//starts the game
				public void mouseClicked(MouseEvent e) {
					
					System.out.println("Clicked Start!");
					parentFrame.getContentPane().removeAll();
					TPanel game = new TPanel(650,800,time,diff,parentFrame);
					game.setBackground(new Color(220, 221, 242));
					parentFrame.add(game);
					parentFrame.pack();
					
					//make a new thread that runs the game so the program doesn't freeze
					Thread play  = new Thread(){
						public void run(){
							game.requestFocusInWindow();
							game.playGame();
						}
					};
					play.start();
					System.out.println("Started game successfully!");
							
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					picture.setIcon(nepStart);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					picture.setIcon(nep);
				}
				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
			});
			
			
		
		}
			
		
		
		
		
		
		

		@Override
		public void keyPressed(KeyEvent e) {
			
		}


		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("boop");
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD1){
				time = 60;
				go = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD2){
				time = 120;
				go = true;
			}
		}


		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
		
}

