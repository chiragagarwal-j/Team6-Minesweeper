package mineSweeper;
import java.util.*;

import javafx.application.Application;  
import javafx.event.ActionEvent;
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.stage.Stage;  
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color; 
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class ViewController extends Application {

    private int leftPadding = 30, topPadding = 70;
    private List<List<Rectangle>> cells;
    private Board board;
    private Group group = new Group();
    
    
    int noOfMines=15;
    int totalNoOfFlags = 15;

    Button openButton = new Button();
    Button flagButton = new Button();
    Text flagLeftTxt = new Text();
    Text developeText = new Text();

    public void updateCellView(){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                if(board.flagArray[row][col] == 1){
                    if(board.mineArray[row][col] == 1){
                        cells.get(row).get(col).setFill(Color.RED);
                        Image image2 = new Image("file:bomb.png", 50, 50, true, true);
                        ImageInput imageinput = new ImageInput();
                        imageinput.setSource(image2);
                        //rect.setFill(Color.RED);
                        imageinput.setX(col * 52 + leftPadding);
                        imageinput.setY(row * 52 + topPadding);
                        cells.get(row).get(col).setEffect(imageinput);
                    }
                    else{
                        cells.get(row).get(col).setFill(Color.GREEN);
                        if(board.adjMinesCount[row][col] > 0){
                            Text txt = new Text();
                            txt.setText(String.valueOf(board.adjMinesCount[row][col]));
                            //System.out.println(board.adjMinesCount[row][col]);
                            txt.setFill(Color.PURPLE);
                            txt.setFont(Font.font("Times New Roman",FontWeight.BOLD,25));
                            txt.setX(cells.get(row).get(col).getX() + 20);
                            txt.setY(cells.get(row).get(col).getY() + 30);
                            group.getChildren().add(txt);
                        }
                    }
                }
            }
        }
        if(board.isGameOver){

            for(int row = 0; row < 10; row++){
                for(int col = 0; col < 10; col++){
                    cells.get(row).get(col).setDisable(true);
                }
            }
            
            System.out.println("Game Over");
            Text gameOverText = new Text();
            gameOverText.setText("GAME OVER!");
            gameOverText.setX(95);
            gameOverText.setY(332);
            gameOverText.setFill(Color.RED);
            gameOverText.setFont(Font.font("Times New Roman",FontWeight.BOLD,60));
           
            group.getChildren().add(gameOverText);

            return;
        }
        if(board.minesCount + board.getOpenCellCount() == 100){
            System.out.println("You Win!");
            Text youWinText = new Text();
            youWinText.setText("YOU WIN!!!");
            youWinText.setX(115);
            youWinText.setY(332);
            youWinText.setFill(Color.BLACK);
            youWinText.setFont(Font.font("Times New Roman",FontWeight.BOLD,60));
           
            group.getChildren().add(youWinText);
        }
    }

    public void addFlag(int row , int col){
        Image image2 = new Image("file:flag.png", 50, 50, true, true);
        ImageInput imageinput = new ImageInput();
        if(board.flagArray[row][col] == 0){
            board.flagArray[row][col] = -1;
            totalNoOfFlags -= 1;
            //cells.get(row).get(col).setFill(Color.RED);
            imageinput.setSource(image2);
            imageinput.setX(col * 52 + leftPadding);
            imageinput.setY(row * 52 + topPadding);
            cells.get(row).get(col).setEffect(imageinput);

        }

        // unable to access the block once marked as flag
        else if(board.flagArray[row][col] == -1){
            
            cells.get(row).get(col).setEffect(null);
            board.flagArray[row][col] = 0;
            totalNoOfFlags += 1;
        }
    }

    public void displayFlagLeft(){
        group.getChildren().remove(flagLeftTxt);
        flagLeftTxt.setText("Flag Left - "+String.valueOf(totalNoOfFlags));
                            //System.out.println(board.adjMinesCount[row][col]);
        flagLeftTxt.setFill(Color.PURPLE);
        flagLeftTxt.setFont(Font.font("Times New Roman",FontWeight.BOLD,20));
        flagLeftTxt.setX(35);
        flagLeftTxt.setY(62);
        group.getChildren().add(flagLeftTxt);
    }

    public Group getGroup(int noOfMines){
        group.getChildren().clear();

        totalNoOfFlags = 15;
        
        developerDetails();
        displayFlagLeft();
        Text gameTitle = new Text();
        gameTitle.setText("MINESWEEPER");
        gameTitle.setX(125);
        gameTitle.setY(35);
        gameTitle.setFill(Color.BLACK);
        gameTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD,40));   
        group.getChildren().add(gameTitle);

        flagButton.setStyle(null);
        openButton.setStyle("-fx-background-color: red;" + 
                                            "-fx-text-fill: white");
        board = new Board();
        cells = new ArrayList<>();
        board.noOfMines = noOfMines;
        for(int row = 0; row < 10; row++){
            List<Rectangle> rectRow = new ArrayList<>();
            for(int col = 0; col < 10; col++){
                System.out.println();
                Rectangle rect = new Rectangle();
                rect.setX(col * 52 + leftPadding);
                rect.setY(row * 52 + topPadding);
                rect.setWidth(50);
                rect.setHeight(50);
                rect.setFill(Color.GREY);
                
                rect.setId(String.valueOf(row * 10 + col));
                rect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        int id = Integer.valueOf(rect.getId());
                        int r = id / 10;
                        int c = id % 10;
                        if(board.isFlagMode == false){
                            if(board.flagArray[r][c] != -1){
                                board.openCell(r, c);
                                updateCellView();
                            }
                        }
                        else{
                            //board.setFlag(r, c);
                            if(totalNoOfFlags > 0){
                                addFlag(r,c);
                               // board.setFlag(r, c);
                                displayFlagLeft();
                            }
                        }
                    }
                });
                rectRow.add(rect);
                group.getChildren().addAll(rect);
            }
            cells.add(rectRow);
        }

        return group;
    }

    public Button flagMode(){
        flagButton.setText(" FLAG MODE");
        flagButton.setMaxWidth(150);
        flagButton.setMaxHeight(30);
        flagButton.setTranslateX(110);
        flagButton.setTranslateY(595);
        flagButton.setFont(Font.font("Times New Roman",FontWeight.BOLD,15));
        flagButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                flagButton.setStyle("-fx-background-color: red;" + 
                                            "-fx-text-fill: white");
                openButton.setStyle(null);
                if(board.isFlagMode == false){
                    board.isFlagMode = true;
                }
            }
        });
        return flagButton;
    }

    public Button openMode(){
        openButton.setText(" OPEN MODE");
        openButton.setMaxWidth(180);
        openButton.setMaxHeight(40);
        openButton.setTranslateX(350);
        openButton.setTranslateY(595);
        openButton.setFont(Font.font("Times New Roman",FontWeight.BOLD,15));

        openButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        openButton.setStyle("-fx-background-color: red;" + 
                                            "-fx-text-fill: white");
                        flagButton.setStyle(null);
                        if(board.isFlagMode == true){
                            board.isFlagMode = false;
                            //board.isOpenMode = true;
                            //updateCellView(group);
                        }
                        else{
                           // board.isFlagMode = false;
                           // board.isOpenMode = false;
                        }
                    }
                });
        return openButton;
    }

    public void reset(){
        Button resetButton = new Button();
        resetButton.setText("RESET");
        resetButton.setMaxWidth(400);
        resetButton.setMaxHeight(30);
        resetButton.setTranslateX(480);
        resetButton.setTranslateY(35);
        resetButton.setFont(Font.font("Times New Roman",FontWeight.BOLD,15));

        resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                getGroup(noOfMines);
                Button flagButton = flagMode();
                group.getChildren().add(flagButton);
                Button openButton = openMode();
                group.getChildren().add(openButton);

               // setDifficulty();
                reset();
                
            }
        });
        group.getChildren().add(resetButton);
    }

    public void developerDetails(){
        
        developeText.setText("Developed by: Ankan, Bibhu, Chirag");
        developeText.setFill(Color.PURPLE);
        developeText.setFont(Font.font("Times New Roman",FontWeight.BOLD,15));
        developeText.setX(5);
        developeText.setY(640);
        group.getChildren().add(developeText);
    }

    public void start(Stage primaryStage) {
        getGroup(noOfMines);

        Button flagButton = flagMode();
        group.getChildren().add(flagButton);

        Button openButton = openMode();
        group.getChildren().add(openButton);

        

        reset();
        primaryStage.setResizable(false);
        Scene scene = new Scene(group,580,650);
        scene.setFill(Color.AQUA);
        
        primaryStage.setScene(scene); 
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); 
    }
}
