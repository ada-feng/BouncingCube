import java.awt.geom.Point2D;

/**
 * this is my class for Point3D
 * 
 * @author Ada Feng, Irene Chen
 *
 */
public class Point3D {
	double x, y, z;


	/**
	 * This is the constructor of my point 3D
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 */
	public Point3D(double x1, double y1, double z1) {
		x = x1;
		y = y1;
		z = z1;
		
	}

	/**
	 * get the x value
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * get the y value
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * get the z value
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * get the point projected onto the screen in 2D
	 * 
	 * @return
	 */
	public Point2D.Double render2D(double eye){
		  double persp_x = x/(1-(z/eye));
		  double persp_y = y/(1-(z/eye));
		  Point2D.Double point =new Point2D.Double(persp_x, persp_y);
		  return point;
		 }
	
	/**
	 * help print the point
	 * 
	 * @return a string formatted to represent this 3D point in a easily readable manner 
	 */
	public String toString() {
		  return "("+ x+","+y+","+z+")";
		 }
	
	/**
	 * Take in the vector that is going to be subtracted
	 * 
	 * @param the point to be substracted
	 * @return the vector result
	 */
	public Point3D subtractPoint(Point3D subtracted) {
		double resultx =  this.x-subtracted.getX();
		double resulty =  this.y-subtracted.getY();
		double resultz =  this.z-subtracted.getZ();
		Point3D mypoint = new Point3D(resultx, resulty, resultz);
		return mypoint;
	}

	/**
	 * calculate the cross product of one vector and the other vector v
	 * 
	 * @param v
	 * @return a new vector 
	 */
	public Point3D getCrossProduct(Point3D v) {
		double u1 = this.x;
		double u2 = this.y;
		double u3 = this.z;
		double v1 = v.getX();
		double v2 = v.getY();
		double v3 = v.getZ();

		// calculate i j k
		double uvi = u2 * v3 - v2 * u3;
		double uvj = u1 * v3 - v1 * u3;
		double uvk = u1 * v2 - v1 * u2;
		Point3D result = new Point3D(uvi, -uvj, uvk);

		return result;
	}
  
	/**
	 * calculate the dot product of one vector and the other vector v
	 * @param v
	 * @return
	 */
	public double getDotProduct(Point3D v) {
		double u1 = this.x;
		double u2 = this.y;
		double u3 = this.z;
		double v1 = v.getX();
		double v2 = v.getY();
		double v3 = v.getZ();
		// calculate the sum of ux*vx uy*vy and uz*vz;
		double result = u1 * v1 + u2 * v2 + u3 * v3;

		return result;
	}

	/**
	 * @param tx
	 * @param ty
	 * @param tz
	 * this point will be translated
	 */
	public void translate(double tx, double ty, double tz) {
		  x+=tx;
		  y+=ty;
		  z+=tz;  
		 }
		 
	/**
	 * @param tx
	 * @param ty
	 * @param tz
	 * @return a new point
	 * get a new point that is equal to this point tranlated
	 */
	public Point3D getTranslatePoint(double tx, double ty, double tz) {
		  tx+=x;
		  ty+=y;
		  tz+=z;
		  return new Point3D(tx,ty,tz);
	}
	
	/**
	 * @param theta
	 */
	public void rotateAroundZ(double theta) {
		double oldx = this.x;
		double oldy = this.y;
		double oldz = this.z;
	//we change the x and coordination by using this
	//The new x coordinate is x cos 𝜃 − y sin 𝜃. The new y is x sin 𝜃 + y cos 𝜃. 
		x = oldx*Math.cos(theta)-oldy*Math.sin(theta);
		y = oldx*Math.sin(theta)+oldy*Math.cos(theta);
		z = oldz;
	}
	
	/**
	 * rotate around the Y axis
	 * @param point, theta
	 * @return
	 */
	public void rotateAroundY(double theta) {
		double oldx = this.x;
		double oldy = this.y;
		double oldz = this.z;
		// When we rotate around the y axis, z plays the role of x, and x plays the role of y, 
		//so the new z coordinate is z cos 𝜃 − x sin 𝜃. Similarly, the new x is z sin 𝜃 + x cos 𝜃. 
		 x = oldx*Math.cos(theta)+oldz*Math.sin(theta);
		 y = oldy;
		 z = -oldx*Math.sin(theta)+oldz*Math.cos(theta);
	}
  
	/**
	 * rotate around the X axis
	 * @param point, theta
	 * @return
	 */
	public void rotateAroundX(double theta) {
		double oldx = this.x;
		double oldy = this.y;
		double oldz = this.z;
		//when we rotate around x axis
		//Now y plays the role of x and z plays the role of x. 
		//so the new y coordinate is y cos 𝜃 − z sin 𝜃. Similarly, the new z is y sin 𝜃 + z cos 𝜃. 
		 x = oldx;
		 y = oldy*Math.cos(theta)-oldz*Math.sin(theta);
		 z = oldy*Math.sin(theta)+oldz*Math.cos(theta);
	}
	
	/**
	 * normalize the current vector
	 * @return
	 */
	public Point3D normalize() {
		  double sum = getLength();
		  double t = 1/sum;
		  return new Point3D(t*x,t*y,t*z);
		 }
	
	/**
	 * get the distance from this point to the origin or
	 * the vector magnitude
	 * @return the length of this vector
	 */
	public double getLength() {
		double sum = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
		return Math.pow(sum, 0.5);
	}

}


