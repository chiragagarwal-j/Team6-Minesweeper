# Board

This is a Java class that represents the board of a minesweeper game. It has the following attributes and methods:

## Attributes

- `size`: an integer constant that stores the size of the board (10 x 10).
- `mineArray`: a 2D integer array that stores the locations of the mines (1 = mine, 0 = no mine).
- `flagArray`: a 2D integer array that stores the status of each cell (0 = closed, 1 = open, -1 = flagged).
- `adjMinesCount`: a 2D integer array that stores the number of adjacent mines for each cell.
- `isGameOver`: a boolean variable that indicates whether the game is over or not.
- `isFirstMove`: a boolean variable that indicates whether the current move is the first move or not.
- `isFlagMode`: a boolean variable that indicates whether the flag mode is on or not.
- `minesCount`: an integer variable that stores the total number of mines on the board.
- `noOfMines`: an integer constant that stores the number of mines to be generated randomly (15).
- `dx` and `dy`: integer arrays that store the relative coordinates of the eight neighboring cells.

## Methods

- `Board()`: a constructor that initializes the attributes and sets `isFirstMove` to true and `isFlagMode` to false.
- `isValidCell(int row, int col)`: a method that checks if a given cell is within the bounds of the board and returns a boolean value.
- `getSize()`: a method that returns the size of the board.
- `dfs(int row, int col)`: a method that performs a depth-first search from a given cell and opens all the adjacent cells that have no mines. If the cell has a mine, it sets `isGameOver` to true and opens all the cells with mines. If the cell has some adjacent mines, it opens only that cell and shows the number of adjacent mines.
- `updateMinesCount()`: a method that updates the `minesCount` attribute by counting the number of mines in the `mineArray`.
- `getOpenCellCount()`: a method that returns the number of open cells in the `flagArray`.
- `countAdjMines()`: a method that counts the number of adjacent mines for each cell and stores them in the `adjMinesCount` array.
- `generateRandomMines()`: a method that generates `noOfMines` random mines and places them in the `mineArray` using a random number generator.
- `openCell(int row, int col)`: a method that opens a given cell by calling `dfs` if it is the first move or not. If it is the first move, it also generates random mines, ensures that the current cell and its neighbors have no mines, and counts the adjacent mines for each cell.
- `setFlag(int row, int col)`: a method that toggles the flag status of a given cell in the `flagArray` and returns an integer value (-1 = unflagged, 0 = already open, 1 = flagged).
- `toString()`: a method that returns a string representation of the `mineArray` for debugging purposes.
