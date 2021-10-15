package graphic;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    ImageView searchButtonImage;
    @FXML
    Button searchButton;
    @FXML
    ImageView translateButtonImage;
    @FXML
    Button translateButton;
    @FXML
    ImageView modifyButtonImage;
    @FXML
    Button modifyButton;

    @FXML
    AnchorPane mainPane;
    AnchorPane currentPane;

    AnchorPane searchPane;
    AnchorPane googleTranslatePane;
    AnchorPane modifyPane;
    SearchPaneController searchPaneController;
    GoogleTranslateController googleTranslateController;

    public void onSearchButtonClick() {
        if (currentPane == searchPane) {
            return;
        }
        searchPaneController.reset();
        setMainPane(searchPane);
    }

    public void onGoogleTranslateButtonClick() {
        if (currentPane == googleTranslatePane) {
            return;
        }
        googleTranslateController.reset();
        setMainPane(googleTranslatePane);
    }

    public void onModifyButtonClick() {
        setMainPane(modifyPane);
    }

    public void setMainPane(AnchorPane pane) {
        if (currentPane == pane) {
            return;
        }
        currentPane = pane;
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    private void initButton() {
        searchButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                searchButtonImage.setImage(new Image("graphic/image/search_hover.png"));
            }
        });
        searchButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                searchButtonImage.setImage(new Image("graphic/image/search.png"));
            }
        });
        translateButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                translateButtonImage.setImage(new Image("graphic/image/google_translate_hover.png"));
            }
        });
        translateButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                translateButtonImage.setImage(new Image("graphic/image/google_translate.png"));
            }
        });
        modifyButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                modifyButtonImage.setImage(new Image("graphic/image/local_dictionary_hover.png"));
            }
        });
        modifyButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                modifyButtonImage.setImage(new Image("graphic/image/local_dictionary.png"));
            }
        });
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

            loader = new FXMLLoader(getClass().getResource("fxml/ModifyDictionary.fxml"));
            modifyPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initButton();
        setMainPane(searchPane);
    }
}
