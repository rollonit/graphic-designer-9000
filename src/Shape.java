
//import processing.core.PApplet;
import processing.core.PApplet;
import processing.core.PShape;

public class Shape {

	PShape s;
	PApplet pApplet;

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
		this.s = pApplet.createShape();
		this.s.beginShape();
		this.s.fill(0, 0, 255);
		this.s.setStroke(stroke);
		this.s.vertex(0, 0);
		this.s.vertex(0, 50);
		this.s.vertex(50, 50);
		this.s.vertex(50, 0);
		this.s.endShape(PShape.CLOSE);
	}

	public void createTriangle() {
		this.s = pApplet.createShape();
		this.s.beginShape(PShape.TRIANGLE_STRIP);
		this.s.setStroke(stroke);
		this.s.vertex(30, 75);
		this.s.vertex(40, 20);
		this.s.vertex(50, 75);
		this.s.vertex(60, 20);
		this.s.vertex(70, 75);
		this.s.vertex(80, 20);
		this.s.vertex(90, 75);
		this.s.endShape();
	}

	public void createCircle() {
		this.s = pApplet.createShape();
		this.s.beginShape(PShape.ELLIPSE);
		this.s.setStroke(stroke);
		this.s.fill(0, 0, 255);
		this.s.noStroke();
		this.s.vertex(0, 0);
		this.s.vertex(0, 50);
		this.s.vertex(50, 50);
		this.s.vertex(50, 0);
	}

}
