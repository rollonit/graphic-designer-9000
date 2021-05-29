package core;

import java.util.ArrayList;

import processing.core.PApplet;
import ui.UI;

/**
 * Canvas class for the graphics designer which handles almost all graphical and
 * logical functions.
 *
 * @author rollonit
 * @author Klemens Fliri
 * @version 1
 */
public class Canvas {

	private int CANVASX;
	private int CANVASY;
	private int CANVASW;
	private int CANVASH;

	PApplet pApplet;

	private ArrayList<Layer> layers;

	private int beginX, beginY;
	private boolean creatingShape, draggingShape;

	UI ui;
	DB db;
	private int highestLayer;

	/**
	 * Default constructor for the canvas class.
	 * 
	 * @param pApplet The main pApplet handle.
	 * @param x       The X coordinate of the top-left corner of the canvas.
	 * @param y       The Y coordinate of the top-left corner of the canvas.
	 * @param h       Height of the canvas.
	 * @param w       Width of the canvas.
	 * 
	 */
	public Canvas(PApplet pApplet, int x, int y, int h, int w) {
		this.pApplet = pApplet;

		// Canvas bounds
		this.CANVASX = x;
		this.CANVASY = y;
		this.CANVASW = w;
		this.CANVASH = h;

		this.layers = new ArrayList<>();

		ui = new UI(this.pApplet, this);
		this.db = new DB(this.pApplet, this);

		this.creatingShape = false;
		this.draggingShape = false;

		this.highestLayer = 1;
	}

	/**
	 * Initializer for the Canvas class.
	 */
	public void init() {
		ui.init();
		this.addLayer();
	}

	/**
	 * Draw call for the canvas. Call this every frame.
	 */
	public void draw() {
		// application background
		pApplet.background(25);

		// canvas background
		pApplet.fill(ui.getBackgroundColorValue());
		pApplet.rect(this.CANVASX, this.CANVASY, this.CANVASW, this.CANVASH);

		// UI elements draw call
		ui.draw();

		// individual elements draw call
		for (Layer layer : layers) {
			if (layer.isVisible())
				layer.draw();
		}

		// dynamically draw a shape while it's being created
		if (this.creatingShape) {
			Shape.draw(pApplet, this.beginX, this.beginY,
					PApplet.constrain(-(this.beginX - this.pApplet.mouseX), (this.CANVASX - this.beginX),
							(this.CANVASX + this.CANVASW - this.beginX)),
					PApplet.constrain(-(this.beginY - this.pApplet.mouseY), (this.CANVASY - this.beginY),
							(this.CANVASY + this.CANVASH - this.beginY)),
					ui.getObjectColor(), ui.getCurrentShapeType());
		}

		// dynamically draw a shape while it's being dragged
		if (this.draggingShape) {
			Shape cur = this.getCurrentShape();
			Shape.draw(pApplet,
					PApplet.constrain(cur.getX() - (beginX - pApplet.mouseX), this.CANVASX,
							this.CANVASX + this.CANVASW - cur.getW()),
					PApplet.constrain(cur.getY() - (beginY - pApplet.mouseY), this.CANVASY,
							this.CANVASY + this.CANVASH - cur.getH()),
					cur.getW(), cur.getH(), cur.getColor(), cur.getType());
		}
	}

	// BUTTONS
	/**
	 * Save the edits to the values in the text boxes and color selectors.
	 */
	public void save() {
		System.out.println("Save Button Triggered!");

		// check if a shape is selected and the new values are within canvas bounds
		// before saving. This process also offsets the X and Y at every step to account
		// for the starting point of the canvas itself, in relation to the global
		// coordinates.
		if (this.getCurrentShape() != null && this.isInCanvas(ui.getObjectX() + this.CANVASX,
				ui.getObjectY() + this.CANVASY, this.getCurrentShape().getH(), this.getCurrentShape().getW())) {
			this.getCurrentLayer().save(ui.getObjectX() + this.CANVASX, ui.getObjectY() + this.CANVASY, ui.getObjectW(),
					ui.getObjectH(), ui.getObjectColor(), ui.getObjectName());
		}

	}

	/**
	 * Removes the currently selected shape from the current layer. Does nothing if
	 * no shape is selected.
	 */
	public void remove() {
		System.out.println("Remove Button Triggered!");

		// Check if a shape is selected before removing it
		if (this.getCurrentShape() != null) {
			this.getCurrentLayer().remove();
		}
	}

	public void addLayer() {
		layers.add(new Layer(pApplet, this.highestLayer++));
	}

	/**
	 * Custom add methods to add shapes directly, parametrically.
	 * 
	 * @param name       Name of the layer, can be anything.
	 * @param visibility Is the layer is visible or not.
	 */
	public void addLayer(String name, boolean visibility) {
		layers.add(new Layer(pApplet, name, visibility));
	}

	public void removeSelectedLayer() {
		ui.removeSelectedLayer();
	}

	public void removeLayer(int index) {
		layers.remove(index);
	}

	// SHAPE CREATION FUNTIONS
	public void beginShape() {

		// Store beginning of drag and start shape creation if the start is in the
		// canvas
		beginX = pApplet.mouseX;
		beginY = pApplet.mouseY;
		if (this.isInCanvas(beginX, beginY)) {
			this.creatingShape = true;
		}
	}

	public void endShape() {
		int finalX = pApplet.mouseX;
		int finalY = pApplet.mouseY;

		System.out.println(
				"Drag action performed!\nBEGIN X:" + beginX + " Y:" + beginY + "\nEND X:" + finalX + " Y:" + finalY);
		this.add(beginX, beginY, finalX, finalY);
		this.creatingShape = false;
	}

	// SHAPE MOVING FUNCTIONS
	public void beginDrag() {
		// Check if left-click drag is in a shape and that shape is selected, and start
		// shape move
		if (this.getCurrentShape() != null && this.getCurrentShape().isInShape()) {
			this.draggingShape = true;
			this.getCurrentShape().setDragging(true);
			this.pApplet.cursor(PApplet.MOVE);
		}

		// Store beginning of left-click drag
		this.beginX = pApplet.mouseX;
		this.beginY = pApplet.mouseY;
	}

	public void endDrag() {
		// Check if the drag is not too small and if the final state of the shape is
		// within the canvas before finalizing move
		if (this.draggingShape
				&& (Math.abs(pApplet.mouseX - this.beginX) + Math.abs(pApplet.mouseY - this.beginY) > 5)) {
			this.getCurrentShape()
					.moveTo(PApplet.constrain(this.getCurrentShape().getX() - (this.beginX - this.pApplet.mouseX),
							this.CANVASX, this.CANVASX + this.CANVASW - this.getCurrentShape().getW()),
							PApplet.constrain(this.getCurrentShape().getY() - (this.beginY - this.pApplet.mouseY),
									this.CANVASY, this.CANVASY + this.CANVASH - this.getCurrentShape().getH()));
			this.updateFields();
		} else if (this.ui.isInLayerList(pApplet.mouseX, pApplet.mouseY)) {
			// Handling clicks within the layer box and passing it to the layer list.
			ui.layerClick();
			System.out.println("Mouse was clicked in layerbox!");
		} else {
			// if it's just a click, attempt a normal shape select operation
			this.select();
		}

		// reset cursor
		this.pApplet.cursor(PApplet.ARROW);
		this.deMoveAllShapes();
		this.draggingShape = false;
	}

	// FILE OPERATIONS

	public void loadFile() {
		db.read();
	}

	public void saveFile() {
		db.write();
	}

	// UTILITY FUNCTIONS
	/**
	 * Add a shape to the currently selected layer.
	 * 
	 * @param beginX X-axis of corner 1.
	 * @param beginY Y-axis of corner 1.
	 * @param endX   X-axis of corner 2.
	 * @param endY   Y-axis of corner 2.
	 */
	public void add(int beginX, int beginY, int endX, int endY) {
		// Check if the starting and ending points of drag are within canvas before
		// creating shape
		if (this.isInCanvas(beginX, beginY)) {
			if (this.isInCanvas(endX, endY)) {
				this.getCurrentLayer().addShape(ui.getCurrentShapeType(), beginX, beginY, endX, endY,
						ui.getObjectColor());
			}
		}
	}

	/**
	 * A function that sets all the shapes in the project to not being dragged at
	 * the current time. It's kind of a janky solution, but it works.
	 */
	private void deMoveAllShapes() {
		for (Layer layer : layers) {
			for (Shape shape : layer.getShapes()) {
				shape.setDragging(false);
			}
		}
	}

	/**
	 * Adds a shape parametrically directly to a specific layer, to be used with DB
	 * operations.
	 * 
	 * @param layerToAdd Layer index to add the shape to.
	 * @param type       Type of shape to be added (Square, Triangle or Ellipse).
	 * @param name       Name of the shape.
	 * @param x          X- coordinate of the shape.
	 * @param y          Y- coordinate of the shape.
	 * @param w          Width of the shape.
	 * @param h          Height of the shape.
	 * @param stroke     The stroke color of the shape.
	 * @param color      Color of the shape.
	 * 
	 */
	public void addToLayer(int layerToAdd, ShapeType type, String name, int x, int y, int w, int h, int stroke,
			int color) {
		this.layers.get(layerToAdd).addShape(x, y, w, h, stroke, color, type, name);
	}

	/**
	 * Moves a given layer in the canvas to a given position.
	 * 
	 * @param layerToMove The index of the layer to be moved.
	 * @param whereToMove the index to which the layer is to be moved.
	 * @return 1 if the move operation is succesful, 0 otherwise
	 */
	public int moveLayer(int layerToMove, int whereToMove) {
		if ((layerToMove < 0 || layerToMove >= layers.size()) || (whereToMove < 0 || whereToMove >= layers.size())) {
			System.out.println("Invalid move operation!");
			return 0;
		}
		if (layerToMove > whereToMove || layerToMove < whereToMove) {
			Layer temp = this.getLayer(layerToMove);
			layers.remove(layerToMove);
			layers.add(whereToMove, temp);
			return 1;
		} else {
			System.out.println("They're the same layer.");
			return 0;
		}
	}

	/**
	 * Deletes all the layers in the current canvas. Use with care!
	 */
	public void deleteAllLayers() {
		layers.removeAll(layers);
	}

	public void select() {
		if (this.isInCanvas(pApplet.mouseX, pApplet.mouseY)) {
			this.getCurrentLayer().select();
			this.updateFields();
		}
	}

	public void updateFields() {
		if (this.getCurrentShape() != null) {

			// Sets the fields for the object properties, off-setting the X and the Y to
			// account for the canvas position.
			ui.setObjectName(this.getCurrentShape().getName());
			ui.setObjectX(this.getCurrentShape().getX() - this.CANVASX);
			ui.setObjectY(this.getCurrentShape().getY() - this.CANVASY);
			ui.setObjectW(this.getCurrentShape().getW());
			ui.setObjectH(this.getCurrentShape().getH());
			ui.setObjectColor(this.getCurrentShape().getColor());
		} else {
			ui.setObjectName("");
			ui.setObjectX("");
			ui.setObjectY("");
			ui.setObjectW("");
			ui.setObjectH("");
		}
	}

	/**
	 * Checks if the given X and Y coordinates are within the canvas.
	 * 
	 * @param X X-axis of the point to check.
	 * @param Y Y-axis of the point to check.
	 * @return True if the given point is within canvas.
	 */
	private boolean isInCanvas(int x, int y) {
		return (x >= CANVASX && x <= (CANVASX + CANVASW)) && (y >= CANVASY && y <= (CANVASY + CANVASH));
	}

	/**
	 * Checks if a shape with the given properties is within the canvas.
	 * 
	 * @param x X-axis of the corner of the shape.
	 * @param y Y-axis of the corner of the shape.
	 * @param h Height of the shape.
	 * @param w Width of the shape.
	 * @return True if the given shape is within the canvas.
	 */
	private boolean isInCanvas(int x, int y, int h, int w) {
		return (this.isInCanvas(x, y) && this.isInCanvas(x + h, y + w));
	}

	private Layer getCurrentLayer() {
		return layers.get(ui.getCurrentLayerIndex());
	}

	private Shape getCurrentShape() {
		return this.getCurrentLayer().selectedShape();
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}

	public Layer getLayer(int index) {
		if (index >= 0 && index < layers.size()) {
			return layers.get(index);
		} else {
			return null;
		}
	}
}
