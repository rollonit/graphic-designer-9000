package core;

import java.io.PrintWriter;

import de.bezier.data.sql.*;
import processing.core.PApplet;

/**
 * <h3>Database class that handles all the SQlite data read and write
 * operations.</h3> It must be associated with a certain DB file location, and
 * reads and writes the layers and shapes from the canvas.
 */
public class DB {

	SQLite db;
	Canvas canvas;
	Layer layer;
	PApplet pApplet;
	
	String path;

	public DB(PApplet pApplet, Canvas canvas) {
		this.canvas = canvas;
		this.pApplet = pApplet;
		
	}
	
	public void init(String connectPath) {
		this.path = connectPath;
		
		PrintWriter temp = this.pApplet.createWriter("data/" + path);
		temp.close();
		db = new SQLite(this.pApplet, "data/" + path);
		
		

		try {
			if (db.connect()) {
				db.query("CREATE TABLE \"canvas\" (\r\n" + "	\"color\"	INTEGER\r\n" + ")");
				db.query("CREATE TABLE \"layer\" (\r\n" + "	\"#l\"	INTEGER,\r\n" + "	\"name\"	TEXT,\r\n"
						+ "	\"visibility\"	INTEGER,\r\n" + "	PRIMARY KEY(\"#l\")\r\n" + ")");
				db.query("CREATE TABLE \"shape\" (\r\n" + "	\"#l\"	INTEGER,\r\n" + "	\"#s\"	INTEGER,\r\n"
						+ "	\"type\"	TEXT,\r\n" + "	\"name\"	TEXT,\r\n" + "	\"x\"	INTEGER,\r\n"
						+ "	\"y\"	INTEGER,\r\n" + "	\"h\"	INTEGER,\r\n" + "	\"w\"	INTEGER,\r\n"
						+ "	\"stroke\"	INTEGER,\r\n" + "	\"color\"	INTEGER,\r\n"
						+ "	PRIMARY KEY(\"#l\",\"#s\")\r\n" + ")");
			}
		}

		catch (Exception e) {
			// Your only way to see whether an UPDATE or INSERT statement worked
			// is when no exception occurred
			e.printStackTrace();
		}
	}

	public void read() {
		ShapeType type;
		Canvas temp = this.canvas;
		this.canvas.deleteAllLayers();
		try {
			if (db.connect()) {
				db.query("SELECT * FROM layer ORDER BY #l");

				// catching the latest layer
				while (db.next()) {

					canvas.addLayer(db.getString("name"), (db.getInt("visibility") == 1) ? true : false);

					PApplet.print(db.getString("name"));
					PApplet.print(db.getInt("visibility"));
				}
			}
			if (db.connect()) {
				db.query("SELECT * FROM shape ORDER BY #s");

				// catching the latest shape
				while (db.next()) {

					if (db.getString("type").equals("SQUARE")) {
						type = ShapeType.SQUARE;
					} else if (db.getString("type").equals("TRIANGLE")) {
						type = ShapeType.TRIANGLE;
					} else {
						type = ShapeType.ELLIPSE;
					}

					canvas.addToLayer(db.getInt("#l"), type, db.getString("name"), db.getInt("x"), db.getInt("y"),
							db.getInt("w"), db.getInt("h"), db.getInt("stroke"), db.getInt("color"));

					PApplet.print(db.getString("name"));
					PApplet.print(db.getString("x"));
					PApplet.print(db.getString("y"));
				}
			}

			if (db.connect()) {
				db.query("SELECT * FROM canvas");

				// catching the latest background color
				while (db.next()) {

					canvas.setBackgroundColor(db.getInt("color"));
				}
			}
		} catch (Exception e) {

			this.canvas = temp;
			// Your only way to see whether an UPDATE or INSERT statement worked
			// is when no exception occurred
			e.printStackTrace();
		}
	}

	public void write() {
		String type = "";
		try {
			if (db.connect()) {
				db.query("DELETE FROM layer");

				// write layers
				for (int i = 0; i < canvas.getLayers().size(); i++) {
					int visible = canvas.getLayer(i).isVisible() ? 1 : 0;
					db.query("INSERT INTO layer VALUES(" + i + ", \"" + canvas.getLayer(i).getName() + "\", " + visible
							+ ")");
				}

				db.query("DELETE FROM shape");

				// write shapes
				for (int i = 0; i < canvas.getLayers().size(); i++) {
					for (int j = 0; j < canvas.getLayer(i).getShapes().size(); j++) {
						type = canvas.getLayer(i).getShape(j).getType().name();

						db.query("INSERT INTO shape VALUES(\"" + i + "\", \"" + j + "\", \"" + type + "\", \""
								+ canvas.getLayer(i).getShape(j).getName() + "\",\""
								+ canvas.getLayer(i).getShape(j).getX() + "\",\""
								+ canvas.getLayer(i).getShape(j).getY() + "\",\""
								+ canvas.getLayer(i).getShape(j).getH() + "\",\""
								+ canvas.getLayer(i).getShape(j).getW() + "\",\""
								+ canvas.getLayer(i).getShape(j).getStroke() + "\",\""
								+ canvas.getLayer(i).getShape(j).getColor() + "\") ");

						PApplet.print("INSERT INTO shape VALUES(\"" + i + "\", \"" + j + "\", \"" + type + "\", \""
								+ canvas.getLayer(i).getShape(j).getName() + "\", \""
								+ canvas.getLayer(i).getShape(j).getX() + "\", \""
								+ canvas.getLayer(i).getShape(j).getY() + "\", \""
								+ canvas.getLayer(i).getShape(j).getH() + "\", \""
								+ canvas.getLayer(i).getShape(j).getW() + "\", \""
								+ canvas.getLayer(i).getShape(j).getStroke() + "\", \""
								+ canvas.getLayer(i).getShape(j).getColor() + "\") ");
					}
				}

				db.query("DELETE FROM canvas");

				// write background color
				db.query("INSERT INTO canvas VALUES(" + canvas.getBackgroundColor() + ")");
			}
		} catch (Exception e) {
			// Your only way to see whether an UPDATE or INSERT statement worked
			// is when no exception occurred
			e.printStackTrace();
		}
	}
}
