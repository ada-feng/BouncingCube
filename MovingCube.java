
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the moving cube which is a cube that will move according to some
 * basic rigid body collision rules. This class is a subclass of Cube,it has
 * access to all the methods in Cube which we can use freely
 * 
 * @author Ada Feng, Irene Chen
 *
 */
public class MovingCube extends Cube implements Comparable<MovingCube> {
	// declare a Point3D array which contain the certain vertices
	// declare the initial speed for x,y,and z
	// the angle of the rotation
	Point3D[] vertices;
	double speed_x, speed_y, speed_z;
	double angle;
	// the wall length, half length of diagonal, the length of half edge,
	double wall_half = 200.0;
	double half_diagonal;
	double half_edge;

	// the initial option of which wall is hit
	// the final static integer indicates which which wall is hit
	int option;
	public final static int FRONT = 0;
	public final static int BACK = 1;
	public final static int TOP = 2;
	public final static int BOTTOM = 3;
	public final static int LEFT = 4;
	public final static int RIGHT = 5;

	/**
	 * Construct the moving cube by initialize a speed x,y,z and a empty array list
	 * of collision point
	 */
	public MovingCube(double edge) {
		// Initialize the option to be no wall is hit
		// and the speed on x,y,z direction
		option = -1;
		speed_x = 3;
		speed_y = 3;
		speed_z = 4;
		center = new Point3D(0, 0, 0);
		// Initialize half length of diagonal, the length of half edge, the angle of
		// rotation to be 0.1

		half_edge = edge / 2;
		angle = 0.1;
		half_diagonal = Math.sqrt(3) * half_edge;

		// initialize the vertices by uisng the half_edge and put them into a vertices
		Point3D V0 = new Point3D(half_edge, half_edge, half_edge);
		Point3D V1 = new Point3D(half_edge, half_edge, -half_edge);
		Point3D V2 = new Point3D(half_edge, -half_edge, -half_edge);
		Point3D V3 = new Point3D(half_edge, -half_edge, half_edge);
		Point3D V4 = new Point3D(-half_edge, half_edge, half_edge);
		Point3D V5 = new Point3D(-half_edge, half_edge, -half_edge);
		Point3D V6 = new Point3D(-half_edge, -half_edge, -half_edge);
		Point3D V7 = new Point3D(-half_edge, -half_edge, half_edge);
		vertices = new Point3D[] { V0, V1, V2, V3, V4, V5, V6, V7 };

		// set up the color for each face
		Color MyColor0 = new Color(135, 150, 166);
		Color MyColor1 = new Color(244, 205, 207);
		Color MyColor2 = new Color(197, 184, 166);
		Color MyColor3 = new Color(150, 164, 140);
		Color MyColor4 = new Color(122, 114, 129);
		Color MyColor5 = new Color(149, 84, 85);
		// set up each face
		Face a = new Face(vertices[0], vertices[3], vertices[2], vertices[1], MyColor0);
		Face b = new Face(vertices[4], vertices[5], vertices[6], vertices[7], MyColor1);
		Face c = new Face(vertices[0], vertices[4], vertices[7], vertices[3], MyColor2);
		Face d = new Face(vertices[1], vertices[2], vertices[6], vertices[5], MyColor3);
		Face e = new Face(vertices[0], vertices[1], vertices[5], vertices[4], MyColor4);
		Face f = new Face(vertices[3], vertices[7], vertices[6], vertices[2], MyColor5);
		// put those face into the array
		arrayOfFace = new Face[6];
		arrayOfFace[0] = a;
		arrayOfFace[1] = b;
		arrayOfFace[2] = c;
		arrayOfFace[3] = d;
		arrayOfFace[4] = e;
		arrayOfFace[5] = f;

	}

	/**
	 * get the half length of current cube's diagonal
	 * 
	 * @return half_diagonal
	 */
	public double getHalfDiagonal() {

		return half_diagonal;
	}

	/**
	 * get the option of which wall is hit by the cube
	 * 
	 * @return option
	 */
	public int getOption() {
		return option;
	}

	/**
	 * Check whether a cube hit the wall, if yes, add the vertices to the array of
	 * collision point
	 * 
	 * @return the array of collision point
	 */
	public ArrayList<Point3D> getCollisionPointWithWall() {
		ArrayList<Point3D> collisionP = new ArrayList<Point3D>();
		// check for each vertices
		for (int i = 0; i < vertices.length; i++) {
			// whether the absolute value of the x,y,z coordination e
			double v_x = Math.abs(vertices[i].x);
			double v_y = Math.abs(vertices[i].y);
			double v_z = Math.abs(vertices[i].z);
			if (v_x >= wall_half || v_y >= wall_half || v_z >= wall_half) {

				Point3D newp = new Point3D(vertices[i].x, vertices[i].y, vertices[i].z);
				collisionP.add(newp);
			}
		}
		return collisionP;

	}

	/**
	 * This function will move all the cube in the ArrayList<MovingCube>
	 * 
	 * @param movingcubes
	 * @return a array list of vertices that hit the wall
	 */
	public ArrayList<Point3D> move(ArrayList<MovingCube> movingcubes) {
		// set up a temporary array that will hold the next vertices coordination

		Point3D[] temporary = new Point3D[8];
		for (int i = 0; i < vertices.length; i++) {
			temporary[i] = vertices[i].getTranslatePoint(speed_x, speed_y, speed_z);
		}
		// check whether the next vertices will pass through the wall and return a array
		// of new speed
		double[] actual_speeds = checkCollisionWithWall(temporary);
		// translate the cube and its center to the new position
		translateCube(actual_speeds[0], actual_speeds[1], actual_speeds[2]);
		center.translate(actual_speeds[0], actual_speeds[1], actual_speeds[2]);
		// rotate the cubes
		checkCollsionWithCubes(movingcubes);
		// get the collision point with wall
		return getCollisionPointWithWall();

	}

	/**
	 * check whether the point3d temp hit any wall
	 * 
	 * @param temp
	 * @return the array of new speed
	 */
	public double[] checkCollisionWithWall(Point3D[] temp) {
		// initialize the option to be -1 if the vertices did not hit any wall
		double coll_index = -1;
		// initialize actual speed be the original speed
		double actual_speed_x = speed_x;
		double actual_speed_y = speed_y;
		double actual_speed_z = speed_z;
		// check whether the x,y,z is recersed
		boolean x_reversed = false;
		boolean y_reversed = false;
		boolean z_reversed = false;
		// for each vertices
		for (int i = 0; i < temp.length; i++) {
			Point3D point = temp[i];
			// get the coordination of this points x,y,z
			double p_x = point.getX();
			double p_y = point.getY();
			double p_z = point.getZ();

			// if any point will hit the wall in the future,
			// the actual speed will be the distance between the wall and the point to let
			// it just hit the wall
			if (p_x >= wall_half) {
				// if we have not reverse the x yet
				if (x_reversed == false) {
					actual_speed_x = speed_x - (p_x - wall_half);
					speed_x = -speed_x;
					x_reversed = true;
					option = RIGHT;
				}

			} else if (p_x <= -wall_half) {
				// if we have not reverse the x yet
				if (x_reversed == false) {
					actual_speed_x = speed_x - (p_x + wall_half);
					speed_x = -speed_x;
					x_reversed = true;
					option = LEFT;

				}
			}
			if (p_y >= wall_half) {
				// if we have not reverse the y yet
				if (y_reversed == false) {
					actual_speed_y = speed_y - (p_y - wall_half);

					speed_y = -speed_y;
					y_reversed = true;
					option = TOP;
				}

			} else if (p_y <= -wall_half) {
				// if we have not reverse the y yet
				if (y_reversed == false) {
					actual_speed_y = speed_y - (p_y + wall_half);
					speed_y = -speed_y;
					y_reversed = true;
					option = BOTTOM;

				}

			}
			if (p_z >= wall_half) {
				// if we have not reverse the z yet
				if (z_reversed == false) {
					actual_speed_z = speed_z - (p_z - wall_half);
					speed_z = -speed_z;
					z_reversed = true;
					option = FRONT;

				}

			} else if (p_z <= -wall_half) {
				// if we have not reverse the z yet
				if (z_reversed == false) {
					actual_speed_z = speed_z - (p_z + wall_half);
					speed_z = -speed_z;
					z_reversed = true;
					option = BACK;
				}
			}
		}
		return new double[] { actual_speed_x, actual_speed_y, actual_speed_z, coll_index };

	}

	/**
	 * get the speed_x
	 * 
	 * @return
	 */
	public double getSpeedX() {
		return speed_x;
	}

	/**
	 * get the speed_y
	 * 
	 * @return
	 */
	public double getSpeedY() {
		return speed_y;
	}

	/**
	 * get the speed_z
	 * 
	 * @return
	 */
	public double getSpeedZ() {
		return speed_z;
	}

	/**
	 * get the Point3D[] vertices of current moving cube
	 * 
	 * @return
	 */
	public Point3D[] getVertices() {
		return vertices;

	}

	/**
	 * this the helper method to sort the cube with other cube's position
	 * 
	 * @param other
	 * @return
	 */
	public int compareTo(MovingCube other) {
		// compare the z value of the cube's center
		Point3D thiscenter = this.getCenter();
		Point3D thatcenter = other.getCenter();

		// if this cube is more closer to the eyes return 1
		if (thiscenter.getZ() > thatcenter.getZ()) {
			return 1;

		}
		// if this face is less closer to the eyes return -1
		if (thiscenter.getZ() < thatcenter.getZ()) {
			return -1;

		}
		// if this face as close as the other face return 0
		if (thiscenter.getZ() == thatcenter.getZ()) {
			return 0;
		}
		return 0;
	}

	/**
	 * translate the cube coordination of each vertices by x,y,z
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translateCube(double x, double y, double z) {
		for (int i = 0; i < 8; i++) {

			vertices[i].translate(x, y, z);

		}

	}

	/**
	 * rotate the cube along the Z axis theta degree
	 * 
	 * @param theta
	 */
	public void rotateAroundZCube(double theta) {
		// translate to the original point
		this.translateCube(-center.x, -center.y, -center.z);
		// go through each vertices in the cube and apply the change
		for (int i = 0; i < 8; i++) {
			vertices[i].rotateAroundZ(theta);
		}
		// translate the cube back
		this.translateCube(center.x, center.y, center.z);

	}

	/**
	 * rotate the cube along the Y axis theta degree
	 * 
	 * @param theta
	 */
	public void rotateAroundYCube(double theta) {

		// translate to the original point
		this.translateCube(-center.x, -center.y, -center.z);
		// go through each vertices in the cube and apply the change
		for (int i = 0; i < 8; i++) {
			vertices[i].rotateAroundY(theta);
		}
		// translate the cube back
		this.translateCube(center.x, center.y, center.z);

	}

	/**
	 * rotate the cube along the X axis theta degree
	 * 
	 * @param theta
	 */
	public void rotateAroundXCube(double theta) {
		// go through each vertices in the cube and apply the change//translate to the
		// original point
		this.translateCube(-center.x, -center.y, -center.z);
		// go through each vertices in the cube and apply the change
		for (int i = 0; i < 8; i++) {
			vertices[i].rotateAroundX(theta);

		}
		// translate the cube back
		this.translateCube(center.x, center.y, center.z);

	}

	/**
	 * Rotate the cube according to the wall that the cube hit
	 */
	public void rotateCube() {
		switch (option) {
		case LEFT: {
			this.rotateAroundZCube(-angle);
			break;
		}
		case RIGHT: {
			this.rotateAroundZCube(angle);
			break;
		}
		case BOTTOM: {

			this.rotateAroundYCube(-angle);
			break;
		}

		case TOP: {
			this.rotateAroundYCube(angle);
			break;
		}
		case FRONT: {
			this.rotateAroundXCube(-angle);
			break;
		}
		case BACK: {
			this.rotateAroundXCube(angle);
			break;
		}
		}
	}

	/**
	 * this is helper method for print out the moving cube
	 */
	public String toString() {
		return Arrays.deepToString(vertices);
	}

	/**
	 * This will help us check whether the cube hit the cube
	 * 
	 * @param cubes
	 */
	public void checkCollsionWithCubes(ArrayList<MovingCube> cubes) {
		// go through every vertices of that cube
		for (int i = 0; i < cubes.size(); i++) {

			MovingCube other_cube = cubes.get(i);
			// if the other cube equals to the current cube than do nothing
			if (other_cube == this) {
				// do nothing
			} else {
				// get the other cube's diagonal, center point to calculate
				// the distance between the current cube and other cube

				double other_half_d = other_cube.getHalfDiagonal();
				Point3D other_center = other_cube.getCenter();
				Point3D d_vector = other_center.subtractPoint(center);
				double real_distance = d_vector.getLength();
				// assume the cube is a sphere and the maximum distance they could have is the
				// sum
				// of their diagonal
				double max_distance = half_diagonal + other_half_d;
				// if the real distance is smaller than that maximum distance
				// then the cube and the other cube collided
				if (real_distance <= max_distance) {
					// if the current real distance is smaller or equal to other cube's diagonal
					// that means one cube is inside the other
					if (real_distance <= other_half_d) {
						// we translate the cube to other position
						translateCube(other_half_d, other_half_d, other_half_d);
						center.translate(other_half_d, other_half_d, other_half_d);

					}
					// if the current real distance is smaller or equal to this cube's diagonal
					// that means one cube is inside the other
					if (real_distance <= half_diagonal) {
						// we translate the cube to other position
						translateCube(half_diagonal, half_diagonal, half_diagonal);
						center.translate(half_diagonal, half_diagonal, half_diagonal);

					}
					// if the two cube is moving in the same x direction but collided
					// we only change one of the cube's x moving direction
					if (speed_x * other_cube.getSpeedX() > 0) {
						if (Math.abs(speed_x) > Math.abs(other_cube.getSpeedX())) {
							speed_x = -speed_x;
						}
					}
					// if the two cube is moving in the same y direction but collided
					// we only change one of the cube's y moving direction

					if (speed_y * other_cube.getSpeedY() > 0) {
						if (Math.abs(speed_y) > Math.abs(other_cube.getSpeedY())) {
							speed_y = -speed_y;
						}
					}
					// if the two cube is moving in the same z direction but collided
					// we only change one of the cube's z moving direction
					if (speed_z * other_cube.getSpeedZ() > 0) {
						if (Math.abs(speed_z) > Math.abs(other_cube.getSpeedZ())) {
							speed_z = -speed_z;
						}
					}
					// otherwise, we just change all the moving direction
					speed_x = -speed_x;
					speed_y = -speed_y;
					speed_z = -speed_z;

					return;
				}
			}
		}
	}
}
