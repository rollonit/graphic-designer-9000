import java.util.ArrayList;
import java.util.HashMap;

import controlP5.ColorPicker;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.Textfield;
import controlP5.Textlabel;

import processing.core.PApplet;
import processing.core.PFont;

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

	///// UI Stuff//////
	ControlP5 cp5;
	Textlabel objProps, backProps, layerHead;
	Textfield objectName;
	Textfield objX, objY, objRot;
	ColorPicker objColorPicker, backColorPicker;
	DropdownList objType, layerList;

	int UIcolor;
	PFont UIinputFont, UIheadFont;
	int UIobjPropsLevel, UIobjButtonLevel;

	public Canvas(PApplet pApplet, int x, int y, int h, int w) {
		this.pApplet = pApplet;

		this.CANVASX = x;
		this.CANVASY = y;
		this.CANVASW = w;
		this.CANVASH = h;

		this.layers = new ArrayList<>();
		this.layerIndex = 0;

		//// UI Stuff////
		cp5 = new ControlP5(this.pApplet);
		UIcolor = 0xFFFFFFFF;
		UIinputFont = pApplet.createFont("Arial", 15);
		UIheadFont = pApplet.createFont("Arial", 18);
		UIobjPropsLevel = 100;
		UIobjButtonLevel = 350;
		
		this.creatingShape = false;
	}

	public void init() {

		/////// UI STUFFF ///////
		pApplet.background(25);
		
		objProps = cp5.addLabel("objProbs")
						.setPosition(15, 15)
						.setText("Object Properties")
						.setSize(250, 30)
						.setFont(UIheadFont)
						.setColor(UIcolor);
		
		objectName =  cp5.addTextfield("objectName")
							.setPosition(15, 50)
							.setSize(250, 30)
							.setFont(UIinputFont)
							.setFocus(true)
							.setColor(UIcolor);
		
		// Object properties boxes		
		objX = cp5.addTextfield("X")
					.setPosition(15, UIobjPropsLevel)
					.setSize(80, 30)
					.setFont(UIinputFont)
					.setColor(UIcolor);
		
		objY = cp5.addTextfield("Y")
					.setPosition(100, UIobjPropsLevel)
					.setSize(80, 30)
					.setFont(UIinputFont)
					.setColor(UIcolor);
		
		objRot = cp5.addTextfield("ROT")
					.setPosition(185, UIobjPropsLevel)
					.setSize(80, 30)
					.setFont(UIinputFont)
					.setColor(UIcolor);
		
		//Object color picker
		objColorPicker = cp5.addColorPicker("OBJ Color")
							.setPosition(15, 150)
							.setSize(250, 550);
		
		//object type DDList
		objType = cp5.addDropdownList("ObjectType")
						.setPosition(15, 220)
						.setSize(250, 120)
						.setBarHeight(25)
						.setItemHeight(30);
		objType.addItem("Square", "Square");
		objType.addItem("Triangle", "Triangle");
		objType.addItem("Ellipse", "Ellipse");
		
		// Object Buttons
		cp5.addButton("save")
			.setPosition(15, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);
		
		cp5.addButton("remove")
			.setPosition(145, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);
		
		// Background properties
		backProps = cp5.addLabel("backProbs")
				.setPosition(15, 600)
				.setText("Background Properties")
				.setSize(250, 30)
				.setFont(UIheadFont)
				.setColor(UIcolor);

		//Background color selector
		backColorPicker = cp5.addColorPicker("Background Color")
							.setPosition(15, 635)
							.setSize(250, 550);
		
		layerHead = backProps = cp5.addLabel("layerHead")
				.setPosition(1015, 15)
				.setText("Layers")
				.setSize(250, 30)
				.setFont(UIheadFont)
				.setColor(UIcolor);
		
		layerList = cp5.addDropdownList("layerList")
				.setPosition(1015, 50)
				.setSize(250, 120)
				.setBarHeight(50)
				.setItemHeight(50);
		
		cp5.addButton("addLayer")
			.setPosition(1015, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);
	
		cp5.addButton("removeLayer")
			.setPosition(1145, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);

		/////// END UI STUFF ////////

		this.addLayer();

	}

	public void draw() {
		pApplet.background(25);

		// placeholder for art board
		pApplet.fill(backColorPicker.getColorValue());
		pApplet.rect(this.CANVASX, this.CANVASY, this.CANVASW, this.CANVASH);

		cp5.draw();

		for (Layer layer : layers) {
			layer.draw();
		}

		if (creatingShape) {
			Shape.draw(pApplet, shapeX, shapeY, -(shapeY - pApplet.mouseY), -(shapeX - pApplet.mouseX),
					this.objColorPicker.getColorValue(), this.getCurrentShapeType());
		}
	}

	public void save() {
		System.out.println("Save Button Triggered!");

	}

	public void remove() {
		System.out.println("Remove Button Triggered!");
	}

	public void add(int beginX, int beginY, int endX, int endY) {
		if (this.isInCanvas(beginX, beginY)) {
			if (this.isInCanvas(endX, endY)) {
				layers.get(this.getCurrentLayerIndex()).addShape(this.getCurrentShapeType(), beginX, beginY, endX, endY,
						objColorPicker.getColorValue());
			}
		}
	}

	public void addLayer() {
		layers.add(new Layer(pApplet, layerList, layerIndex));
		layerIndex++;
	}

	public void removeLayer() {
		layers.remove(this.getCurrentLayerIndex());
		layerIndex--;
	}

	public int getCurrentLayerIndex() {
		String curSel = ((HashMap<String, Object>) (layerList.getItem((int) (layerList.getValue())))).get("value")
				.toString();
		int index = Integer.parseInt(String.valueOf(curSel.charAt(curSel.length() - 1)));
		System.out.println("String: " + curSel + "\nIndex:" + index);
		return index;
	}

	private String getCurrentShape() {
		return ((HashMap<String, Object>) (objType.getItem((int) (objType.getValue())))).get("value").toString();
	}

	public ShapeType getCurrentShapeType() {
		String s = getCurrentShape();
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
		layers.get(this.getCurrentLayerIndex()).select();
	}

	private boolean isInCanvas(int x, int y) {
		if (x >= CANVASX && x <= (CANVASX + CANVASW)) {
			if (y >= CANVASY && y <= (CANVASY + CANVASH))
				return true;
		}
		return false;
	}

}
