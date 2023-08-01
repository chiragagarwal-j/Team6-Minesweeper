package mineSweeper;

import java.util.*;

public class Board {
	
	private final int size = 10;  
	public int[][] mineArray = new int[10][10];         // 1 = Mine, 0 = "No Mine"
	public int[][] flagArray = new int[10][10];         // 0 = closed, 1 = open, -1 = flagged
	public int[][] adjMinesCount = new int[10][10];
	
	public boolean isFirstMove;    // Initially first move is set to true
	public boolean isFlagMode;

	public final int[] dx = {-1, -1, -1,  0,  0,  1,  1,  1};
	public final int[] dy = {-1,  0,  1, -1,  1, -1,  0,  1};
	
	public Board() {
		
		isFirstMove = true;
		isFlagMode = false;
	}
	
	
	public boolean isValidCell(int row, int col){
		if(row < 0 || row >= size) return false;
		if(col < 0 || col >= size) return false;
		return true;
	}
	
	public int getSize() {
		return size;
	}


	public void dfs(int row, int col){
		if(mineArray[row][col] == 1){
			for(int r = 0; r < size; r++){
				for(int c = 0; c < size; c++){
					if(mineArray[r][c] == 1){
						flagArray[r][c] = 1;
					}
				}
			}
			return;
		}
		if(adjMinesCount[row][col] > 0){
			flagArray[row][col] = 1;
			return;
		}

		flagArray[row][col] = 1;

		for(int i = 0; i < 8; i++){
			int newRow = row + dx[i];
			int newCol = col + dy[i];
			if(isValidCell(newRow, newCol) && flagArray[newRow][newCol] == 0){
				dfs(newRow, newCol);
			}
		}


	}
	
	
	
	public void countAdjMines(){
		for(int row = 0; row < size; row++){
			for(int col = 0; col < size; col++){
				if(mineArray[row][col] == 1){
					adjMinesCount[row][col] = -1;
					continue;
				}

				int count = 0;
				for(int i = 0; i < 8; i++){
					int newRow = row + dx[i];
					int newCol = col + dy[i];

					if(isValidCell(newRow, newCol) && (mineArray[newRow][newCol] == 1)){
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
		for(int i = 0; i < 15; i++) {
			int num = r.nextInt(size * size);
			int row = num / size;
			int col = num % size;
			mineArray[row][col] = 1;
		}
	}
	
	public void openCell(int row, int col) {
		if(isFirstMove) {
			isFirstMove = false;
			generateRandomMines();
			mineArray[row][col] = 0;
			for(int i = 0; i < 8; i++){
				if(isValidCell(row + dx[i], col + dy[i])) {
					mineArray[row + dx[i]][col + dy[i]] = 0;
				}
			}
			countAdjMines();
			dfs(row, col);
			return;
		}
		
		dfs(row, col);
	}
	
	public int setFlag(int row, int col){
		if(flagArray[row][col] == -1){
			flagArray[row][col] = 0;
			return -1;
		}
		if(flagArray[row][col] == 1) return 0;

		flagArray[row][col] = 1;
		return 1;
	}
	
	
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(mineArray[i][j] == 0) {
					result += "0 ";
				}
				else {
					result += "1 ";
				}
			}
			result += "\n";
		}
		return result;
	}
	
	
	
}
