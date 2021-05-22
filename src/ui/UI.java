package ui;

import controlP5.ColorPicker;
import controlP5.ControlP5;
import controlP5.DropdownList;
import controlP5.Textfield;
import controlP5.Textlabel;
import core.Canvas;
import core.ShapeType;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.HashMap;

public class UI {
	PApplet pApplet;

	ControlP5 cp5;
	Textlabel objProps, backProps, layerHead;
	Textfield objectName, fileName;
	Textfield objX, objY, objW, objH;
	ColorPicker objColorPicker, backColorPicker;
	DropdownList objType;

	LayerList layerlist;

	int UIcolor;
	PFont UIinputFont, UIheadFont;
	int UIobjPropsLevel, UIobjButtonLevel;

	public UI(PApplet pApplet, Canvas canvas) {
		this.pApplet = pApplet;
		layerlist = new LayerList(pApplet, canvas, 1015, 50, 250, 280);
		cp5 = new ControlP5(this.pApplet);
		UIcolor = 0xFFFFFFFF;
		UIinputFont = pApplet.createFont("Arial", 15);
		UIheadFont = pApplet.createFont("Arial", 18);
		UIobjPropsLevel = 100;
		UIobjButtonLevel = 350;
	}

	public void init() {
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
					.setSize(58, 30)
					.setFont(UIinputFont)
					.setColor(UIcolor);
		
		objY = cp5.addTextfield("Y")
					.setPosition(79, UIobjPropsLevel)
					.setSize(58, 30)
					.setFont(UIinputFont)
					.setColor(UIcolor);
		
		objW = cp5.addTextfield("W")
					.setPosition(143, UIobjPropsLevel)
					.setSize(58, 30)
					.setFont(UIinputFont)
					.setColor(UIcolor);

		objH = cp5.addTextfield("H")
					.setPosition(207, UIobjPropsLevel)
					.setSize(58, 30)
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
		
		// Layer controls
		layerHead = backProps = cp5.addLabel("layerHead")
				.setPosition(1015, 15)
				.setText("Layers")
				.setSize(250, 30)
				.setFont(UIheadFont)
				.setColor(UIcolor);
		
		cp5.addButton("addLayer")
			.setPosition(1015, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);
	
		cp5.addButton("removeLayer")
			.setPosition(1145, UIobjButtonLevel)
			.setSize(120, 30)
			.setColorValue(UIcolor);

		// File operations controls
		fileName = cp5.addTextfield("fileName")
						.setPosition(1015, 580)
						.setSize(250, 30)
						.setFont(UIinputFont)
						.setFocus(true)
						.setColor(UIcolor);

		cp5.addButton("loadFile")
			.setPosition(1015, 635)
			.setSize(120, 30)
			.setColorValue(UIcolor);
		
		cp5.addButton("saveFile")
			.setPosition(1145, 635)
			.setSize(120, 30)
			.setColorValue(UIcolor);
	}

	public void draw() {
		cp5.draw();
		layerlist.draw();
	}

	public boolean isInLayerList(int x, int y) {
		return layerlist.isInList(x, y);
	}

	public void layerClick() {
		layerlist.click();
	}

	public String getObjectName() {
		return this.objectName.getText();
	}

	public void setObjectName(String textToSet) {
		this.objectName.setText(textToSet);
	}

	// Object x setters and getters
	public int getObjectX() {
		return Integer.parseInt(this.objX.getText());
	}

	public void setObjectX(String textToSet) {
		this.objX.setText(textToSet);
	}

	public void setObjectX(int valueToSet) {
		this.objX.setText(String.valueOf(valueToSet));
	}

	// Object Y setters and getters
	public int getObjectY() {
		return Integer.parseInt(this.objY.getText());
	}

	public void setObjectY(String textToSet) {
		this.objY.setText(textToSet);
	}

	public void setObjectY(int valueToSet) {
		this.objY.setText(String.valueOf(valueToSet));
	}

	// Object width setters and getters
	public int getObjectW() {
		return Integer.parseInt(this.objW.getText());
	}

	public void setObjectW(String valueToSet) {
		this.objW.setText(String.valueOf(valueToSet));
	}

	public void setObjectW(int valueToSet) {
		this.objW.setText(String.valueOf(valueToSet));
	}

	// Object height setters and getters
	public int getObjectH() {
		return Integer.parseInt(this.objH.getText());
	}

	public void setObjectH(String valueToSet) {
		this.objH.setText(String.valueOf(valueToSet));
	}

	public void setObjectH(int valueToSet) {
		this.objH.setText(String.valueOf(valueToSet));
	}

	public int getObjectColor() {
		return objColorPicker.getColorValue();
	}

	public void setObjectColor(int colorValueToSet) {
		this.objColorPicker.setColorValue(colorValueToSet);
	}

	public int getBackgroundColorValue() {
		return backColorPicker.getColorValue();
	}

	public int getCurrentLayerIndex() {
		return this.layerlist.getCurrentLayerIndex();
		/*
		 * String curSel = ((HashMap<String, Object>) (layerList.getItem((int)
		 * (layerList.getValue())))).get("value") .toString(); int index =
		 * Integer.parseInt(String.valueOf(curSel.charAt(curSel.length() - 1))); //
		 * System.out.println("String: " + curSel + "\nIndex:" + index); return index;
		 */
	}

	private String getCurrentShape() {
		return ((HashMap<String, Object>) (objType.getItem((int) (objType.getValue())))).get("value").toString();
	}

	public ShapeType getCurrentShapeType() {
		String s = this.getCurrentShape();
		if (s.equals("Square"))
			return ShapeType.SQUARE;
		else if (s.equals("Triangle"))
			return ShapeType.TRIANGLE;
		else
			return ShapeType.ELLIPSE;
	}

	public void removeSelectedLayer() {
		layerlist.removeSelectedLayer();
	}
}
