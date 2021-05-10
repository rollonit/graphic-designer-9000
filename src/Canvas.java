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

	private int shapeX, shapeY;
	private boolean creatingShape;

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
	}

	public void init() {
		ui.init();
		this.addLayer();
	}

	// BUTTON COMMANDS

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
			Shape.draw(pApplet, shapeX, shapeY, -(shapeY - pApplet.mouseY), -(shapeX - pApplet.mouseX),
					ui.getObjectColor(), this.getCurrentShapeType());
		}
	}

	public void save() {
		System.out.println("Save Button Triggered!");
		this.getCurrentLayer().save(ui.getObjectX(), ui.getObjectY(), ui.getObjectColor());

	}

	public void remove() {
		System.out.println("Remove Button Triggered!");
	}

	public void add(int beginX, int beginY, int endX, int endY) {
		if (this.isInCanvas(beginX, beginY)) {
			if (this.isInCanvas(endX, endY)) {
				this.getCurrentLayer().addShape(this.getCurrentShapeType(), beginX, beginY, endX, endY,
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

	public ShapeType getCurrentShapeType() {
		String s = ui.getCurrentShape();
		if (s.equals("Square"))
			return ShapeType.SQUARE;
		else if (s.equals("Triangle"))
			return ShapeType.TRIANGLE;
		else
			return ShapeType.ELLIPSE;
	}

	public void beginShape() {
		shapeX = pApplet.mouseX;
		shapeY = pApplet.mouseY;
		if (this.isInCanvas(shapeX, shapeY)) {
			this.creatingShape = true;
		}
	}

	public void endShape() {
		int finalX = pApplet.mouseX;
		int finalY = pApplet.mouseY;

		System.out.println(
				"Drag action performed!\nBEGIN X:" + shapeX + " Y:" + shapeY + "\nEND X:" + finalX + " Y:" + finalY);
		this.add(shapeX, shapeY, finalX, finalY);
		this.creatingShape = false;
	}

	public void select() {
		if (this.isInCanvas(pApplet.mouseX, pApplet.mouseY)) {
			this.getCurrentLayer().select();
			if (this.getCurrentLayer().selectedShape() != null) {
				ui.setObjectX(this.getCurrentLayer().selectedShape().getX());
				ui.setObjectY(this.getCurrentLayer().selectedShape().getY());
				ui.setObjectColor(this.getCurrentLayer().selectedShape().getColor());
			} else {
				ui.setObjectX("");
				ui.setObjectY("");
			}
		}
	}

	private boolean isInCanvas(int x, int y) {
		return (x >= CANVASX && x <= (CANVASX + CANVASW)) && (y >= CANVASY && y <= (CANVASY + CANVASH));
	}
}
