package graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicDemo extends Application {
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/ParentPane.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("css/ModifyDictionary.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
