
import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;


/**
 * This is the face class that contain the vertices
 * it implements comparable so the user could sort the face later by using the
 *  distance between the eye and the face
 * @author Ada Feng, Irene Chen
 *
 */
public class Face implements Comparable<Face>{
	//declare the array of point, the color of this face and the eyes vector
	Point3D[] arrayOfPoint;
	Color myColor;
	Point3D eyes = SimpleCanvas.EYE;
  
	/**
	 * This is the constructor of the face, vertices must be given in counterclockwise order
	 * @param v0
	 * @param v1
	 * @param v2
	 * @param v3
	 */
	public Face(Point3D v0,Point3D v1,Point3D v2, Point3D v3, Color color) {
		//initialize the array with 4 vertices
		//initialize the color and eyes vector
		 arrayOfPoint = new Point3D[4];
		 arrayOfPoint[0]=v0;
		 arrayOfPoint[1]=v1;
		 arrayOfPoint[2]=v2;
		 arrayOfPoint[3]=v3;
		 myColor =  color;
		 
		
	}
  
	/**
	 * get the vertex at certain postion of this array
	 * @param n : the index of the vertex
	 * @return
	 */
	public Point3D getVertex(int n) {
		return arrayOfPoint[n];
	}
	
	/**
	 * set the vertex at certain postion of this array to be the point 
	 * @param n
	 * @param point
	 */
	public void setVertex(int n, Point3D point) {
		arrayOfPoint[n] = point;
	}
  

	/**
	 * calculate the normal point outward of this face
	 * @return
	 */
	public Point3D getNormal() {
		Point3D v0 = arrayOfPoint[0];
		Point3D v1 = arrayOfPoint[1];
		Point3D v2 = arrayOfPoint[2];
		Point3D vector1 = v2.subtractPoint(v0);
		Point3D vector2 = v2.subtractPoint(v1);
		Point3D normal = vector1.getCrossProduct(vector2);
		return normal;	
		
	}
  
	/**
	 * set the face color to be certain color
	 * @param newc
	 */
	public void setColor(Color newc) {
		myColor =newc;
		
	}
  
	/**
	 * get the color of this face
	 * @return
	 */
	public Color getColor() {
		return myColor;
	}

	
	/**
	 * override the compareTo method so we can compare which face is more closer to the eyes
	 * by using the dot product of the normals and eyes vector
	 * 
	 */
	public int compareTo(Face compareFace) {
		Point3D thisnormal = this.getNormal();
		Point3D othernormal = compareFace.getNormal();
		Point3D thisver =this.getVertex(1);
		Point3D thatver =compareFace.getVertex(1);
		Point3D v1 =eyes.subtractPoint(thisver);
		Point3D v2 =eyes.subtractPoint(thatver);
		
	
		double thisresult = thisnormal.getDotProduct(v1);
		double otherresult = othernormal.getDotProduct(v2);
		
		//if this face is more closer to the eyes return 1
		if (thisresult>otherresult) {
			return 1;
			
		}
		//if this face is less closer to the eyes return -1
		if (thisresult<otherresult) {
			return -1;
			
		}
		//if this face as close as the other face return 0
		if(thisresult==otherresult) {
			return 0;
		}
		return 0;
	}
  
	/**
	 * compare the normals with eyes vector if it is positive that means we could see the face
	 * if it is negative that means we could not see the face
	 * 
	 */
	public boolean compareDotproduct() {
		Point3D thisnormal = this.getNormal();
	
	
		double thisresult = thisnormal.getDotProduct(eyes);
		
		
		//if this face is more closer to the eyes return 1
		if (thisresult>0) {
			return true;
			
		}
		//if this face is less closer to the eyes return -1
		if (thisresult<=0) {
			return false;
			
		}
		
		return false;
	}
	
	
	 /**
	 * Change the 3D coordination to 2D coordination by using perspective transformation
	 * @param vertex
	 * @return the 2D shape
	 */
	public Path2D.Double get2DFace(Point3D eyescoordination){
		
		Path2D.Double path = new Path2D.Double();
		Point3D current1 = arrayOfPoint[0];
		
		Point2D.Double v1 = current1.render2D(eyescoordination.z);
		path.moveTo(v1.x, v1.y);
		 for (int j =0; j<arrayOfPoint.length; j++) {
				Point3D current = arrayOfPoint[j];
				Point2D.Double vi = current.render2D(eyescoordination.z);
				path.lineTo(vi.getX(),vi.getY());
			}
		 path.lineTo(v1.x, v1.y);
		 
		 return path;
		
	}

}