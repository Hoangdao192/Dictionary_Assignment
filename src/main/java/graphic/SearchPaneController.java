package graphic;

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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import data.Dictionary;
import javafx.util.Callback;

public class SearchPaneController implements Initializable {
    Dictionary dictionary = new Dictionary();
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

    String[] dictionaryList = {"Từ điển trên máy", "Google dịch"};
    String[] recentWords = {"Hello", "Hi", "How", "Why"};

    public void reset() {
        searchBox.clear();
        listView.getItems().clear();
        hideListView();
        webView.getEngine().loadContent("");
    }

    public ArrayList<Word> getSuggestedWord(String hasTyped) {
        ArrayList<Word> words = dictionary.searchWord(hasTyped);

        while (words.size() >= 10) {
            words.remove(words.size() - 1);
        }
        return words;
    }

    public void showSuggestedWords(ArrayList<Word> suggestedWords) {
        if (suggestedWords.size() == 0) {
            showFoundWord("Không tìm thấy từ này");
            return;
        }

        if (suggestedWords.size() == 1) {
            showFoundWord(suggestedWords.get(0).getWord_explain());
            return;
        }

        listView.getItems().clear();
        listView.getItems().addAll(suggestedWords);
        listView.setPrefHeight(listCellHeight * suggestedWords.size());
        showListView();
    }

    public void showFoundWord(String wordTarget) {
        hideListView();
        webView.setFontScale(1.5);
        webView.getEngine().loadContent(wordTarget);
    }

    /**
     * Reset thanh search về trạng thái ban đầu.
     */
    public void resetSearchField() {
        listView.getItems().clear();
        hideListView();
        searchBox.clear();
    }

    /**
     * Sự kiện người dùng click chuột chọn 1 từ trong danh sách gợi ý.
     * @param mouseEvent
     */
    public void listViewOnMouseClick(MouseEvent mouseEvent) {
        Word wordTarget = listView.getSelectionModel().getSelectedItem();
        System.out.println(wordTarget);
        webView.getEngine().loadContent(wordTarget.getWord_explain());

        resetSearchField();
    }

    public void searchBoxOnCharacterTyped(KeyEvent event) {
        final char ENTER_CODE = (char) 13;
        final char BACKSPACE_CODE = (char) 8;

        String hasTyped = "";

        if (event.getCharacter().charAt(0) == ENTER_CODE) {
            hasTyped = searchBox.getText();
            ArrayList<Word> suggestedWords = getSuggestedWord(hasTyped);
            if (suggestedWords.size() > 0) {
                showFoundWord(suggestedWords.get(0).getWord_explain());
            }
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
        showFoundWord(getSuggestedWord(hasTyped).get(0).getWord_explain());
    }

    private void showListView() {
        listView.setVisible(true);
        searchBox.setStyle(SEARCH_BOX_STYLE_FOCUS);
        line.setVisible(true);
    }

    private void hideListView() {
        listView.setVisible(false);
        searchBox.setStyle(SEARCH_BOX_STYLE_NORMAL);
        line.setVisible(false);
    }

    private void setRecentWordStyle(Label label) {
        final String NORMAL =
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20;" +
                "-fx-font-family: Arial;" +
                "-fx-alignment: center;" +
                "-fx-padding: 0 0 0 50;";
        final String HOVER = NORMAL + "-fx-underline: true;";

        label.setStyle(NORMAL);
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

    private void initComboBox() {
        comboBox.getItems().addAll(dictionaryList);
        comboBox.setValue(dictionaryList[0]);
    }

    /**
     * Khởi tạo thanh gridPane(recentWordPane).
     */
    private void initGridPane() {
        int gridCol = gridPane.getColumnConstraints().size();
        for (int i = 0; i < recentWords.length; ++i) {
            int currentRow = i / gridCol;
            int currentCol = i % gridCol;
            Label label = new Label(recentWords[i]);
            setRecentWordStyle(label);

            gridPane.add(label, currentCol, currentRow);
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
        initComboBox();
        initGridPane();
        initListView();

        //dictionary.loadFromFile("src/main/resources/data/English-Vietnamese.txt");
        webView.setFontScale(1.5);
    }
}
