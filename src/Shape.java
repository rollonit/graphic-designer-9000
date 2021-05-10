
//import processing.core.PApplet;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Shape {

	private PShape shape;
	private PApplet pApplet;

	private String name;

	private int x;
	private int y;
	private int h;
	private int w;

	private int color;
	private int stroke;
	private boolean isSelected;
	private ShapeType type;

	public Shape(PApplet pApplet) {
		this.pApplet = pApplet;
		this.name = "";
	}

	void setup() {
	}

	private static int[] translate(int x, int y, int w, int h) {
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

	private void assignCoords(int[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.w = coords[2];
		this.h = coords[3];
		System.out.println(coords[0] + ", " + coords[1] + ", " + coords[2] + ", " + coords[3] + ".");
	}

	public void createSquare(int x, int y, int w, int h, int color) {
		this.assignCoords(Shape.translate(x, y, w, h));
		this.color = color;
		type = ShapeType.SQUARE;

		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x, this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	private void updateSquare() {
		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x, this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	public void createTriangle(int x, int y, int w, int h, int color) {
		this.assignCoords(Shape.translate(x, y, w, h));
		this.color = color;
		type = ShapeType.TRIANGLE;

		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x + (this.w / 2), this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	private void updateTriangle() {
		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(this.x + (this.w / 2), this.y);
		this.shape.vertex(this.x, this.y + this.h);
		this.shape.vertex(this.x + this.w, this.y + this.h);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	public void createEllipse(int x, int y, int w, int h, int color) {
		this.assignCoords(Shape.translate(x, y, w, h));
		this.color = color;
		type = ShapeType.ELLIPSE;

		pApplet.ellipseMode(PApplet.CORNER);
		this.shape = pApplet.createShape(PShape.ELLIPSE, this.x, this.y, this.w, this.h);
		this.shape.setFill(color);
		this.shape.setStroke(stroke);
	}

	private void updateEllipse() {
		pApplet.ellipseMode(PApplet.CORNER);
		this.shape = pApplet.createShape(PShape.ELLIPSE, this.x, this.y, this.w, this.h);
		this.shape.setFill(color);
		this.shape.setStroke(stroke);
	}

	public void draw() {
		this.pApplet.shape(this.shape);

		if (this.isSelected)
			this.highlightVertices();
	}

	public static void draw(PApplet p, int x, int y, int h, int w, int color, ShapeType type) {
		p.fill(color);
		switch (type) {
		case SQUARE:
			p.rect(x, y, w, h);
			break;
		case TRIANGLE:
			int[] coords = Shape.translate(x, y, w, h);
			p.triangle(coords[0] + (coords[2] / 2), coords[1], coords[0], coords[1] + coords[3], coords[0] + coords[2], coords[1] + coords[3]);
			break;
		case ELLIPSE:
			p.ellipseMode(PApplet.CORNER);
			p.ellipse(x, y, w, h);
			break;
		}
	}

	public void select() {
		this.isSelected = true;
		// this.shape.setStroke(stroke + 1);
		pApplet.cursor(PApplet.HAND);
		System.out.println("Shape Selected!");
	}

	public void deselect() {
		this.isSelected = false;
		// this.shape.setStroke(stroke - 1);
		pApplet.cursor(PApplet.ARROW);
		System.out.println("Shape Deselected");
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	private void highlightVertices() {
		switch (this.type) {
		case SQUARE:
		case TRIANGLE:
			for (int i = 0; i < this.shape.getVertexCount(); i++) {
				PVector v = this.shape.getVertex(i);
				// this.pApplet.noStroke();
				this.pApplet.fill(0xffff0000);
				this.pApplet.ellipseMode(PApplet.CENTER);
				this.pApplet.ellipse(v.x, v.y, 7, 7);
			}
			break;
		case ELLIPSE:
			this.pApplet.fill(0xffff0000);
			this.pApplet.ellipseMode(PApplet.CENTER);
			this.pApplet.ellipse(this.x, this.y, 7, 7);
			this.pApplet.ellipse(this.x, this.y + this.h, 7, 7);
			this.pApplet.ellipse(this.x + this.w, this.y, 7, 7);
			this.pApplet.ellipse(this.x + this.w, this.y + this.h, 7, 7);
		}
	}

	public boolean isInShape() {
		/*
		 * if(this.shape.contains(pApplet.mouseX, pApplet.mouseY)) return true; return
		 * false;
		 */
		switch (this.type) {
		case SQUARE:
			return this.isInSquare();
		case TRIANGLE:
			return this.isInTriangle();
		case ELLIPSE:
			return this.isInEllipse();
		}
		return false;
	}

	private boolean isInEllipse() {
		double centerX = this.x + this.w / 2;
		double centerY = this.y + this.h / 2;
		double det = (Math.pow((pApplet.mouseX - centerX), 2) / Math.pow(w / 2, 2))
				+ (Math.pow((pApplet.mouseY - centerY), 2) / Math.pow(h / 2, 2));
		System.out.println("det=" + det);
		return (det <= 1);
	}

	private boolean isInTriangle() {
		double x1 = this.x + this.w / 2;
		double y1 = this.y;
		double x2 = this.x;
		double y2 = this.y + this.h;
		double x3 = this.x + this.w;
		double y3 = this.y + this.h;
		double x = pApplet.mouseX;
		double y = pApplet.mouseY;
		double A = area(x1, y1, x2, y2, x3, y3);
		double A1 = area(x, y, x2, y2, x3, y3);
		double A2 = area(x1, y1, x, y, x3, y3);
		double A3 = area(x1, y1, x2, y2, x, y);

		return (Math.abs(A - (A1 + A2 + A3)) < 0.00001);
	}

	private double area(double x1, double y1, double x2, double y2, double x3, double y3) {
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	private boolean isInSquare() {
		return (pApplet.mouseX >= this.x && pApplet.mouseX <= (this.x + this.w))
				&& (pApplet.mouseY >= this.y && pApplet.mouseY <= (this.y + this.h));
	}

	public void update() {
		switch (this.type) {
		case SQUARE:
			this.updateSquare();
			break;
		case TRIANGLE:
			this.updateTriangle();
			break;
		case ELLIPSE:
			this.updateEllipse();
			break;
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String nameToSet) {
		this.name = nameToSet;
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
}
