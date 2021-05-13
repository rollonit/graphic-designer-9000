import de.bezier.data.sql.*;
import processing.core.PApplet;

public class DB extends PApplet {

	SQLite db;
	
	public DB() {
		db = new SQLite(this, "data/db.sqlite");
		try {
			if (db.connect()) {
				db.query("SELECT * FROM shape");

				//catching the latest information
				while (db.next()) {
					PApplet.print(db.getInt("#i") + ", ");
					PApplet.print(db.getInt("#s") + ", ");
					PApplet.print(db.getInt("x") + ", ");
					PApplet.print(db.getInt("y") + ", ");
					PApplet.print(db.getInt("h") + ", ");
					PApplet.print(db.getInt("w") + ", ");
					PApplet.print(db.getInt("stroke") + ", ");
					PApplet.print(db.getInt("color") + ", ");

					PApplet.println();
				}

				//updating information
				//db.query("UPDATE shape");

				try {
					
					//insert new information
					//db.query("INSERT INTO shape VALUES(1, 1, 50, 50, 20, 10, 12, 255)");
				} catch (Exception e) {
					// Your only way to see whether an UPDATE or INSERT statement worked
					// is when no exception occurred
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
	

