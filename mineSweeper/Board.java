package mineSweeper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Board {

	private final int size = 10;
	public int[][] mineArray = new int[10][10]; // 1 = Mine, 0 = "No Mine"
	public int[][] flagArray = new int[10][10]; // 0 = closed, 1 = open, -1 = flagged
	public int[][] adjMinesCount = new int[10][10];

	public boolean isGameOver = false;
	public boolean isFirstMove; // Initially first move is set to true
	public boolean isFlagMode;

	public int minesCount = 0;
	public int noOfMines = 15;

	public final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	public final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	DBConnector db;

	public Board() {
		isFirstMove = true;
		isFlagMode = false;
		db = new DBConnector();
		try {
			ResultSet rs = db.loadGame();
			if (rs.next()) {
				rs = db.loadGame();
				getValues(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getValues(ResultSet rs) throws SQLException {
		while (rs.next()) {
			int row = rs.getInt("row_no");
			int col = rs.getInt("col_no");
			int flagStatus = rs.getInt("flag_status");
			int mineStatus = rs.getInt("mine_status");
			int adjMineCount = rs.getInt("adj_mine_count");

			flagArray[row][col] = flagStatus;
			mineArray[row][col] = mineStatus;
			adjMinesCount[row][col] = adjMineCount;
		}
		isFirstMove = false;
	}

	public boolean isValidCell(int row, int col) {
		if (row < 0 || row >= size)
			return false;
		if (col < 0 || col >= size)
			return false;
		return true;
	}

	public int getSize() {
		return size;
	}

	public void dfs(int row, int col) throws SQLException {
		if (mineArray[row][col] == 1) {
			isGameOver = true;
			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {
					if (mineArray[r][c] == 1) {
						flagArray[r][c] = 1;
						db.saveGame("UPDATE GameData SET flag_status=1 WHERE row_no=" + r + " and col_no=" + c + ";");
					}
				}
			}
			return;
		}
		if (adjMinesCount[row][col] > 0) {
			flagArray[row][col] = 1;
			db.saveGame("UPDATE GameData SET flag_status=1 WHERE row_no=" + row + " and col_no=" + col + ";");
			return;
		}

		flagArray[row][col] = 1;
		db.saveGame("UPDATE GameData SET flag_status=1 WHERE row_no=" + row + " and col_no=" + col + ";");

		for (int i = 0; i < 8; i++) {
			int newRow = row + dx[i];
			int newCol = col + dy[i];
			if (isValidCell(newRow, newCol) && flagArray[newRow][newCol] == 0) {
				dfs(newRow, newCol);
			}
		}

	}

	public void updateMinesCount() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				minesCount += mineArray[row][col];
			}
		}
	}

	public int getOpenCellCount() {
		int count = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (flagArray[row][col] == 1) {
					count += 1;
				}
			}
		}
		return count;
	}

	public void countAdjMines() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (mineArray[row][col] == 1) {
					adjMinesCount[row][col] = -1;
					continue;
				}

				int count = 0;
				for (int i = 0; i < 8; i++) {
					int newRow = row + dx[i];
					int newCol = col + dy[i];

					if (isValidCell(newRow, newCol) && (mineArray[newRow][newCol] == 1)) {
						count++;
					}
				}
				adjMinesCount[row][col] = count;
			}
		}
	}

	public void generateRandomMines() {
		// Generating 15 Random Mines
		Random r = new Random();
		for (int i = 0; i < noOfMines; i++) {
			int num = r.nextInt(size * size);
			int row = num / size;
			int col = num % size;
			mineArray[row][col] = 1;
		}
	}

	public void openCell(int row, int col) throws SQLException {
		if (isFirstMove) {
			isFirstMove = false;
			generateRandomMines();
			mineArray[row][col] = 0;
			for (int i = 0; i < 8; i++) {
				if (isValidCell(row + dx[i], col + dy[i])) {
					mineArray[row + dx[i]][col + dy[i]] = 0;
				}
			}
			countAdjMines();
			dfs(row, col);
			updateMinesCount();
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					StringBuilder sb = new StringBuilder("INSERT INTO GameData VALUES");
					sb.append("(" + i + "," + j + "," + flagArray[i][j] + "," + mineArray[i][j] + ","
							+ adjMinesCount[i][j] + ")");
					db.initialiseDB(sb.toString());
				}
			}
			return;
		}

		dfs(row, col);
	}

	public int setFlag(int row, int col) {
		if (flagArray[row][col] == -1) {
			flagArray[row][col] = 0;
			try {
				db.saveGame("UPDATE GameData SET flag_status=0 WHERE row_no=" + row + " and col_no=" + col + ";");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
		}
		if (flagArray[row][col] == 1)
			return 0;

		flagArray[row][col] = 1;
		try {
			db.saveGame("UPDATE GameData SET flag_status=1 WHERE row_no=" + row + " and col_no=" + col + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (mineArray[i][j] == 0) {
					result += "0 ";
				} else {
					result += "1 ";
				}
			}
			result += "\n";
		}
		return result;
	}

}
