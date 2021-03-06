package graphic;

import data.FreeDictionaryAPI.FreeDictionaryAPI;
import data.FreeDictionaryAPI.word.Phonetic;
import data.RecentWord;
import data.Word;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import data.Dictionary;
import javafx.util.Callback;

public class SearchPaneController implements Initializable {
    @FXML
    TextField searchBox;
    final String SEARCH_BOX_STYLE_NORMAL =
            "-fx-background-radius: 30;" +
            "-fx-background-color: #3c4a7d;" +
            "-fx-padding: 0 0 0 30;" +
            "-fx-font-size: 20;" +
            "-fx-text-fill: white;" +
            "-fx-font-family: Arial;" +
            "-fx-font-weight: bold;" +
            "-fx-background-insets: 0;";
    final String SEARCH_BOX_STYLE_FOCUS =
            SEARCH_BOX_STYLE_NORMAL +
            "-fx-background-radius: 28 28 0 0;";
    @FXML
    ListView<Phonetic> audioList;
    @FXML
    Label audioLabel;
    @FXML
    ComboBox<Dictionary> comboBox;
    @FXML
    ListView<Word> listView;
    final double LIST_CELL_HEIGHT = 40;
    @FXML
    Line line;
    @FXML
    WebView webView;
    @FXML
    GridPane gridPane;

    FreeDictionaryAPI freeDictionaryAPI = new FreeDictionaryAPI();
    Dictionary localDictionary = new Dictionary();
    Dictionary personalDictionary = new Dictionary();
    RecentWord recentWords = new RecentWord();

    public void reset() {
        audioList.getItems().clear();
        audioLabel.setVisible(false);
        audioList.setVisible(false);
        searchBox.clear();
        hideListView();
        webView.getEngine().loadContent("");
    }

    public ArrayList<Word> getSuggestedWord(String hasTyped) {
        ArrayList<Word> words = comboBox.getValue().searchWord(hasTyped);
        while (words.size() >= 10) {
            words.remove(words.size() - 1);
        }
        return words;
    }

    //  L???y t??? ??? ?????u danh s??ch g???i ??.
    public Word getExactlyWord(String hasTyped) {
        ArrayList<Word> words = getSuggestedWord(hasTyped);
        if (words.size() > 0) {
            updateAudioList(words.get(0).getPhonetics());
            return words.get(0);
        }
        Word word = new Word();
        word.setWord_target("Kh??ng t??m th???y t??? n??y");
        word.setWord_explain("Kh??ng t??m th???y t??? n??y");
        return word;
    }

    public void showSuggestedWords(ArrayList<Word> suggestedWords) {
        if (suggestedWords.size() == 0) {
            Word word = new Word();
            word.setWord_explain("Kh??ng t??m th???y t??? n??y");
            showFoundWord(word);
            return;
        }
        if (suggestedWords.size() == 1) {
            showFoundWord(suggestedWords.get(0));
            return;
        }
        listView.getItems().clear();
        listView.getItems().addAll(suggestedWords);
        listView.setPrefHeight(LIST_CELL_HEIGHT * suggestedWords.size());
        showListView();
    }

    public void showFoundWord(Word word) {
        hideListView();
        webView.getEngine().loadContent(word.getWord_explain());
        if (word.getWord_explain().equals("Kh??ng t??m th???y t??? n??y")) {
            audioLabel.setVisible(false);
            audioList.setVisible(false);
        } else {
            audioLabel.setVisible(true);
            audioList.setVisible(true);
            recentWords.add(word);
            updateRecentWordPane();
        }
    }

    /**
     * S??? ki???n ng?????i d??ng click chu???t ch???n 1 t??? trong danh s??ch g???i ??.
     * @param mouseEvent
     */
    public void listViewOnMouseClick(MouseEvent mouseEvent) {
        Word wordTarget = listView.getSelectionModel().getSelectedItem();
        updateAudioList(wordTarget.getPhonetics());
        showFoundWord(wordTarget);
        hideListView();
    }

    public void audioListOnMouseClick(MouseEvent mouseEvent) {
        if (audioList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        audioList.getSelectionModel().getSelectedItem().playSound();
    }

    public void searchBoxOnCharacterTyped(KeyEvent keyEvent) {
        final char ENTER_CODE = (char) 13;
        final char BACKSPACE_CODE = (char) 8;

        String hasTyped = "";

        if (keyEvent.getCharacter().charAt(0) == ENTER_CODE) {
            hasTyped = searchBox.getText();
            Word word = getExactlyWord(hasTyped);
            showFoundWord(word);
        }
        //  Khi ng?????i d??ng x??a h???t k?? t??? trong search box.
        else if (keyEvent.getCharacter().charAt(0) == BACKSPACE_CODE && searchBox.getText().isEmpty()) {
            hideListView();
        }
        else if (keyEvent.getCharacter().charAt(0) == BACKSPACE_CODE) {
            hasTyped = searchBox.getText();
            showSuggestedWords(getSuggestedWord(hasTyped));
        }
        /*
        *   Kh??ng g???i ?? t??? khi ??ang d??ng T??? ??i???n online.
        *   V?? qu?? tr??nh k???t n???i m???ng ????? tra t??? t???n nhi???u th???i gian l??m cho app b??? lag
        *   Vi???c g???i ?? t??? li??n t???c s??? khi???n cho app b??? lag sau m???i l???n g?? 1 k?? t???
        */
        else if (comboBox.getValue() != freeDictionaryAPI){
            hasTyped = searchBox.getText() + keyEvent.getCharacter();
            showSuggestedWords(getSuggestedWord(hasTyped));
        }
    }

    public void searchButtonOnMouseClick(ActionEvent actionEvent) {
        String hasTyped = searchBox.getText();
        hideListView();
        Word word = getExactlyWord(hasTyped);
        showFoundWord(word);
    }

    private void showListView() {
        listView.setVisible(true);
        searchBox.setStyle(SEARCH_BOX_STYLE_FOCUS);
        line.setVisible(true);
    }

    private void hideListView() {
        listView.getItems().clear();
        listView.setVisible(false);
        searchBox.setStyle(SEARCH_BOX_STYLE_NORMAL);
        line.setVisible(false);
    }

    /**
     * set style v?? x??? l?? s??? ki???n cho c??c Label trong tab recent word.
     */
    private void initRecentWordLabel(Label label) {
        final String NORMAL =
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20;" +
                "-fx-font-family: Arial;" +
                "-fx-alignment: center;" +
                "-fx-padding: 0 0 0 50;";
        final String HOVER = NORMAL + "-fx-underline: true;";

        label.setStyle(NORMAL);
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Word word = recentWords.getWord(label.getText());
                audioList.getItems().clear();
                audioList.getItems().add(word.getPhonetics().get(0));
                showFoundWord(word);
            }
        });
        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setStyle(HOVER);
                ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
            }
        });
        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setStyle(NORMAL);
                ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    /**
     * Kh???i t???o dictionary list.
     */
    private void initComboBox() {
        comboBox.getItems().addAll(localDictionary, freeDictionaryAPI, personalDictionary);
        comboBox.setValue(localDictionary);
    }

    /**
     * Kh???i t???o thanh gridPane(recentWordPane).
     */
    private void initRecentWordPane() {
        int gridCol = gridPane.getColumnConstraints().size();
        ArrayList<Word> words = recentWords.getArrWord();
        for (int i = 0; i < words.size(); ++i) {
            int currentRow = i / gridCol;
            int currentCol = i % gridCol;
            Label label = new Label(words.get(i).getWord_target());
            initRecentWordLabel(label);

            gridPane.add(label, currentCol, currentRow);
        }
    }

    private void updateRecentWordPane() {
        gridPane.getChildren().clear();
        initRecentWordPane();
        recentWords.saveToFile(Dictionary.RECENT_WORD);
    }

    private void initAudioList() {
        audioLabel.setVisible(false);
        audioList.setVisible(false);
        audioList.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
            }
        });
        audioList.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    private void updateAudioList(ArrayList<Phonetic> phonetics ) {
        audioList.getItems().clear();
        for (int i = 0; i < phonetics.size(); ++i) {
            System.out.println(phonetics.get(i).getPronounce());
            System.out.println(phonetics.get(i).getAudio());
        }
        audioList.getItems().addAll(phonetics);
    }

    private void initListView() {
        listView.setFixedCellSize(LIST_CELL_HEIGHT);
        listView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
            }
        });
        listView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
            }
        });

        listView.setCellFactory(new Callback<ListView<Word>, ListCell<Word>>() {
            @Override
            public ListCell<Word> call(ListView<Word> param) {
                return new ListCell<Word>(){
                    @Override
                    public void updateItem(Word person, boolean empty) {
                        super.updateItem(person, empty);
                        if (empty || person == null) {
                            setText(null);
                        } else {
                            setText(person.getWord_target());
                        }
                    }
                };
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDictionary.insertFromFile(Dictionary.MAIN_DICTIONARY);
        localDictionary.setName("T??? ??i???n c?? nh??n");
        recentWords.loadFromFile(Dictionary.RECENT_WORD);
        personalDictionary.insertFromFile(Dictionary.PERSONAL_DICTIONARY);
        personalDictionary.setName("T??? ??i???n tr??n m??y");
        freeDictionaryAPI.setName("T??? ??i???n online");

        reset();
        initComboBox();
        initRecentWordPane();
        initListView();
        initAudioList();
        webView.setFontScale(1.5);
    }
}
