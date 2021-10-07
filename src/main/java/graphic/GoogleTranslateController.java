package graphic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GoogleTranslateController implements Initializable {
    @FXML
    TextArea englishInput;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        englishInput.setWrapText(true);
    }
}
