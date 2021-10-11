package graphic;

import data.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyDetailController implements Initializable {
    @FXML
    TextField textWord;
    @FXML
    TextArea textAreaDefind;
    @FXML
    Button buttonUpdate;
    @FXML
    Button buttonCancel;
    @FXML
    AnchorPane modifyPane;
    AnchorPane myPane;

    public void getTheWord() {
        textWord.setText(Data.word);
        textAreaDefind.setText(Data.explain);
    }

    public void clickButtonUpdate() throws IOException {
        Data.word = textWord.getText();
        Data.explain = textAreaDefind.getText();
        if(Data.word.equals("") || Data.explain.equals("")) {
            return;
        }
        Data.update = true;
        changePane();
    }

    public void clickButtonCancel() throws IOException {
        changePane();
    }

    public void changePane() throws IOException {
        if(!Data.update) {
            Data.word = "";
            Data.explain = "";
        }
        FXMLLoader loader = new FXMLLoader();
        myPane = loader.load(getClass().getResource("fxml/ModifyDictionary.fxml"));
        modifyPane.getChildren().clear();
        modifyPane.getChildren().addAll(myPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTheWord();
    }
}
