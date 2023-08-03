package mineSweeper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

 

import static org.junit.jupiter.api.Assertions.*;

public class BoardTester {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

 

    @Test
    public void testIsValidCell() {
        assertTrue(board.isValidCell(0, 0));
        assertTrue(board.isValidCell(9, 9));
        assertFalse(board.isValidCell(-1, 5));
        assertFalse(board.isValidCell(10, 2));
        assertFalse(board.isValidCell(5, -3));
    }

 

    @Test
    public void testGenerateRandomMines() {
        board.generateRandomMines();
        int mineCount = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                mineCount += board.mineArray[i][j];
            }
        }
        assertEquals(true, mineCount > 0);
    }

 

    @Test
    public void testOpenCellFirstMove() {
        board.openCell(0, 0);
        assertFalse(board.isFirstMove);
        assertEquals(0, board.mineArray[0][0]); // First move ensures cell is not a mine.
    }

 

    @Test
    public void testOpenCellRevealSafeCells() {
        board.openCell(0, 0);
        int openCells = board.getOpenCellCount();
        assertTrue(openCells > 0);
    }

 

    @Test
    public void testOpenCellGameOver() {
        board.generateRandomMines();
        for(int row = 0; row < 10; row++) {
        	for(int col = 0; col < 10; col++) {
        		board.openCell(row, col);
        	}
        }
        assertTrue(board.isGameOver);
    }

 

    @Test
    public void testSetFlag() {
        int row = 0;
        int col = 0;
        assertEquals(1, board.setFlag(row, col));
        assertEquals(0, board.setFlag(row, col));
    }
    
    @Test
    public void testToString() {
    	board.toString();
    }

 

    // Add more test cases for other methods as needed.
}
