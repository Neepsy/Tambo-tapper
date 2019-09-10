package tambourine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class OptionPanel extends JPanel{
	
	protected int diff, time;
	MenuPanel parent;
	JRadioButton Time60, Time90;
	JRadioButton Diff1, Diff2, Diff3;
	
	public OptionPanel(MenuPanel parent){
		//declare all the radio buttons for options
		this.setPreferredSize(new Dimension(200,200));
		this.parent = parent;
		this.setLayout(null);
		Time60 = new JRadioButton("60 Sec");
		Time60.setBounds(10, 20, 100, 20);
		Time90 = new JRadioButton("90 Sec");
		Time90.setBounds(10, 40, 100, 20);
		Diff1 = new JRadioButton("Concert");
		Diff1.setBounds(110, 20, 100, 20);
		Diff2 = new JRadioButton("Symphonic");
		Diff2.setBounds(110, 40, 100, 20);
		Diff3 = new JRadioButton("Galloway");
		Diff3.setBounds(110, 60, 100, 20);
		OptionListener listen = new OptionListener();
		this.add(Time60);
		this.add(Time90);
		this.add(Diff1);
		this.add(Diff2);
		this.add(Diff3);
		
		Time60.setSelected(true);
		Diff1.setSelected(true);
	
		//add in the custom action listen in each button
		Time60.addActionListener(listen);
		Time90.addActionListener(listen);
		Diff1.addActionListener(listen);
		Diff2.addActionListener(listen);
		Diff3.addActionListener(listen);
		
		}
	
	public void paintComponent(Graphics g){
		g.drawString("Options", 10, 10);
	}
	
	

	class OptionListener implements ActionListener{

		//this event listener checks for what button and clicks, and sets the other buttons as required
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String clicked = ((JRadioButton) e.getSource()).getText();
			System.out.println(clicked);
			if(clicked.equals("60 Sec")){
				Time90.setSelected(false);
				parent.setTime(60);
			}
			
			if(clicked.equals("90 Sec")){
				Time60.setSelected(false);
				parent.setTime(90);
			}
			
			if(clicked.equals("Concert")){
				Diff2.setSelected(false);
				Diff3.setSelected(false);
				parent.setDiff(4);
			}
			
			if(clicked.equals("Symphonic")){
				Diff1.setSelected(false);
				Diff3.setSelected(false);
				parent.setDiff(6);
			}
			
			if(clicked.equals("Galloway")){
				Diff1.setSelected(false);
				Diff2.setSelected(false);
				parent.setDiff(8);
			}
			
			
		}
		

	}
	
}
