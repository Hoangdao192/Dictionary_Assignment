package graphic;

import data.Data;
import data.Dictionary;
import data.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyController implements Initializable {
    @FXML
    private TableView<Word> tableWord;
    @FXML
    private TableColumn<Word, String> columnWord;
    @FXML
    private TextField textSearch;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonDetail;
    @FXML
    private ObservableList<Word> wordList;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane myPane;
    @FXML
    private AnchorPane modifyPane;


    private String fileSup = "src/main/resources/data/Sup.txt";
    private Dictionary dictionary = new Dictionary();
    public Word newWord = new Word();

    public void clickButtonSearch() {
        String str = textSearch.getText();
        ArrayList<Word> arr = dictionary.searchWord(str);
        wordList = FXCollections.observableArrayList(arr);
        tableWord.setItems(wordList);
    }

    public void textSearchStart() {
        ArrayList<Word> arr = dictionary.getArrWord();
        wordList = FXCollections.observableArrayList(arr);
        tableWord.setItems(wordList);
    }

    public void textSearchRun() {
        textSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String str = textSearch.getText();
                System.out.println(str);
                ArrayList<Word> arr = dictionary.searchWord(str);
                wordList = FXCollections.observableArrayList(arr);
                tableWord.setItems(wordList);
            }
        });
    }

    public void clickButtonDelete() {
        if(tableWord.getSelectionModel().getSelectedItem() != null) {
            Word word = tableWord.getSelectionModel().getSelectedItem();
            wordList.remove(word);
            dictionary.deleteWord(word);
            dictionary.saveToFile(fileSup);
        }
    }

    public void clickButtonNew() throws IOException {
        changePane();
    }

    public void clickButtonDetail() throws IOException {
        if (tableWord.getSelectionModel().getSelectedItem() != null) {
            newWord.setWord(tableWord.getSelectionModel().getSelectedItem());
            Data.word = newWord.getWord_target();
            Data.explain = newWord.getWord_explain();
            changePane();
        } else {
            return;
        }
    }

    public void changePane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        modifyPane = loader.load(getClass().getResource("fxml/ModifyDetail.fxml"));
        myPane.getChildren().clear();
        myPane.getChildren().addAll(modifyPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionary.insertFromFile(fileSup);
        if(Data.update) {
            Word w = newWord;
            w.setWord_target(Data.word);
            w.setWord_explain(Data.explain);
            dictionary.addWord(w);
            dictionary.saveToFile(fileSup);
            Data.update = false;
            Data.word = "";
            Data.explain = "";
        }
        FileInputStream input = null;
        columnWord.setCellValueFactory(new PropertyValueFactory<Word, String>("word_target"));
        textSearchStart();
        textSearchRun();
    }
}