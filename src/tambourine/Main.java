package tambourine;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

public class Main{
	static boolean done = false;

	public static void main(String[] args) throws InterruptedException, LineUnavailableException, IOException {
		
		/* load in the music
		String soundName = "src/Music2.wav";    
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start(); */
		
		JFrame frame = new JFrame();
		frame.setTitle("Tambourine");
		frame.setSize(new Dimension(650, 800));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		
		MenuPanel panel = new MenuPanel(650, 800,frame);

		frame.add(panel);
		frame.pack();
		frame.addKeyListener(panel);
		frame.setVisible(true);
	
		panel.requestFocusInWindow();
	

	}
	
	public void playGame(){
		
	}


}
