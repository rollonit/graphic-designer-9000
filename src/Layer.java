import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Class which represents each layer and stores the shapes in that layer.
 */
public class Layer {
	private ArrayList<Shape> shapes;

	PApplet pApplet;
	private boolean isVisible;
	private String name;

	/**
	 * Default constructor for the Layer class.
	 * 
	 * @param pApplet Handle for main PApplet.
	 */
	public Layer(PApplet pApplet, int layerIndex) {
		shapes = new ArrayList<>();
		this.pApplet = pApplet;
		this.isVisible = true;
		this.name = "Layer " + layerIndex;
	}

	/**
	 * A constructor with more parameters for use during a direct DB load.
	 * 
	 * @param pApplet    Handle for main PApplet
	 * @param name       Name of the layer, can be anything.
	 * @param visibility If the layer is visible when saved.
	 */
	public Layer(PApplet pApplet, String name, boolean visibility) {
		shapes = new ArrayList<>();
		this.pApplet = pApplet;
		this.isVisible = visibility;
		this.name = name;
	}

	/**
	 * Adds a new shape to the layer
	 * 
	 * @param type   Type of shape to add (Square, Triangle or Ellipse).
	 * @param beginX X-coordinate of corner 1.
	 * @param beginY Y-coordinate of corner 1.
	 * @param endX   X-coordinate of corner 2.
	 * @param endY   Y-coordinate of corner 2.
	 * @param color  Color of shape to add.
	 */
	public void addShape(ShapeType type, int beginX, int beginY, int endX, int endY, int color) {
		int stroke = 0;
		switch (type) {
		case SQUARE:
			this.shapes.add(new Square(pApplet, beginX, beginY, -(beginX - endX), -(beginY - endY), stroke, color));
			break;
		case TRIANGLE:
			this.shapes.add(new Triangle(pApplet, beginX, beginY, -(beginX - endX), -(beginY - endY), stroke, color));
			break;
		case ELLIPSE:
			this.shapes.add(new Ellipse(pApplet, beginX, beginY, -(beginX - endX), -(beginY - endY), stroke, color));
			break;
		}
	}

	/**
	 * Adds a shape to the layer using more direct parameters.
	 * 
	 * @param x      X- coordinate of the shape.
	 * @param y      Y- coordinate of the shape.
	 * @param w      Width of the shape.
	 * @param h      Height of the shape.
	 * @param stroke The stroke color of the shape.
	 * @param color  Color of the shape.
	 * @param type   Type of shape to be added (Square, Triangle or Ellipse).
	 * @param name   Name of the shape.
	 */
	public void addShape(int x, int y, int w, int h, int stroke, int color, ShapeType type, String name) {
		Shape shape = null;
		switch (type) {
		case SQUARE:
			shape = new Square(pApplet, x, y, w, h, stroke, color);
			break;
		case TRIANGLE:
			shape = new Triangle(pApplet, x, y, w, h, stroke, color);
			break;
		case ELLIPSE:
			shape = new Ellipse(pApplet, x, y, w, h, stroke, color);
			break;
		}
		shape.setName(name);
		this.shapes.add(shape);
	}

	/**
	 * Draw call for this layer. Draws all the shapes.
	 */
	public void draw() {
		for (Shape shape : shapes) {
			shape.draw();
		}
	}

	public void select() {
		boolean somethingSelected = false;
		for (int i = shapes.size() - 1; i >= 0; i--) {
			if (shapes.get(i).isInShape()) {
				this.deselectAll();
				shapes.get(i).select();
				somethingSelected = true;
				break;
			}
		}
		if (!somethingSelected) {
			this.deselectAll();
		}
	}

	public void save(int x, int y, int color, String name) {
		this.selectedShape().setX(x);
		this.selectedShape().setY(y);
		this.selectedShape().setColor(color);
		this.selectedShape().setName(name);
		this.selectedShape().update();
	}

	public void remove() {
		if (this.selectedShape() != null) {
			shapes.remove(this.selectedShape());
		}
	}

	public void moveBy(int x, int y) {
		this.selectedShape().setX(this.selectedShape().getX() + x);
		this.selectedShape().setY(this.selectedShape().getY() + y);
		this.selectedShape().update();
	}

	public void deselectAll() {
		for (Shape shape : shapes) {
			shape.deselect();
		}
	}

	public Shape selectedShape() {
		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				return shape;
			}
		}
		return null;
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void toggleVisibility() {
		this.isVisible = !this.isVisible;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String nameToSet) {
		this.name = nameToSet;
	}

	public ArrayList<Shape> getShapes() {
		return this.shapes;
	}

	public Shape getShape(int index) {
		if (index >= 0 && index < shapes.size()) {
			return shapes.get(index);
		} else {
			return null;
		}
	}
}
