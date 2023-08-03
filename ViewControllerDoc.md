## Minesweeper Game Application

The Minesweeper game application is a Java-based implementation of the classic Minesweeper puzzle video game. It allows players to uncover cells on a grid while avoiding hidden mines. The application features a graphical user interface built using JavaFX and provides an interactive gaming experience.

### Features

- A 10x10 grid representing the game board.
- Cells that can be hidden, flagged, or revealed.
- The ability to mark cells suspected to contain mines with flags.
- Counting and display of adjacent mines for revealed cells.
- Game Over when a mine is clicked, displaying a "GAME OVER" message.
- Winning the game by revealing all non-mine cells, displaying a "YOU WIN!!!" message.
- The option to start a new game with a different number of mines.
- A flag mode and open mode to control user interactions.
- Developer details displayed at the bottom of the game window.

### How to Play

1. Launch the Minesweeper game application.
2. The game board will be displayed as a 10x10 grid of cells, initially hidden.
3. Click on the "FLAG MODE" button to enable flag mode, or "OPEN MODE" to enable open mode.
4. In flag mode, right-click on a cell to mark it with a flag, indicating a suspected mine. Right-click again to remove the flag.
5. In open mode, left-click on a cell to reveal its contents. If it contains a mine, the game ends with a "GAME OVER" message.
6. If the revealed cell contains a number, it indicates the number of adjacent cells with mines.
7. Use the numbers to deduce which cells are safe to click on and which ones contain mines.
8. Continue playing until you either win the game by revealing all non-mine cells or lose by clicking on a mine.
9. To start a new game, click the "RESET" button.

### Adjusting Difficulty

By default, the game starts with 15 mines. You can adjust the number of mines to increase or decrease the difficulty by modifying the `noOfMines` variable in the code.

### Conclusion

The Minesweeper game application provides an entertaining and challenging experience for players of all ages. With its user-friendly interface and interactive gameplay, it remains true to the classic Minesweeper game while adding a modern touch. Enjoy hours of puzzle-solving fun as you strategically uncover cells and avoid hidden mines.
