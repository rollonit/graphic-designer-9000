package core;

import java.util.ArrayList;

import processing.core.PApplet;
import shapes.Ellipse;
import shapes.Square;
import shapes.Triangle;

/**
 * Class which represents each layer and stores the shapes in that layer.
 */
public class Layer {
	private ArrayList<Shape> shapes;

	private PApplet pApplet;
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
			if (!shape.isDragging()) {
				shape.draw();
			}
		}
	}

	/**
	 * <p>
	 * Performs a select operation, which selects the topmost layer under the
	 * mouse's current location in the currently selected layer, and then de-selects
	 * every other shape in that layer.
	 * </p>
	 * <p>
	 * If there is no shape from the current layer under the mouse, it de-selects
	 * everything in the current layer.
	 * </p>
	 */
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

	/**
	 * Updates the currently selected shape with the parameneter provided.
	 * 
	 * @param x     The X coordinate of the corner
	 * @param y     The Y coordinate of the corner
	 * @param w     The width of the shape
	 * @param h     The height of the shape
	 * @param color The color of the shape
	 * @param name  The name of the shape
	 */
	public void save(int x, int y, int w, int h, int color, String name) {
		this.selectedShape().setX(x);
		this.selectedShape().setY(y);
		this.selectedShape().setW(w);
		this.selectedShape().setH(h);
		this.selectedShape().setColor(color);
		this.selectedShape().setName(name);
		this.selectedShape().update();
	}

	/**
	 * Removes the currently selected shape if one is selected.
	 */
	public void remove() {
		if (this.selectedShape() != null) {
			shapes.remove(this.selectedShape());
		}
	}

	/**
	 * Deselects all the shapes.
	 */
	public void deselectAll() {
		for (Shape shape : shapes) {
			shape.deselect();
		}
	}

	/**
	 * Get the currently selected shape.
	 * 
	 * @return The currently selected shape as a Shape object.
	 */
	public Shape selectedShape() {
		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				return shape;
			}
		}
		return null;
	}

	/**
	 * @return True if this layer is visible.
	 */
	public boolean isVisible() {
		return this.isVisible;
	}

	/**
	 * Toggles the visiblity of the current layer.
	 */
	public void toggleVisibility() {
		this.isVisible = !this.isVisible;
	}

	/**
	 * @return The name of the current layer.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param nameToSet The new name to set for the current layer.
	 */
	public void setName(String nameToSet) {
		this.name = nameToSet;
	}

	/**
	 * @return All the shapes in the layer in an ArrayList.
	 */
	public ArrayList<Shape> getShapes() {
		return this.shapes;
	}

	/**
	 * Gets a specific shape from this layer.
	 * 
	 * @param index Index of the shape to get.
	 * @return The shape at that index.
	 */
	public Shape getShape(int index) {
		if (index >= 0 && index < shapes.size()) {
			return shapes.get(index);
		} else {
			return null;
		}
	}
}
