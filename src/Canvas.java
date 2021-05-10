import java.util.ArrayList;

import processing.core.PApplet;

public class Canvas {

	private int CANVASX;
	private int CANVASY;
	private int CANVASW;
	private int CANVASH;

	PApplet pApplet;

	private ArrayList<Layer> layers;
	int layerIndex;

	private int beginX, beginY;
	private boolean creatingShape, draggingShape;

	UI ui;

	public Canvas(PApplet pApplet, int x, int y, int h, int w) {
		this.pApplet = pApplet;

		this.CANVASX = x;
		this.CANVASY = y;
		this.CANVASW = w;
		this.CANVASH = h;

		this.layers = new ArrayList<>();
		this.layerIndex = 0;

		ui = new UI(this.pApplet);

		this.creatingShape = false;
		this.draggingShape = false;
	}

	public void init() {
		ui.init();
		this.addLayer();
	}

	public void draw() {
		pApplet.background(25);

		// placeholder for art board
		pApplet.fill(ui.getBackgroundColorValue());
		pApplet.rect(this.CANVASX, this.CANVASY, this.CANVASW, this.CANVASH);

		ui.draw();

		for (Layer layer : layers) {
			layer.draw();
		}

		if (creatingShape) {
			Shape.draw(pApplet, beginX, beginY, -(beginY - pApplet.mouseY), -(beginX - pApplet.mouseX),
					ui.getObjectColor(), ui.getCurrentShapeType());
		}
	}

	public void save() {
		System.out.println("Save Button Triggered!");
		if (this.getCurrentLayer().selectedShape() != null && this.isInCanvas(ui.getObjectX(), ui.getObjectY(),
				this.getCurrentLayer().selectedShape().getH(), this.getCurrentLayer().selectedShape().getW())) {
			this.getCurrentLayer().save(ui.getObjectX(), ui.getObjectY(), ui.getObjectColor(), ui.getObjectName());
		}

	}

	public void remove() {
		System.out.println("Remove Button Triggered!");
		if (this.getCurrentLayer().selectedShape() != null) {
			this.getCurrentLayer().remove();
		}
	}

	public void add(int beginX, int beginY, int endX, int endY) {
		if (this.isInCanvas(beginX, beginY)) {
			if (this.isInCanvas(endX, endY)) {
				this.getCurrentLayer().addShape(ui.getCurrentShapeType(), beginX, beginY, endX, endY,
						ui.getObjectColor());
			}
		}
	}

	public void addLayer() {
		layers.add(new Layer(pApplet, ui.getLayerList(), layerIndex));
		layerIndex++;
	}

	public void removeLayer() {
		layers.remove(ui.getCurrentLayerIndex());
		layerIndex--;
	}

	private Layer getCurrentLayer() {
		return layers.get(ui.getCurrentLayerIndex());
	}

	public void beginShape() {
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

	public void beginDrag() {
		if (this.getCurrentLayer().selectedShape() != null && this.getCurrentLayer().selectedShape().isInShape()) {
			this.draggingShape = true;
			this.pApplet.cursor(PApplet.MOVE);
		}

		this.beginX = pApplet.mouseX;
		this.beginY = pApplet.mouseY;
	}

	public void endDrag() {
		if (this.draggingShape && (Math.abs(pApplet.mouseX - this.beginX) + Math.abs(pApplet.mouseY - this.beginY) > 5)
				&& this.isInCanvas(this.getCurrentLayer().selectedShape().getX() + (pApplet.mouseX - beginX),
						this.getCurrentLayer().selectedShape().getY() + (pApplet.mouseY - beginY),
						this.getCurrentLayer().selectedShape().getH(), this.getCurrentLayer().selectedShape().getW())) {
			this.getCurrentLayer().moveBy(pApplet.mouseX - beginX, pApplet.mouseY - beginY);
			this.updateFields();
		} else {
			this.select();
		}

		this.pApplet.cursor(PApplet.ARROW);
		this.draggingShape = false;
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
}