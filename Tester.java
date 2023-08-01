
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tester extends Application {
    public void start(Stage primaryStage) {
        Board board = new Board();
        int topPadding = 10, leftPadding = 10;
        Group group = new Group();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle rect = new Rectangle();
                rect.setX(col * 55 + leftPadding);
                rect.setY(row * 55 + topPadding);
                rect.setWidth(50);
                rect.setHeight(50);
                rect.setFill(Color.GREY);
                rect.setId(String.valueOf(row * 10 + col));
                Text text = new Text();
                rect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent e) {
                        int id = Integer.valueOf(rect.getId());
                        int r = id / 10;
                        int c = id % 10;
                        if (board.openCell(r, c)) {
                            String sum ="1";
                            rect.setFill(Color.GREEN);
                            text.setText(sum);
                            text.setX(c * 55 + leftPadding);
                            text.setY(r * 55 + topPadding);
                            text.setFont(Font.font("Times New Roman",20));
                            text.setFill(Color.RED);
                            text.setStroke(Color.BLACK);
                            
                            
                        }
                        else {
                            Image image2 = new Image("/bomb.png", 50, 50, true, true);
                            ImageInput imageinput = new ImageInput();
                            imageinput.setSource(image2);
                            rect.setFill(Color.RED);
                            imageinput.setX(c * 55 + leftPadding);
                            imageinput.setY(r * 55 + topPadding);
                            rect.setEffect(imageinput);
                      }
                    }
                });
                group.getChildren().addAll(rect, text);

            }
        }

        Scene scene = new Scene(group, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

 

    public static void main(String[] args) {
        launch(args);

    }

 

}