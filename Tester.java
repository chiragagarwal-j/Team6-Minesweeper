package mineSweeper;

public class Tester {
	public static void main(String[] args) {
		Board board = new Board();
		
		board.openCell(5, 5);
		
		System.out.println(board);
	}
}
