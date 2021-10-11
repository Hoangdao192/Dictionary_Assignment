package graphic;

import data.Data;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Text;

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
        Data.update = true;
        changePane();
    }

    public void clickButtonCancel() throws IOException {
        changePane();
    }

    public void changePane() throws IOException {
        if(Data.word.equals("") || Data.explain.equals("")) {
            Data.update = false;
        }
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
