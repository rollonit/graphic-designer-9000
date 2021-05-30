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

	/**
	 * Creates a new DB and associates it with the main pApplet and the Canvas.
	 * 
	 * @param pApplet The handle of the pApplet
	 * @param canvas  The handle of the canvas
	 */
	public DB(PApplet pApplet, Canvas canvas) {
		this.canvas = canvas;
		this.pApplet = pApplet;

	}

	/**
	 * Initializes the DB. It creates a new file if a file with the given name
	 * doesn't exist and prepares it for data entry if is empty.
	 * 
	 * @param connectPath The path to initialize the database with
	 */
	public void init(String connectPath) {
		this.path = connectPath;

		PrintWriter temp = this.pApplet.createWriter("data/" + path + ".gx9");
		temp.close();
		db = new SQLite(this.pApplet, "data/" + path + ".gx9");

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return The path the DB is currently associated with
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Changes the file the DB is linked to and reinitializes the file, unless the
	 * new path is empty.
	 * 
	 * @param newPath The new path to a file
	 */
	public void setPath(String newPath) {
		if (newPath.equals("")) {
			this.init(newPath);
		}
	}

	/**
	 * Reads all the data in the currently associated database and applies the data
	 * to the canvas class adding layers, shapes and their attributes (and the
	 * canvas color).
	 */
	public void read() {
		ShapeType type;
		// Temporary canvas object to store the canvas and restore it if the database
		// read operation fails in any way.
		Canvas temp = this.canvas;

		this.canvas.deleteAllLayers();

		try {
			// Reading and adding all the layers with their attributes
			if (db.connect()) {
				db.query("SELECT * FROM layer ORDER BY #l");

				// catching the latest layer
				while (db.next()) {

					canvas.addLayer(db.getString("name"), (db.getInt("visibility") == 1) ? true : false);

					PApplet.print(db.getString("name"));
					PApplet.print(db.getInt("visibility"));
				}
			}

			// Reading and adding all the shapes with their attributes
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

			// Reading and applying the background color of the canvas
			if (db.connect()) {
				db.query("SELECT * FROM canvas");

				// catching the latest background color
				while (db.next()) {

					canvas.setBackgroundColor(db.getInt("color"));
				}
			}
		} catch (Exception e) {
			// Restoring the canvas since the read operation has failed
			this.canvas = temp;
			e.printStackTrace();
		}
	}

	/**
	 * Writes all of the layers, shapes and their attributes (and the canvas color)
	 * to the currently associated DB file. Warning: Overwrites any currently
	 * existing data in that file.
	 */
	public void write() {
		String type = "";
		try {
			if (db.connect()) {
				// Deleting any previous data from the file to prevent conflicts.
				db.query("DELETE FROM layer");

				// Writing all the layers to layers table
				for (int i = 0; i < canvas.getLayers().size(); i++) {
					int visible = canvas.getLayer(i).isVisible() ? 1 : 0;
					db.query("INSERT INTO layer VALUES(" + i + ", \"" + canvas.getLayer(i).getName() + "\", " + visible
							+ ")");
				}

				db.query("DELETE FROM shape");

				// Writing all the shapes to the shapes table
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

				// Writing the background color of the canvas to the canvas table
				db.query("INSERT INTO canvas VALUES(" + canvas.getBackgroundColor() + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the currently associated DB has any data in it.
	 * 
	 * @return True if there is data in the currently associated file
	 */
	public boolean hasData() {
		boolean hasData = false;
		try {
			// This works by getting the number of columns in the canvas table. It should be
			// 1 normally, and 0 if it is empty or doesn't exist.
			if (db.connect()) {
				db.query("SELECT COUNT(*) FROM canvas");
				db.next();
				int x = db.getInt(1);
				hasData = x > 0 ? true : false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasData;
	}
}
