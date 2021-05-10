import de.bezier.data.sql.*;
import processing.core.PApplet;

public class DB {

	SQLite db;

	public DB() {
		db = new SQLite(null, "data/db.sql");
		try {
			if (db.connect()) {
				db.query("SELECT * FROM shape");

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

				// db.query("UPDATE shape SET )

				try {
					db.query("INSERT INTO contacts VALUES(\"Oak Trebb\", \"Test 1 \", 4, 123)");
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
