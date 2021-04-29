import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.PApplet;
import processing.core.PFont;

public class Canvas {

	PApplet pApplet;

	///// UI Stuff//////
	ControlP5 cp5;
	Textfield objectName;
	int UIcolor;
	PFont UIfont;

	public Canvas(PApplet pApplet) {
		this.pApplet = pApplet;

		//// UI Stuff////
		cp5 = new ControlP5(this.pApplet);
		UIcolor = 0xFFFFFFFF;
		UIfont = pApplet.createFont("Arial", 13);
	}

	public void init() {
		objectName =  cp5.addTextfield("nameInput")
							.setPosition(15, 15)
							.setSize(250, 30)
							.setFont(UIfont)
							.setFocus(true)
							.setColor(UIcolor);
	}

	public void draw() {
		cp5.draw();
		pApplet.rect(280, 5, 720, 710);
	}

}
