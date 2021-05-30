package core;

import processing.core.PApplet;
import processing.core.PShape;

/**
 * <h3>An abstract shape class that encompasses the PShape class and contains
 * methods for creating and editing shapes.</h3> It also stores certain other
 * useful information and has some utility functions.
 * <p>
 * It also has a static draw method, but it currently only works with three
 * shapes:
 * <ul>
 * <li>Square (Rectangle)</li>
 * <li>Triangle</li>
 * <li>Ellipse</li>
 * </ul>
 * </p>
 */
public abstract class Shape {

	protected PShape shape;
	protected PApplet pApplet;

	private String name;

	protected int x;
	protected int y;
	protected int h;
	protected int w;

	protected int color;
	protected int stroke;
	private boolean isSelected;
	private boolean isDragging;
	protected ShapeType type;

	/**
	 * Cretaes a new shape and associates it with the main pApplet.
	 * 
	 * @param pApplet The handle of the main pApplet.
	 */
	public Shape(PApplet pApplet) {
		this.pApplet = pApplet;
		this.name = "";
	}

	/**
	 * Creates a new shape with the given characteristics.
	 * 
	 * @param x      The X-coordinate of the new shape to create
	 * @param y      The Y-coordinate of the new shape to create
	 * @param w      The width of the new shape to create
	 * @param h      The height of the new shape to create
	 * @param stroke The stroke of the new shape to create (currently unused)
	 * @param color  The color of the new shape to create
	 */
	public abstract void create(int x, int y, int w, int h, int stroke, int color);

	/**
	 * Updates the internal PShape if any of the attribues have changed.
	 */
	public abstract void update();

	/**
	 * Checks if the mouse currently is within the shape.
	 * 
	 * @return True if the mouse is in the shape
	 */
	public abstract boolean isInShape();

	/**
	 * Hilights all the vertices of the shape with little red circles, used so that
	 * the user can see if the shape is selected.
	 */
	public abstract void highlightVertices();

	/**
	 * Translates possible wonky X, Y, width and height parameters (for example, if
	 * the shape is created in a bottom left to top right direction, creating
	 * negative width and height) to proper attributes with a positive width and
	 * height.
	 * 
	 * @param x X-coordinate of the shape
	 * @param y Y-coordinate of the shape
	 * @param w Width of the shape
	 * @param h Height of the shape
	 * @return An int array with the new corrected values in the format of [x, y, w,
	 *         h]
	 */
	protected static int[] translate(int x, int y, int w, int h) {
		if (h >= 0 && w >= 0) {
			int[] out = { x, y, w, h };
			return out;
		} else if (h >= 0 && w < 0) {
			int[] out = { x + w, y, -w, h };
			return out;
		} else if (h < 0 && w >= 0) {
			int[] out = { x, y + h, w, -h };
			return out;
		} else {
			int[] out = { x + w, y + h, -w, -h };
			return out;
		}
	}

	/**
	 * Assigns the coordinates of the shape from an int array. This format of int
	 * array is an output of the translate function.
	 * 
	 * @param coords An int array in the format [x, y, w, h]
	 */
	protected void assignCoords(int[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.w = coords[2];
		this.h = coords[3];
		System.out.println(coords[0] + ", " + coords[1] + ", " + coords[2] + ", " + coords[3] + ".");
	}

	/**
	 * Standard draw call for the shapes. To be called every frame.
	 */
	public void draw() {
		this.pApplet.shape(this.shape);

		if (this.isSelected)
			this.highlightVertices();
	}

	/**
	 * A static method that draws a temporary shape with the given characteristics
	 * for one frame. Used to temporarily and dynamically display shapes.
	 * 
	 * @param p     The pApplet object handle
	 * @param x     The X-coordinate of the shape to draw
	 * @param y     The Y-coordinate of the shape to draw
	 * @param w     The width of the shape to draw
	 * @param h     The height of the shape to draw
	 * @param color The color of the shape to draw
	 * @param type  The type of the shape to draw. Currently supported are SQUARE,
	 *              TRIANGLE and ELLIPSE.
	 */
	public static void draw(PApplet p, int x, int y, int w, int h, int color, ShapeType type) {
		p.fill(color);
		switch (type) {
		case SQUARE:
			p.rect(x, y, w, h);
			break;
		case TRIANGLE:
			int[] coords = Shape.translate(x, y, w, h);
			p.triangle(coords[0] + (coords[2] / 2), coords[1], coords[0], coords[1] + coords[3], coords[0] + coords[2],
					coords[1] + coords[3]);
			break;
		case ELLIPSE:
			p.ellipseMode(PApplet.CORNER);
			p.ellipse(x, y, w, h);
			break;
		}
	}

	/**
	 * Moves this shape relatively by a certain amount. Use moveTo() for absolute
	 * position.
	 * 
	 * @param x The amount to change the X-coordinate by.
	 * @param y The amount to change the Y-coordinate by.
	 */
	public void moveBy(int x, int y) {
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
		this.update();
	}

	/**
	 * Moves the shape to an absolute point. Use moveBy() for relative movements.
	 * 
	 * @param x The new X-coordinate.
	 * @param y The new Y-coordinate.
	 */
	public void moveTo(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.update();
	}

	/**
	 * Selects this current shape. This highlights the current shape.
	 */
	public void select() {
		this.isSelected = true;
		pApplet.cursor(PApplet.HAND);
		System.out.println("Shape Selected!");
	}

	/**
	 * Deslects this shape and stops highlighting it.
	 */
	public void deselect() {
		this.isSelected = false;
		pApplet.cursor(PApplet.ARROW);
		System.out.println("Shape Deselected");
	}

	/**
	 * @return True if the current shape is selected.
	 */
	public boolean isSelected() {
		return this.isSelected;
	}

	// Getters and setters

	public String getName() {
		return this.name;
	}

	public void setName(String nameToSet) {
		this.name = nameToSet;
	}

	public ShapeType getType() {
		return this.type;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getH() {
		return this.h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getW() {
		return this.w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getStroke() {
		return this.stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public void setDragging(boolean isDragging) {
		this.isDragging = isDragging;
	}

	public boolean isDragging() {
		return this.isDragging;
	}
}
