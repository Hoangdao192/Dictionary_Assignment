package graphic;

import data.GoogleTranslateAPI.GoogleTranslateAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
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

    private void translate() {
        String text = englishTextArea.getText();
        try {
            googleTranslateAPI.translate(text);
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

    public void reset() {
        vietnameseTextArea.clear();
        englishTextArea.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vietnameseTextArea.textProperty().bind(googleTranslateAPI.getStringProperty());
    }
}
