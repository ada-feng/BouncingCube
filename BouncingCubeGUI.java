import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * This the control panel which has three buttons that can change the cube and add more cubes
 * 
 * @author  Ada Feng, Irene Chen
 *
 */
public class BouncingCubeGUI extends JPanel implements ActionListener {

	JButton WireFrame,Colorful,AddCube;
	JLabel message3;
	JLabel message2;
	JLabel message1;
	JLabel message0;
	
	SimpleCanvas cPanel;


/**
 * this is the constructor for BouncingCubeGUI which will take in the simple canvas 
 * @param cp
 */
	public BouncingCubeGUI(SimpleCanvas cp) {
		//initialize the menu JPanel which contain the three buttons
		//set the menu to be 3row in one column
		JPanel menu = new JPanel();
		GridLayout menugrid = new GridLayout(7, 1);
		menu.setLayout(menugrid);
		cPanel = cp;
		WireFrame = new JButton("Wireframe Cube");
		Colorful = new JButton("Colorful Cube");
		AddCube = new JButton("Add Another Cube");
		message0 =  new JLabel("Please click the mouse to start!");
		message1 =  new JLabel("Switch between colorful or wireframe!");
		message2 = new JLabel("Please add the cube slowly!");
		message3 = new JLabel("The new cube added will be larger than before.");
		//add listener
		WireFrame.addActionListener(this);
		Colorful.addActionListener(this);
		AddCube.addActionListener(this);
		menu.add(message0);
		menu.add(message1);
		menu.add(WireFrame);
		menu.add(Colorful);
		menu.add(message2);
		menu.add(message3);
		menu.add(AddCube);
		
		add(menu);
		

	}// end constructor
/**
 * listen to the action and check the source
 */
	public void actionPerformed(ActionEvent event) {
		//if user click wire frame cube
		if (event.getSource() == WireFrame) {
			cPanel.setColorful(false);
			cPanel.repaint();

		}
		//if user click colorful cube 
		if (event.getSource() == Colorful) {
			cPanel.setColorful(true);
			cPanel.repaint();

		}
		//if the user want to add another cube
		if (event.getSource() == AddCube) {
			cPanel.addCube();
			cPanel.repaint();

		}
	}
}