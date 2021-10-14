package graphic;

import data.FreeDictionaryAPI.FreeDictionaryAPI;
import data.FreeDictionaryAPI.word.FreeDictionaryWord;
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
    ComboBox<String> comboBox;
    @FXML
    ListView<Word> listView;
    final double listCellHeight = 40;
    @FXML
    Line line;
    @FXML
    WebView webView;
    @FXML
    GridPane gridPane;

    FreeDictionaryAPI freeDictionaryAPI = new FreeDictionaryAPI();
    Dictionary localDictionary = new Dictionary();
    RecentWord recentWords = new RecentWord();
    Dictionary personalDictionary = new Dictionary();
    String[] dictionaryList = {"Từ điển trên máy", "Từ điển online", "Từ điển cá nhân"};

    public void reset() {
        audioList.getItems().clear();
        audioLabel.setVisible(false);
        audioList.setVisible(false);
        searchBox.clear();
        hideListView();
        webView.getEngine().loadContent("");
    }

    public ArrayList<Word> getSuggestedWord(String hasTyped) {
        ArrayList<Word> words = new ArrayList<Word>();
        if (comboBox.getValue().equals(dictionaryList[0])) {
            words = localDictionary.searchWord(hasTyped);
        } else if (comboBox.getValue().equals(dictionaryList[2])) {
            words = personalDictionary.searchWord(hasTyped);
        }

        while (words.size() >= 10) {
            words.remove(words.size() - 1);
        }
        return words;
    }

    public Word getExactlyWord(String hasTyped) {
        if (comboBox.getValue().equals(dictionaryList[1])) {
            ArrayList<FreeDictionaryWord> words = freeDictionaryAPI.getSuggestedWord(hasTyped);
            if (words.size() > 0) {
                updateAudioList(words.get(0).getPhonetics());
                return words.get(0);
            }
        } else {
            ArrayList<Word> words = getSuggestedWord(hasTyped);
            if (words.size() > 0) {
                Word word = getSuggestedWord(hasTyped).get(0);
                ArrayList<Phonetic> audios = new ArrayList<Phonetic>();
                audios.add(new Phonetic(word.getSound(), ""));
                updateAudioList(audios);
                return word;
            }
        }
        Word word = new Word();
        word.setWord_target("Không tìm thấy từ này");
        word.setWord_explain("Không tìm thấy từ này");
        return word;
    }

    public void showSuggestedWords(ArrayList<Word> suggestedWords) {
        if (suggestedWords.size() == 0) {
            Word word = new Word();
            word.setWord_explain("Không tìm thấy từ này");
            showFoundWord(word);
            return;
        }
        if (suggestedWords.size() == 1) {
            showFoundWord(suggestedWords.get(0));
            return;
        }
        listView.getItems().clear();
        listView.getItems().addAll(suggestedWords);
        listView.setPrefHeight(listCellHeight * suggestedWords.size());
        showListView();
    }

    public void updateAudioList(ArrayList<Phonetic> phonetics ) {
        audioList.getItems().clear();
        for (int i = 0; i < phonetics.size(); ++i) {
            System.out.println(phonetics.get(i).getPronounce());
            System.out.println(phonetics.get(i).getAudio());
        }
        audioList.getItems().addAll(phonetics);
    }

    public void showFoundWord(Word word) {
        hideListView();
        webView.getEngine().loadContent(word.getWord_explain());
        System.out.println(word.getSound());
        if (word.getWord_explain().equals("Không tìm thấy từ này")) {
            audioLabel.setVisible(false);
            audioList.setVisible(false);
        } else {
            audioLabel.setVisible(true);
            audioList.setVisible(true);
            recentWords.add(word);
            updateGridPane();
        }
    }

    /**
     * Sự kiện người dùng click chuột chọn 1 từ trong danh sách gợi ý.
     * @param mouseEvent
     */
    public void listViewOnMouseClick(MouseEvent mouseEvent) {
        Word wordTarget = listView.getSelectionModel().getSelectedItem();
        ArrayList<Phonetic> audios = new ArrayList<Phonetic>();
        audios.add(wordTarget.getPhonetic());
        updateAudioList(audios);
        showFoundWord(wordTarget);
        hideListView();
    }

    public void audioListOnMouseClick(MouseEvent mouseEvent) {
        if (audioList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        audioList.getSelectionModel().getSelectedItem().playSound();
    }

    public void searchBoxOnCharacterTyped(KeyEvent event) {
        final char ENTER_CODE = (char) 13;
        final char BACKSPACE_CODE = (char) 8;

        String hasTyped = "";

        if (event.getCharacter().charAt(0) == ENTER_CODE) {
            hasTyped = searchBox.getText();
            Word word = getExactlyWord(hasTyped);
            showFoundWord(word);
        } else if (event.getCharacter().charAt(0) == BACKSPACE_CODE && searchBox.getText().isEmpty()) {
            hideListView();
        } else if (event.getCharacter().charAt(0) == BACKSPACE_CODE) {
            hasTyped = searchBox.getText();
            System.out.println(hasTyped);
            showSuggestedWords(getSuggestedWord(hasTyped));
        } else {
            hasTyped = searchBox.getText() + event.getCharacter();
            System.out.println(hasTyped);
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
     * set style và xử lý sự kiện cho các Label trong tab recent word.
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
                System.out.println(word.getSound());
                audioList.getItems().clear();
                audioList.getItems().add(word.getPhonetic());
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
     * Khởi tạo dictionary list.
     */
    private void initComboBox() {
        comboBox.getCellFactory();
        comboBox.getItems().addAll(dictionaryList);
        comboBox.setValue(dictionaryList[0]);
    }

    /**
     * Khởi tạo thanh gridPane(recentWordPane).
     */
    private void initGridPane() {
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

    private void initListView() {
        listView.setFixedCellSize(listCellHeight);
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

    private void updateGridPane() {
        gridPane.getChildren().clear();
        initGridPane();
        recentWords.saveToFile(Dictionary.RECENT_WORD);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDictionary.insertFromFile(Dictionary.MAIN_DICTIONARY);
        recentWords.loadFromFile(Dictionary.RECENT_WORD);
        personalDictionary.insertFromFile(Dictionary.PERSONAL_DICTIONARY);

        reset();
        initComboBox();
        initGridPane();
        initListView();
        initAudioList();
        webView.setFontScale(1.5);
    }
}
