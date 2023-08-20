package mineSweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	static PreparedStatement insert = null;
	static Connection cnx = null;

	DBConnector() {
		try {
			cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/mineSweeper", "chiragagarwals",
					"chiragagarwals");
			System.out.println("Connection Succeeded!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initialiseDB(String initialQuery) throws SQLException {
		PreparedStatement show = cnx.prepareStatement(initialQuery);
		show.executeUpdate();
	}

	public void saveGame(String updateQuery) throws SQLException {
		PreparedStatement show = cnx.prepareStatement(updateQuery);
		show.executeUpdate();
	}

	public ResultSet loadGame() {
		PreparedStatement show;
		try {
			show = cnx.prepareStatement("SELECT * FROM GameData");
			ResultSet rs = show.executeQuery();
			return rs;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}
}
