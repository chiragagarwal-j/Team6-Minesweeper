package mineSweeper;

import java.util.*;
import javafx.application.Application;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.stage.Stage;  
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

 
public class guiApplication extends Application {
    public void start(Stage primaryStage) {
        Board board = new Board();

        int topPadding = 10, leftPadding = 10;
        Group group = new Group();


        Rectangle backGroundRectangle = new Rectangle();
        backGroundRectangle.setWidth(350);
        backGroundRectangle.setHeight(350);
        backGroundRectangle.setFill(Color.GREEN);
        group.getChildren().addAll(backGroundRectangle);

        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){

                Rectangle rect = new Rectangle();
                //Text text = new Text();

                rect.setX(col * 55 + leftPadding);
                rect.setY(row * 55 + topPadding);
                rect.setWidth(50);
                rect.setHeight(50);
                rect.setFill(Color.GREY);
                rect.setId(String.valueOf(row * 6 + col));
                rect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    
                    @Override
                    public void handle(MouseEvent e) {
                        rect.setFill(Color.LIGHTGRAY);
                        System.out.println(rect.getId());
                        // text.setX(col * 55 + leftPadding);
                        // text.setY(row * 55 + topPadding);
                        // text.setFont(Font.font("Times New Roman",30));

                    }

                });

                group.getChildren().addAll(rect);

            }

        }

        Scene scene = new Scene(group,600,600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

 

 

    public static void main(String[] args) {

        launch(args);

    }

}