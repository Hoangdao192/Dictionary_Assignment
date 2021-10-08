package graphic;

import data.GoogleTranslateAPI.GoogleTranslateAPI;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class GoogleTranslateController{
    @FXML
    TextArea vietnameseTextArea;
    @FXML
    TextArea englishTextArea;

    GoogleTranslateAPI googleTranslateAPI = new GoogleTranslateAPI();

    private void translate() {
        String text = englishTextArea.getText();
        try {
            String translatedText = googleTranslateAPI.translate(text);
            vietnameseTextArea.setText(translatedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void translateButtonOnClick(MouseEvent mouseEvent) {
        translate();
    }

    public void englishTextAreaOnInput(KeyEvent event) {
        final char ENTER_CODE = (char) 13;
        if (event.getCharacter().charAt(0) != ENTER_CODE) {
            return;
        }
        translate();
    }

    public void reset() {
        vietnameseTextArea.clear();
        englishTextArea.clear();
    }
}
