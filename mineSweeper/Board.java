package mineSweeper;

import java.util.*;

public class Board {
	
	public final int size = 10;  
	private int[][] mineArray = new int[10][10];  // 1 = Mine, 0 = "No Mine"
	private int[][] flagArray = new int[10][10];      // 0 = closed, 1 = open, -1 = flagged
	
	private boolean isFirstMove;    // Initially first move is set to true
	
	public Board() {
		
		isFirstMove = true;
	}
	
	
	
	
	
	public int[][] getFlagArray(){
		return flagArray;
	}
	
	public int[][] getMineArray(){
		return mineArray;
	}
	
	public int getSize() {
		return size;
	}
	
	
	
	
	
	public void generateRandomMines() {
		// Generating 30 Random Mines
		Random r = new Random();
		for(int i = 0; i < 30; i++) {
			int num = r.nextInt(0,size * size);
			int row = num / size;
			int col = num % size;
			mineArray[row][col] = 1;
		}
	}
	
	public boolean openCell(int row, int col) {
		if(isFirstMove) {
			isFirstMove = false;
			generateRandomMines();
			mineArray[row][col] = 0;
			return true;
		}
		
		flagArray[row][col] = 1;
		return mineArray[row][col] == 0;
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
