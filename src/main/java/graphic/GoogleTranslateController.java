package graphic;

import data.GoogleTranslateAPI.GoogleTranslateAPI;
import data.GoogleTranslateAPI.Language;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class GoogleTranslateController implements Initializable {
    @FXML
    TextArea vietnameseTextArea;
    @FXML
    TextArea englishTextArea;

    GoogleTranslateAPI googleTranslateAPI = new GoogleTranslateAPI();

    @FXML
    ComboBox<String> sourceLanguageList;
    @FXML
    ComboBox<String> targetLanguageList;
    @FXML
    Button swapButton;
    @FXML
    ImageView swapButtonImage;

    private void translate() {
        String text = englishTextArea.getText();
        try {
            String sourceLanguage = Language.getLanguage(sourceLanguageList.getValue());
            String targetLanguage = Language.getLanguage(targetLanguageList.getValue());
            googleTranslateAPI.translate(text, sourceLanguage, targetLanguage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void translateButtonOnClick(MouseEvent mouseEvent) {
        translate();
    }

    public void englishTextAreaOnInput(KeyEvent event) {
        final char ENTER_CODE = (char) 13;
    }

    public void swapButtonOnClick(MouseEvent mouseEvent) {
        String temp = sourceLanguageList.getValue();
        sourceLanguageList.setValue(targetLanguageList.getValue());
        targetLanguageList.setValue(temp);
    }

    public void reset() {
        vietnameseTextArea.clear();
        englishTextArea.clear();
        sourceLanguageList.setValue("English");
        targetLanguageList.setValue("Vietnamese");
    }

    private void initButton() {
        swapButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                swapButton.setPrefHeight(40);
                swapButtonImage.setImage(new Image("graphic/image/swap_hover.png"));
            }
        });
        swapButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                swapButtonImage.setImage(new Image("graphic/image/swap.png"));
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vietnameseTextArea.textProperty().bind(googleTranslateAPI.getStringProperty());
        sourceLanguageList.getItems().addAll(Language.getLanguageList());
        sourceLanguageList.setValue("English");
        targetLanguageList.getItems().addAll(sourceLanguageList.getItems());
        targetLanguageList.setValue("Vietnamese");
        initButton();
    }
}
