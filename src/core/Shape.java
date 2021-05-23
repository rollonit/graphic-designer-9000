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

	public Shape(PApplet pApplet) {
		this.pApplet = pApplet;
		this.name = "";
	}

	public abstract void create(int x, int y, int w, int h, int stroke, int color);

	public abstract void update();

	public abstract boolean isInShape();

	public abstract void highlightVertices();

	protected static int[] translate(int x, int y, int w, int h) {
		if (h >= 0 && w >= 0) {
			int[] out = { x, y, w, h };
			return out;
		} else if (h >= 0 && w < 0) {
			int[] out = { x + w, y, x, -h };
			return out;
		} else if (h < 0 && w >= 0) {
			int[] out = { x, y + h, -w, y };
			return out;
		} else {
			int[] out = { x + w, y + h, -w, -h };
			return out;
		}
	}

	protected void assignCoords(int[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.w = coords[2];
		this.h = coords[3];
		System.out.println(coords[0] + ", " + coords[1] + ", " + coords[2] + ", " + coords[3] + ".");
	}

	public void draw() {
		this.pApplet.shape(this.shape);

		if (this.isSelected)
			this.highlightVertices();
	}

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

	public void select() {
		this.isSelected = true;
		pApplet.cursor(PApplet.HAND);
		System.out.println("Shape Selected!");
	}

	public void deselect() {
		this.isSelected = false;
		pApplet.cursor(PApplet.ARROW);
		System.out.println("Shape Deselected");
	}

	public boolean isSelected() {
		return this.isSelected;
	}

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
