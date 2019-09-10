package tambourine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class TPanel extends JPanel{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final double FPS = 60.0;
	int x = 30;
	int framesSinceLast = 0;
	int presses, hits;
	int diff = 4;
	double acc;
	int hit, hit1, hit2, hit3;
	NoteManager notes;
	JFrame parentFrame;
	BufferedImage tamb1, tamb2, timer;
	int time, frames;
	DecimalFormat df = new DecimalFormat("##.#");
	
	

		public TPanel(int x, int y, int t, int d, JFrame par){
			//Declare **ALL** the vars
			
			parentFrame = par;
			setUpKeybinds();
			diff = d;
			acc = 0;
			presses = 0;
			hits = 0;
			hit = 0;
			hit1 = 0;
			hit2 = 0;
			hit3 = 0;
			frames = 0;
			time = t;
			this.setPreferredSize(new Dimension(x, y));
			notes = new NoteManager(diff);
			notes.addNote();
			//load in the images
			try{
			tamb1 = ImageIO.read(getClass().getResource("/game/tambodefault.png"));
			tamb2 = ImageIO.read(getClass().getResource("/game/tambohit.png"));
			timer = ImageIO.read(getClass().getResource("/game/time.png"));
			}
			catch(Exception e){
				System.out.println("TP Error!");
				System.out.println(e.getStackTrace());
			}
			
			
		}
		
		public void setOptions(int t, int d){
			//set the difficulty and time settings
			time = t;
			diff = d;
		}
		
		private void setUpKeybinds(){
			//add keybinds to the panel. the actions are defined towards the bottom of the file.
			this.getInputMap().put(KeyStroke.getKeyStroke("released 1"), "press1");
			this.getInputMap().put(KeyStroke.getKeyStroke("released 2"), "press2");
			this.getInputMap().put(KeyStroke.getKeyStroke("released 3"), "press3");
			
			this.getActionMap().put("press1", pressAction1);
			this.getActionMap().put("press2", pressAction2);
			this.getActionMap().put("press3", pressAction3);
		}
		
		
		public void paintComponent(Graphics g){
			//draw all gui the stuff
			g.setColor(Color.black);
			super.paintComponent(g);
			g.setFont(new Font("Consolas", Font.PLAIN, 14));
			g.drawString(notes.msg, 570, 590);
			
			g.drawImage(timer, 10, 270,100,100, this);
			if(notes.comboImg != null){
			g.drawImage(notes.comboImg, 10, 400,220,120,this);
			}
			
			//make the tambourine move when a key is pressed
			if(hit != 0)
				g.drawImage(tamb2, 10, 600,150,150,this);
			else	
				g.drawImage(tamb1, 10, 600,150,150,this);
			
			
			
			
			g.setFont(new Font("Consolas", Font.PLAIN, 20));
			g.drawString(Integer.toString(time), 120, 320);
			
			g.drawString("Score: " + notes.score, 40, 130);
			g.drawString("Combo: " + notes.combo, 40, 150);
			g.drawString("Acc: " + acc + "%", 40, 170);
		
			g.setColor(new Color(54, 64, 71));
			g.fillRect(300, 40, 256, 640);
			
			g.setColor(Color.white);
			g.drawLine(290, 590,566,590);
			
			//these draw the colored "lasers" when you hit the keys
			//each "hit" variable is a timer for that specific laser
			if(hit1 != 0){
				g.setColor(new Color(119, 167, 255));
				g.fillRect(348, 40, 10, 640);
			}
			if(hit2 != 0){
				g.setColor(new Color(109, 255, 107));
				g.fillRect(428, 40, 10, 640);
			}
			if(hit3 != 0){
				g.setColor(new Color(255, 84, 84));
				g.fillRect(508, 40, 10, 640);
			}
			
			//draw all notes
			Iterator<Note> it = notes.notes.iterator();
			while(it.hasNext()){
				Note n = it.next();
				g.drawImage(n.getImg(), 240+(80*n.getType()), n.getTime()+20, this);
			}
			
		}
		
		public void playGame() {
			//initiate the game
			this.repaint();
			System.out.println("Playing");
			this.run();
		}
		
		
		public void run(){
			
			//setup timing variables
			System.out.println("TAMB V2.0");
			long lastTime = System.nanoTime();
			final double UPDATE_TIME = 1000000000 / FPS;
			double deltaTime = 0.0;
			frames = 0;

			
			
			boolean running = true;
		//main game loop
			while(running){
				long currentTime = System.nanoTime();
				deltaTime += (currentTime - lastTime)/UPDATE_TIME;
				lastTime = currentTime;
				while(deltaTime >= 1){
					//update logic here
					deltaTime--;
					frames++;
					
					//decrease the time every 60 seconds
					if(frames % 60 == 0)
						time = time-1;
					
					//these control the timing of the hit effects
					if(hit > 0){
						hit--;
					}
					if(hit1 > 0){
						hit1--;
					}
					if(hit2 > 0){
						hit2--;
					}
					if(hit3 > 0){
						hit3--;
					}
					
					//this is in charge of adding more notes at a semi-random time
					framesSinceLast++;
					notes.increment();
					
					int randFrame = ((int)Math.random()) * 25 + 35;
		
					if(framesSinceLast >= randFrame && notes.num < 5){
						notes.addNote();
						framesSinceLast = 0;
					}
					this.repaint();
				}
				
				
				//when time is up end the game
				if(time <= 0)
					running = false;
				
			
			}
			//when game finishes, remove game panel and load in end panel
			parentFrame.getContentPane().removeAll();
			EndPanel end = new EndPanel(650, 800, parentFrame, acc, notes.score);
			end.setBackground(new Color(25, 25, 25));
			parentFrame.add(end);
			parentFrame.pack();
		
		}
		//actions for the presses. Better than keyListener!
		Action pressAction1 = new AbstractAction(){
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e){
				pressedKey(1);
				hit1 = 10;
			}
		};
		Action pressAction2 = new AbstractAction(){
				private static final long serialVersionUID = 2L;
			@Override
			public void actionPerformed(ActionEvent e){
				pressedKey(2);
				hit2 = 10;
			}
		};
		Action pressAction3 = new AbstractAction(){
			private static final long serialVersionUID = 3L;

		@Override
			public void actionPerformed(ActionEvent e){
				pressedKey(3);
				hit3 = 10;
			}
		};
		
		private void pressedKey(int i){
			//logic for key presses, effects, and accuracy
			presses++;
			hit = 15;
			if (notes.keyPressed(i))
				hits++;
			acc = ((double)hits/presses)*100;
			acc = Double.parseDouble(df.format(acc));
		}
}

