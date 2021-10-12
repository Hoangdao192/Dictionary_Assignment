package graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class GraphicDemo extends Application {
    public static void cleanTempFile() {
        for(File file: new File("src/main/resources/temp").listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public static void main(String[] args) {
        cleanTempFile();
        launch(args);
        cleanTempFile();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/ParentPane.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
