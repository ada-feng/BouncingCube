
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Arrays;


/**
 * This class is the abstract Cube class for the contain box and moving cube
 *  which has a array of face with size 6. A point3D which represent the center
 *  Another point3D which has the eyes coordination from SimpleCanvas
 * @author Ada Feng, Irene Chen
 *
 */
public abstract class Cube  {
	//declare arrayOfFace,center and eyescoordination
	Face[] arrayOfFace;
	Point3D center;
	Point3D eyescoordination = SimpleCanvas.EYE;
	
	
	/**
	 * This will set the center point to be c
	 * @param c
	 */
	public void setCenter(Point3D c) {
		center =c;
	}
	/**
	 * get the center of the current cube
	 * 
	 * @return
	 */
	public Point3D getCenter() {
		return center;
	}
	
	/**
	 * this method will get the face at n+1 position of the array
	 * @param n
	 * @return
	 */
	public Face getFace(int n) {
		return arrayOfFace[n];
		
	}
	
	
	/**
	 * this method will set the face at n+1 position of the array 
	 * @param n
	 * @param face
	 */
	public void setFace(int n, Face face) {
		arrayOfFace[n] = face;
		
	}
	
	/**
	 * reorganize the array because the face is comparable to the ascending order from 
	 */
	public void orderFace() {
		Arrays.sort(arrayOfFace);
		
	}
	
	/** Draw the cube using the given graphic context. */
   public void drawCube(Graphics2D g2d, boolean fill) {
	   //order the face by using their distance from the eyes
	   orderFace();
	   //draw out each face or fill each face with a color
	   for (int i =0; i<arrayOfFace.length; i++) {
			Face current = arrayOfFace[i];
			Shape to_draw = current.get2DFace(eyescoordination);
			Color color = current.getColor();
			
			if(fill) {
				g2d.setColor(color);
				g2d.fill(to_draw);
				
			}
			else {
				g2d.setColor(Color.black);
				g2d.setStroke(new BasicStroke((float) (3), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.draw(to_draw);
			}
			
		}

   }
}
