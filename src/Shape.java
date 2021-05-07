
//import processing.core.PApplet;
import processing.core.PApplet;
import processing.core.PShape;

public class Shape {

	PShape shape;
	PApplet pApplet;

	int x;
	int y;
	int h;
	int w;

	int color;
	int stroke;
	private boolean isSelected;
	private ShapeType type;

	public Shape(int mouseX, int mouseY, boolean draw, int stroke, int color) {

		this.stroke = color;

	}

	public Shape(PApplet pApplet) {
		this.pApplet = pApplet;
	}

	void setup() {

	}

	public void createSquare() {
		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(0, 0, 255);
		this.shape.setStroke(stroke);
		this.shape.vertex(0, 0);
		this.shape.vertex(0, 50);
		this.shape.vertex(50, 50);
		this.shape.vertex(50, 0);
		this.shape.endShape(PShape.CLOSE);
	}

	public void createSquare(int x, int y, int w, int h, int color) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.color = color;
		type = ShapeType.SQUARE;

		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(x, y);
		this.shape.vertex(x, y + h);
		this.shape.vertex(x + w, y + h);
		this.shape.vertex(x + w, y);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	public void createTriangle() {
		this.shape = pApplet.createShape();
		this.shape.beginShape(PShape.TRIANGLE_STRIP);
		this.shape.setStroke(stroke);
		this.shape.vertex(30, 75);
		this.shape.vertex(40, 20);
		this.shape.vertex(50, 75);
		this.shape.vertex(60, 20);
		this.shape.vertex(70, 75);
		this.shape.vertex(80, 20);
		this.shape.vertex(90, 75);
		this.shape.endShape();
	}

	public void createTriangle(int x, int y, int w, int h, int color) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.color = color;
		type = ShapeType.TRIANGLE;

		this.shape = pApplet.createShape();
		this.shape.beginShape();
		this.shape.fill(color);
		this.shape.vertex(x + (w / 2), y);
		this.shape.vertex(x, y + h);
		this.shape.vertex(x + w, y + h);
		this.shape.endShape(PShape.CLOSE);
		this.shape.setStroke(stroke);
	}

	public void createEllipse() {
		this.shape = pApplet.createShape();
		this.shape.beginShape(PShape.ELLIPSE);
		this.shape.setStroke(stroke);
		this.shape.fill(0, 0, 255);
		this.shape.noStroke();
		this.shape.vertex(0, 0);
		this.shape.vertex(0, 50);
		this.shape.vertex(50, 50);
		this.shape.vertex(50, 0);
	}

	public void createEllipse(int x, int y, int w, int h, int color) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.color = color;
		type = ShapeType.ELLIPSE;

		pApplet.ellipseMode(PApplet.CORNER);
		this.shape = pApplet.createShape(PShape.ELLIPSE, x, y, w, h);
		this.shape.setFill(color);
		this.shape.setStroke(stroke);
	}

	public void draw() {
		this.pApplet.shape(this.shape);
	}

	public static void draw(PApplet p, int x, int y, int h, int w, int color, ShapeType type) {
		p.fill(color);
		switch (type) {
		case SQUARE:
			p.rect(x, y, w, h);
			break;
		case TRIANGLE:
			p.triangle(x + (w / 2), y, x, y + h, x + w, y + h);
			break;
		case ELLIPSE:
			p.ellipseMode(PApplet.CORNER);
			p.ellipse(x, y, w, h);
			break;
		}
	}

	public void select() {
		this.isSelected = true;
		this.shape.setStroke(stroke + 1);
		pApplet.cursor(PApplet.HAND);
		System.out.println("Shape Selected!");
	}

	public void deselect() {
		this.isSelected = false;
		this.shape.setStroke(stroke - 1);
		pApplet.cursor(PApplet.ARROW);
		System.out.println("Shape Deselected");
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

		return (A == A1 + A2 + A3);
	}

	private double area(double x1, double y1, double x2, double y2, double x3, double y3) {
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	private boolean isInSquare() {
		return (pApplet.mouseX >= this.x && pApplet.mouseX <= (this.x + this.w))
				&& (pApplet.mouseY >= this.y && pApplet.mouseY <= (this.y + this.h));
	}
}
