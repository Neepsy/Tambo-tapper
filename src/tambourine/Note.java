package tambourine;

import java.awt.image.BufferedImage;

public class Note {

	private int time;
	private int type;
	private BufferedImage img;
	
	public Note(int t, BufferedImage note){
		time = 0;
		type = t;
		img = note;
	}
	
	public void advance(int a){
		time += a;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getType(){
		return type;
	}

	public BufferedImage getImg(){
		return img;
	}
}
