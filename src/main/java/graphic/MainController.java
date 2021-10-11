package graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    AnchorPane mainPane;
    AnchorPane currentPane;

    AnchorPane searchPane;
    AnchorPane googleTranslatePane;
    AnchorPane modifyPane;
    SearchPaneController searchPaneController;

    public void onSearchButtonClick() {
        if (currentPane == searchPane) {
            return;
        }
        searchPaneController.reset();
        setMainPane(searchPane);
    }

    public void onGoogleTranslateButtonClick() {
        setMainPane(googleTranslatePane);
    }

    public void onModifyButtonClick() {
        setMainPane(modifyPane);
    }

    public void setMainPane(AnchorPane pane) {
        if (currentPane == pane) {
            return;
        }
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

            loader = new FXMLLoader(getClass().getResource("fxml/ModifyDictionary.fxml"));
            modifyPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setMainPane(searchPane);
    }
}
