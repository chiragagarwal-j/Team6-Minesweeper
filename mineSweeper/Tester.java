package mineSweeper;
import java.util.*;

import javafx.application.Application;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.stage.Stage;  
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color; 
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class Tester extends Application {

    private List<List<Rectangle>> cells;
    private Board board;

    public void updateCellView(Group group){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                if(board.flagArray[row][col] == 1){
                    if(board.mineArray[row][col] == 1){
                        cells.get(row).get(col).setFill(Color.RED);
                    }
                    else{
                        cells.get(row).get(col).setFill(Color.GREEN);
                        if(board.adjMinesCount[row][col] > 0){
                            Text txt = new Text();
                            txt.setText(String.valueOf(board.adjMinesCount[row][col]));
                            txt.setX(cells.get(row).get(col).getX() + 20);
                            txt.setY(cells.get(row).get(col).getY() + 30);
                            group.getChildren().add(txt);
                        }
                    }
                }
            }
        }
    }

    public Group getGroup(){
        
        Group group = new Group();

        int topPadding = 50, leftPadding = 50;

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
                            updateCellView(group);
                        }
                        else{
                            board.setFlag(r, c);
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

    public void start(Stage primaryStage) {
        Group group = getGroup();

        Scene scene = new Scene(group,600,600);
        scene.setFill(Color.AQUA);
        
        primaryStage.setScene(scene); 
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); 
    }
}
