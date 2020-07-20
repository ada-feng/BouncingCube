
import java.awt.BorderLayout;

import javax.swing.*;

/**
 * set up my frame for the window of bouncing cube
 * 
 * @author Ada Feng, Irene Chen
 *
 */
public class BouncingCubeController {

	/**
	 * This is the main function of the Bouncing cubes, it will provides the window
	 * that contain the GUI and canvas of our program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleCanvas myCanvas = new SimpleCanvas();
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Bouncing Cube");
		myFrame.setSize(700, 700);

		// Sets the window to close when upper right corner clicked.
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BouncingCubeGUI bcgui = new BouncingCubeGUI(myCanvas);

		myFrame.setLayout(new BorderLayout());
		myFrame.add(myCanvas, BorderLayout.CENTER);
		myFrame.add(bcgui, BorderLayout.EAST);

		myFrame.add(myCanvas);
		myFrame.pack();
		myFrame.setResizable(true);
		myFrame.setVisible(true);

	}

}
