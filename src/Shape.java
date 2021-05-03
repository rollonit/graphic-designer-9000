
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

	public void draw() {
		this.pApplet.shape(this.shape);
	}

	public static void draw(PApplet p, int x, int y, int h, int w, int color, ShapeType type) {
		p.fill(color);
		switch (type) {
		case SQUARE:
			p.rect(x, y, h, w);
			break;
		case TRIANGLE:
			break;
		case ELLIPSE:
			break;
		}
	}
}
