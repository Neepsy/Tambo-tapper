package tambourine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


import javax.imageio.ImageIO;

public class NoteManager {
	protected ArrayList<Note> notes;
	protected BufferedImage comboImg;
	protected int score;
	protected int combo;
	protected int num;
	protected double multi;
	protected String msg = "";
	protected int increment;
	protected BufferedImage note1, note2, note3, combo1, combo2,combo3;
	
	
	public NoteManager(int inc){
		notes = new ArrayList<Note>();
		score = 0;
		combo = 0;
		multi = 1;
		num = 0;
		comboImg = null;
		increment = inc;
		
		//load in the images
		try{
			note1 = ImageIO.read(getClass().getResource("/game/note1.png"));
			note2 = ImageIO.read(getClass().getResource("/game/note2.png"));
			note3 = ImageIO.read(getClass().getResource("/game/note3.png"));
			combo1 = ImageIO.read(getClass().getResource("/game/combo10.png"));
			combo2 = ImageIO.read(getClass().getResource("/game/combo20.png"));
			combo3 = ImageIO.read(getClass().getResource("/game/combo30.png"));
			
		}
		catch(Exception e){
			System.out.println("NM Error!");
			System.out.println(e);
		}
		
	}
	
	public void addNote(){
		//Increment note number
		num++;
		
		//add note of random type
		int type = (int) (Math.random() * 3) + 1;
			if(type == 1)
				notes.add(new Note(type, note1));
			if(type == 2)
				notes.add(new Note(type, note2));
			if(type == 3)
				notes.add(new Note(type, note3));
		
		//possibly add another note of a different value
		int rand = ((int) (Math.random() * 40)) + 1;
		if(rand % 10 == 0){
			type--;
			if(type == 0)
				type = 3;
		
			if(type == 1)
				notes.add(new Note(type, note1));
			if(type == 2)
				notes.add(new Note(type, note2));
			if(type == 3)
				notes.add(new Note(type, note3));
		}
		
	}
	
	public void increment(){
		//push all notes down X pixels
		for(Note n : notes){
			n.advance(increment);
		}
		checkNotes();
	}
	public boolean keyPressed(int key){
	
		boolean hit = false;
		//when key is pressed, check if any notes can be "hit"
		for(int i = 0;i<notes.size();i++){
			int time = notes.get(i).getTime();
			if(time >= 510 && time <= 570 && notes.get(i).getType() == key){
				//if so, remove the note and add to score and combo
				hit = true;
				msg = "Hit!";
				notes.remove(i);
				num--;
				i--;
				score+=(100*multi);
				combo+=1;
				if(combo % 15 == 0 && combo < 46){
					comboCheck();
				}
			}
		}
		return hit;
	}
	public void checkNotes(){
		//check for any notes that have hit the bottom, and delete them. remove combo if applicable
		for(int i = 0;i<notes.size();i++){
			if(notes.get(i).getTime() >= 600){
				notes.remove(i);
				num--;
				i--;
				msg = "Miss!";
				if(combo > 0)
				killCombo();
			}
		}
	}
	
	
	public void comboCheck(){
		//check if the player has reached a combo threshold and update it.
		multi = 1+((double) combo/15)/10;
		try{
			if(combo == 15)
				comboImg = combo1;
			if(combo == 30)
				comboImg = combo2;
			if(combo == 45)
				comboImg = combo3;
		}
		catch(Exception e){
			System.out.println(e);
		}
		msg = "Excellent! \n +Combo!";
	}
	
	public ArrayList<Note> getNotes(){
		return notes;
	}
	
	
	public void killCombo(){
		//remove the combo if you miss
		msg = "MISS! \n Combo Lost!";
		multi = 1;
		combo = 0;
		comboImg = null;
	
	}
	
}
