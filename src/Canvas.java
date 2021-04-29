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

	PApplet pApplet;

	ArrayList<Layer> layers;
	int layerIndex;

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

	public Canvas(PApplet pApplet) {
		this.pApplet = pApplet;

		layers = new ArrayList<>();
		layerIndex = 0;

		//// UI Stuff////
		cp5 = new ControlP5(this.pApplet);
		UIcolor = 0xFFFFFFFF;
		UIinputFont = pApplet.createFont("Arial", 15);
		UIheadFont = pApplet.createFont("Arial", 18);
		UIobjPropsLevel = 100;
		UIobjButtonLevel = 350;
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
		objType.addItem("Rectangle", "Rectangle");
		objType.addItem("Triangle", "Triangle");
		objType.addItem("Eclipse", "Eclipse");
		
		// Object Buttons
		cp5.addButton("save")
			.setPosition(15, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);
		
		cp5.addButton("add")
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
		cp5.draw();
		
		//placeholder for art board
		pApplet.fill(backColorPicker.getColorValue());
		pApplet.rect(280, 5, 720, 710);
	}
	
	public void save() {
		System.out.println("Save Button Triggered!");
		
	}
	
	public void add() {
		System.out.println("Add Button Triggered");
		layers.get(this.getCurrentLayerIndex()).addShape();
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
		String curSel = ((HashMap) (layerList.getItem((int) (layerList.getValue())))).get("value").toString();
		int index = Integer.parseInt(String.valueOf(curSel.charAt(curSel.length() - 1)));
		System.out.println("String: " + curSel + "\nIndex:" + index);
		return index;
	}

}
