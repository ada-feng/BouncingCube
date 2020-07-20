
/**
 * ContainBox.java
 *
 *
 * 
 *
 */
import java.awt.*;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A cubic container box that simulate a room setup
 * it is a subclass of "cube", so it also has an array of face,
 * and can be drawn by drawCube if we want to, and has access to
 * all the methods in Cube
 */
public class ContainBox extends Cube {

	//a background color of our choosing
	Color background = new Color(247, 233, 169);
  
  //a transparent color for the first cube
	Color transparent = new Color(0, 0, 0, 0);

	/**
	 * Constructor of the contain box which is the room that contains the bouncing
	 * cube 
	 */
	public ContainBox() {
		// initialize the box 
    
		//first make 8 vertices for the box centered at 0
    //with 400.0 edges
		Point3D V0 = new Point3D(200.0, 200.0, 200.0);
		Point3D V1 = new Point3D(200.0, 200.0, -200.0);
		Point3D V2 = new Point3D(200.0, -200.0, -200.0);
		Point3D V3 = new Point3D(200.0, -200.0, 200.0);
		Point3D V4 = new Point3D(-200.0, 200.0, 200.0);
		Point3D V5 = new Point3D(-200.0, 200.0, -200.0);
		Point3D V6 = new Point3D(-200.0, -200.0, -200.0);
		Point3D V7 = new Point3D(-200.0, -200.0, 200.0);
		
    // use the vertices to construct faces, the vertices in counterclockwise order
		Face a = new Face(V0, V3, V2, V1, background);
		Face b = new Face(V4, V5, V6, V7, background);
		Face c = new Face(V0, V4, V7, V3, background);
		Face d = new Face(V1, V2, V6, V5, background);
		Face e = new Face(V0, V1, V5, V4, background);
		Face front = new Face(V3, V7, V6, V2, transparent);
		arrayOfFace = new Face[6];
		arrayOfFace[0] = a;
		arrayOfFace[1] = b;
		arrayOfFace[2] = c;
		arrayOfFace[3] = d;
		arrayOfFace[4] = e;
		arrayOfFace[5] = front;

	}
	
  /**
	 * this method draws a face in 2D with texture from an input image onto an output bufferedImage
	 * @param image: the input
   * @parma output: the bufferedimage we will write on
   * @myFace a Face that has vertices stored in counterclockwise order
	 */
	public void drawFaceTexture(BufferedImage image, BufferedImage output, Face myface) {

		Point3D v0 = myface.getVertex(0);
		Point2D.Double newV0 = v0.render2D(600);
		Point3D v1 = myface.getVertex(1);
		Point2D.Double newV3 = v1.render2D(600);
		Point3D v2 = myface.getVertex(2);
		Point2D.Double newV2 = v2.render2D(600);
		Point3D v3 = myface.getVertex(3);
		Point2D.Double newV1 = v3.render2D(600);

		// for each pixel in the orginal image
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				int myColor = image.getRGB(i, j);
        //get u,v fractions
				double u = i * 0.001;
				double v = j * 0.001;
        
        //to find the x and the y coordinate of the corresponding point on the triangle
        // (1−𝑣)(1−u)P0 +(1−𝑣)uP1 +𝑣(1−u)P3 +u𝑣P2
				double xcoor = (1 - v) * (1 - u) * newV0.getX() + (1 - v) * u * newV1.getX()
						+ v * (1 - u) * newV3.getX() + u * v * newV2.getX();
				double ycoor = (1 - v) * (1 - u) * newV0.getY() + (1 - v) * u * newV1.getY()
						+ v * (1 - u) * newV3.getY() + u * v * newV2.getY();
            
				// since the x,y coordinate is based on origin in the center, 
        //y pointing up, we need to convert it to the corresponding pixel coordinate in the buffered image
				output.setRGB((int) xcoor + 350, 350 - (int) ycoor, myColor);

			}
		}
	}
  
  /**
	 * This method draws texture for all faces on a new buffered image object 
   * @return the bufferedImage output that has pixels set up as in texture
	 */
	public BufferedImage mapImage() {
		BufferedImage output = new BufferedImage(700, 700, BufferedImage.TYPE_INT_BGR);
		orderFace();
		BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_BGR);
		//This is the direct path
		//File input = new File("/Users/xiaoxiao/Desktop/background.jpg");
		//This is relative path
		File input = new File("./background.jpg");
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    //draw each face, except the last face
		for (int i = 0; i < 5; i++) {
			drawFaceTexture(image, output, arrayOfFace[i]);
		}
		return output;

	}
}


