/**
 * SimpleCanvas.java
 *
 * Part of the basic graphics Template.
 *
 */

import javax.swing.*;

import javafx.scene.shape.Ellipse;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author Ada Feng, Irene Chen
 *
 */
public class SimpleCanvas extends JPanel implements MouseListener, ActionListener {

	public static final Point3D EYE= new Point3D(0,0,600);

	ContainBox mybox;
	Timer timer;
	ArrayList<MovingCube> movingcubes;
	//we use it to determine if we want to drau wiredrame or color
	boolean isColorful;
	Graphics2D g2d;
	ArrayList<Point3D> collision;
	BufferedImage background;
	double edge;

	
	/**
	 * The constructor initializes some varibles we want to toggle
	 */
	public SimpleCanvas() {
		
		setPreferredSize(new Dimension(700, 700));
		addMouseListener(this);
		int delay = 100; // milliseconds
		// create the timer which has actionlistener this
		timer = new Timer(delay, this);
		//default is wiredframe
		isColorful =false;
		mybox = new ContainBox();
		collision = new ArrayList<Point3D>();
		movingcubes = new ArrayList<MovingCube>();
		MovingCube myMC = new MovingCube(70.0);
		movingcubes.add(myMC);
		edge =20;
		background =mybox.mapImage();
		
	}
	/**
	 * @param tof true if we want color; false if we want the outlines
	 */
	public void setColorful(boolean tof) {
		isColorful = tof;
	}
	
	/**
	 * adds a new moving cube to our arraylist 
	 * the second cube starts at 30.0, then each cube is 5 large than the previous one
	 */
	public void addCube() {
		MovingCube cube = new MovingCube(edge);
		movingcubes.add(cube);
		edge += 5.0;
	}


	
	/**
	 *	paints background, all the cubes and collsions on the wall to the screen
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // without this no background color set.

		g2d = (Graphics2D) g; // cast so we can use JAVA2D.
		
		
		g2d.drawImage(background, 0,0,null);
		g2d.translate(getWidth() / 2, getHeight() / 2);
		g2d.scale(1, -1);
		
		//this will draw the outlines of the room
		mybox.drawCube(g2d, false);
		
		Collections.sort(movingcubes);
		
		for(int j =0; j<movingcubes.size();j++){
			MovingCube mc = movingcubes.get(j);
			//order the faces on the cube so that the back ones will be painted first
			mc.orderFace();
			
			//set up the color and stroke of the collisions ovals we want to paint 
			g2d.setPaint(Color.white);
			g2d.setStroke(new BasicStroke((float)(1),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));

			ArrayList<Point3D> later = new ArrayList<Point3D>();
			if(!collision.isEmpty()) {
				for (int i=0; i< collision.size(); i++) {
					Point3D currentpoint = collision.get(i);
					//if the collision if in front of the cube, store it to be painted later
					if(currentpoint.z>mc.getCenter().z) {
						later.add(currentpoint);
					}else {
						Point2D.Double point_2d = currentpoint.render2D(600);
						//several isocentric circles to emphasize
						g2d.drawOval((int)point_2d.getX()-5, (int)point_2d.getY()-5, 10, 10);
						g2d.drawOval((int)point_2d.getX()-10, (int)point_2d.getY()-10, 20, 20);
						g2d.drawOval((int)point_2d.getX()-15, (int)point_2d.getY()-15, 30, 30);
					}
					
				}
			}
			
			
			mc.drawCube(g2d, isColorful);
			

			//set up the color and stroke of the collisions ovals we want to paint 
			g2d.setPaint(Color.white);
			g2d.setStroke(new BasicStroke((float)(1),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
			
			//then draw the collisions in front of this cube
			if(!later.isEmpty()) {
				for (int i=0; i< later.size(); i++) {
					Point3D currentpoint = later.get(i);
					Point2D.Double point_2d = currentpoint.render2D(600);
					g2d.drawOval((int)point_2d.getX()-5, (int)point_2d.getY()-5, 10, 10);
					g2d.drawOval((int)point_2d.getX()-10, (int)point_2d.getY()-10, 20, 20);
					g2d.drawOval((int)point_2d.getX()-15, (int)point_2d.getY()-15, 30, 30);
				}
			}
			
			
		}
	}


	
	public void mouseClicked(MouseEvent e)
	 {
		
      if (timer.isRunning() == false) {
        timer.start();

      } else {
        timer.stop();

      }

	 }

	// Empty methods to satisfy MouseListener interface
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	/**
	 *this will perform the animation
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//reset the collsions
		collision = new ArrayList<Point3D>();
		//for each of the moving cube, move it and obtain the collisions to the wall
		//then rotate
		for(int i =0; i<movingcubes.size();i++){
			MovingCube mc = movingcubes.get(i);
			//adds all the collison points in each arraylist returned by our moving cube
			collision.addAll( mc.move(movingcubes));
			mc.rotateCube();
		}
		
		repaint();
		

	}

}// SimpleCanvas

