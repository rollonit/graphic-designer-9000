import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Canvas class which handles almost all graphical functions.
 *
 * @author rollonit
 * @author klemensfliri
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
	
		// DB 
		

	public Canvas(PApplet pApplet, int x, int y, int h, int w) {
		this.pApplet = pApplet;

		// Canvas bounds
		this.CANVASX = x;
		this.CANVASY = y;
		this.CANVASW = w;
		this.CANVASH = h;

		this.layers = new ArrayList<>();

		ui = new UI(this.pApplet, this);

		this.creatingShape = false;
		this.draggingShape = false;
	}

	public void init() {
		ui.init();
		this.addLayer();
	}

	public void draw() {
		// Application background
		pApplet.background(25);

		// Canvas background
		pApplet.fill(ui.getBackgroundColorValue());
		pApplet.rect(this.CANVASX, this.CANVASY, this.CANVASW, this.CANVASH);

		// UI elements draw call
		ui.draw();

		// Individual elements draw call
		for (Layer layer : layers) {
			if (layer.isVisible())
				layer.draw();
		}

		// Dynamically draw a shape while it's being created
		if (creatingShape) {
			Shape.draw(pApplet, beginX, beginY, -(beginY - pApplet.mouseY), -(beginX - pApplet.mouseX),
					ui.getObjectColor(), ui.getCurrentShapeType());
		}
	}

	public void save() {
		System.out.println("Save Button Triggered!");

		// Check if a shape is selected and the new values are within canvas bounds
		// before saving
		if (this.getCurrentLayer().selectedShape() != null && this.isInCanvas(ui.getObjectX(), ui.getObjectY(),
				this.getCurrentLayer().selectedShape().getH(), this.getCurrentLayer().selectedShape().getW())) {
			this.getCurrentLayer().save(ui.getObjectX(), ui.getObjectY(), ui.getObjectColor(), ui.getObjectName());
		}

	}

	// BUTTONS
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

	public void remove() {
		System.out.println("Remove Button Triggered!");

		// Check if a shape is selected before removing it
		if (this.getCurrentLayer().selectedShape() != null) {
			this.getCurrentLayer().remove();
		}
	}

	public void addLayer() {
		layers.add(new Layer(pApplet));
	}

	public void removeLayer() {
		layers.remove(ui.getCurrentLayerIndex());
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
		if (this.getCurrentLayer().selectedShape() != null && this.getCurrentLayer().selectedShape().isInShape()) {
			this.draggingShape = true;
			this.pApplet.cursor(PApplet.MOVE);
		}

		// Store beginning of left-click drag
		this.beginX = pApplet.mouseX;
		this.beginY = pApplet.mouseY;
	}

	public void endDrag() {
		// Check if the drag is not too small and if the final state of the shape is
		// within the canvas before finalizing move
		if (this.draggingShape && (Math.abs(pApplet.mouseX - this.beginX) + Math.abs(pApplet.mouseY - this.beginY) > 5)
				&& this.isInCanvas(this.getCurrentLayer().selectedShape().getX() + (pApplet.mouseX - beginX),
						this.getCurrentLayer().selectedShape().getY() + (pApplet.mouseY - beginY),
						this.getCurrentLayer().selectedShape().getH(), this.getCurrentLayer().selectedShape().getW())) {
			this.getCurrentLayer().moveBy(pApplet.mouseX - beginX, pApplet.mouseY - beginY);
			this.updateFields();
		} else if (this.ui.isInLayerList(pApplet.mouseX, pApplet.mouseY)) {
			// Handling clicks within the layer box and passing it to the layerlist
			ui.layerClick();
			System.out.println("Mouse was clicked in layerbox!");
		} else {
			// if it's just a click, attempt a normal shape select operation
			this.select();
		}

		// reset cursor
		this.pApplet.cursor(PApplet.ARROW);
		this.draggingShape = false;
	}

	// UTILITY FUNCTIONS
	public void moveLayer(int layerToMove, int whereToMove) {
		if (layerToMove > whereToMove) {
			layers.add(whereToMove, layers.get(layerToMove));
			layers.remove(layerToMove + 1);
		} else if (layerToMove < whereToMove) {
			layers.add(whereToMove, layers.get(layerToMove));
			layers.remove(layerToMove);
		}
	}

	public void select() {
		if (this.isInCanvas(pApplet.mouseX, pApplet.mouseY)) {
			this.getCurrentLayer().select();
			this.updateFields();
		}
	}

	public void updateFields() {
		if (this.getCurrentLayer().selectedShape() != null) {
			ui.setObjectName(this.getCurrentLayer().selectedShape().getName());
			ui.setObjectX(this.getCurrentLayer().selectedShape().getX());
			ui.setObjectY(this.getCurrentLayer().selectedShape().getY());
			ui.setObjectColor(this.getCurrentLayer().selectedShape().getColor());
		} else {
			ui.setObjectName("");
			ui.setObjectX("");
			ui.setObjectY("");
		}
	}

	private boolean isInCanvas(int x, int y) {
		return (x >= CANVASX && x <= (CANVASX + CANVASW)) && (y >= CANVASY && y <= (CANVASY + CANVASH));
	}

	private boolean isInCanvas(int x, int y, int h, int w) {
		return (this.isInCanvas(x, y) && this.isInCanvas(x + h, y + w));
	}

	private Layer getCurrentLayer() {
		return layers.get(ui.getCurrentLayerIndex());
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}
}
