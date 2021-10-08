package graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    AnchorPane mainPane;
    AnchorPane currentPane;

    AnchorPane searchPane;
    AnchorPane googleTranslatePane;
    GoogleTranslateController googleTranslateController;
    SearchPaneController searchPaneController;

    public void onSearchButtonClick() {
        searchPaneController.reset();
        setMainPane(searchPane);
    }

    public void onGoogleTranslateButtonClick() {
        googleTranslateController.reset();
        setMainPane(googleTranslatePane);
    }

    public void setMainPane(AnchorPane pane) {
        if (currentPane == pane) {
            return;
        }
        currentPane = pane;
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/SearchPane.fxml"));
            searchPane = loader.load();
            searchPaneController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("fxml/GoogleTranslatePane.fxml"));
            googleTranslatePane = loader.load();
            googleTranslateController = loader.getController();
        } catch (IOException ioException) {
            System.out.println("Lỗi load file fxml.");
            ioException.printStackTrace();
        }

        setMainPane(searchPane);
    }
}
