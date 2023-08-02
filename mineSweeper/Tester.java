package mineSweeper;
import java.util.*;

import javafx.application.Application;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.stage.Stage;  
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color; 
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class Tester extends Application {

    private int leftPadding = 30, topPadding = 100;
    private List<List<Rectangle>> cells;
    private Board board;
    private Group group = new Group();

    int totalNoOfFlags = 15;

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
                            txt.setX(cells.get(row).get(col).getX() + 20);
                            txt.setY(cells.get(row).get(col).getY() + 30);
                            group.getChildren().add(txt);
                        }
                    }
                }
            }
        }
        if(board.isGameOver){
            System.out.println("Game Over");
            // TODO
            return;
        }
        if(board.minesCount + board.getOpenCellCount() == 100){
            System.out.println("You Win!");
        }
    }

    public void addFlag(int row , int col){
        Image image2 = new Image("file:flag.png", 50, 50, true, true);
        ImageInput imageinput = new ImageInput();
        if(board.flagArray[row][col] == 0){
            board.flagArray[row][col] = -1;
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
        }
    }

    public Group getGroup(){
        
        group.getChildren().clear();

        board = new Board();
        cells = new ArrayList<>();

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
                            board.openCell(r, c);
                            updateCellView();
                        }
                        else{
                            //board.setFlag(r, c);
                            if(totalNoOfFlags > 0){
                                addFlag(r,c);
                               // board.setFlag(r, c);
                                totalNoOfFlags --;
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
        Button flagButton = new Button();
        flagButton.setText(" FLAG MODE");
        flagButton.setMaxWidth(150);
        flagButton.setMaxHeight(30);
        flagButton.setTranslateX(140);
        flagButton.setTranslateY(60);
        flagButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(board.isFlagMode == false){
                    board.isFlagMode = true;
                }
            }
        });
        return flagButton;
    }

    public Button openMode(){
        Button openButton = new Button();
        openButton.setText(" OPEN MODE");
        openButton.setMaxWidth(150);
        openButton.setMaxHeight(30);
        openButton.setTranslateX(350);
        openButton.setTranslateY(60);
        openButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
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

    public Button Reset(){
        Button resetButton = new Button();
        resetButton.setText("RESET");
        resetButton.setMaxWidth(150);
        resetButton.setMaxHeight(30);
        resetButton.setTranslateX(140);
        resetButton.setTranslateY(20);
        resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                getGroup();
                Button flagButton = flagMode();
                group.getChildren().add(flagButton);
                Button openButton = openMode();
                group.getChildren().add(openButton);
                Button resetButton = Reset();
                group.getChildren().add(resetButton);
            }
        });
        return resetButton;
    }

    public void start(Stage primaryStage) {
        getGroup();

        Button flagButton = flagMode();
        group.getChildren().add(flagButton);

        Button openButton = openMode();
        group.getChildren().add(openButton);

        Button resetButton = Reset();
        group.getChildren().add(resetButton);

        Scene scene = new Scene(group,580,650);
        scene.setFill(Color.AQUA);
        
        primaryStage.setScene(scene); 
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); 
    }
}
