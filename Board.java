package mineSweeper;

import java.util.*;

public class Board {
	
	public final int size;  
	private boolean[][] mineArray;  // true = Mine, false = "No Mine"
	private int[][] flagArray;      // 0 = closed, 1 = open, -1 = flagged
	
	private boolean isFirstMove = true;    // Initially first move is set to true
	
	public Board(int size) {
		this.size = size;
		mineArray = new boolean[size][size];
		flagArray = new int[size][size];
		Arrays.fill(mineArray, false);
		Arrays.fill(flagArray, 0);
	}
	
	
	public void generateRandomMines() {
		// Generating 20 Random Mines
		Random r = new Random();
		for(int i = 0; i < 20; i++) {
			int num = r.nextInt(0,size * size);
			int row = num / size;
			int col = num % size;
			mineArray[row][col] = true;
		}
	}
	
	public boolean openCell(int row, int col) {
		if(isFirstMove) {
			isFirstMove = false;
			generateRandomMines();
			mineArray[row][col] = false;
			return true;
		}
		
		flagArray[row][col] = 1;
		return !mineArray[row][col];
	}
	
}
