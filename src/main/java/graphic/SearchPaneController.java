package graphic;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchPaneController implements Initializable {
    @FXML
    TextField searchBox;
    final String SEARCHBOX_STYLE_NORMAL = "NORMAL";
    final String SEARCHBOX_STYLE_FOCUS = "FOCUS";
    @FXML
    ComboBox<String> comboBox;
    @FXML
    ListView<String> listView;
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
        listView.setVisible(false);
        webView.getEngine().loadContent("");
    }

    public String[] getSuggestedWord(String hasTyped) {
        String[] words = {"Hello", "How", "Why"};
        return words;
    }

    public void showSuggestedWords(String[] suggestedWords) {
        if (suggestedWords.length == 0) {
            showFoundWord("Không tìm thấy từ này");
            return;
        }

        if (suggestedWords.length == 1) {
            showFoundWord(suggestedWords[0]);
            return;
        }

        listView.getItems().clear();
        listView.getItems().addAll(suggestedWords);
        listView.setPrefHeight(listCellHeight * suggestedWords.length);
        listView.setVisible(true);
    }

    public void showFoundWord(String wordTarget) {
        searchBox.clear();
        listView.setVisible(false);
        webView.setVisible(true);
        webView.setFontScale(1.5);
        webView.getEngine().loadContent("<i>abacist</i><br/><ul><li><b><i> danh từ</i></b><ul><li><font color='#cc0000'><b> người gãy bàn phím</b></font></li></ul><ul><li><font color='#cc0000'><b> người kế toán</b></font></li></ul></li></ul>");
    }

    public void searchBoxSetStyle(String style) {
        final String STYLE_NORMAL =
                "-fx-background-radius: 30;" +
                        "    -fx-background-color: #3c4a7d;" +
                        "    -fx-padding: 0 0 0 30;" +
                        "    -fx-font-size: 20;" +
                        "    -fx-text-fill: white;" +
                        "    -fx-font-family: Arial;" +
                        "    -fx-font-weight: bold;" +
                        "-fx-background-insets: 0";
        final String STYLE_FOCUS =
                "-fx-background-radius: 28 28 0 0;" +
                        "    -fx-background-color: #3c4a7d;" +
                        "    -fx-padding: 0 0 0 30;" +
                        "    -fx-font-size: 20;" +
                        "    -fx-text-fill: white;" +
                        "    -fx-font-family: Arial;" +
                        "    -fx-font-weight: bold;" +
                        "-fx-background-insets: 0";
        if (style.equals(SEARCHBOX_STYLE_NORMAL)) {
            line.setVisible(false);
            searchBox.setStyle(STYLE_NORMAL);
        }
        if (style.equals(SEARCHBOX_STYLE_FOCUS)) {
            searchBox.setStyle(STYLE_FOCUS);
            line.setVisible(true);
        }
    }

    public void searchBoxOnCharacterTyped(KeyEvent event) {
        final char ENTER_CODE = (char) 13;
        final char BACKSPACE_CODE = (char) 8;
        String hasTyped = "";
        searchBoxSetStyle(SEARCHBOX_STYLE_NORMAL);
        if (event.getCharacter().charAt(0) == ENTER_CODE) {
            hasTyped = searchBox.getText();
            String[] suggestedWords = getSuggestedWord(hasTyped);
            if (suggestedWords.length > 0) {
                showFoundWord(suggestedWords[0]);
            }
        } else if (event.getCharacter().charAt(0) == BACKSPACE_CODE) {
            if (searchBox.getText().isEmpty()) {
                listView.setVisible(false);
            } else {
                searchBoxSetStyle(SEARCHBOX_STYLE_FOCUS);
            }
        } else //if (Character.isLetterOrDigit(event.getCharacter().charAt(0))
                //|| Character.is)
            {
            hasTyped = searchBox.getText() + event.getCharacter();
            searchBoxSetStyle(SEARCHBOX_STYLE_FOCUS);
            showSuggestedWords(getSuggestedWord(hasTyped));
        }
    }

    private void initComboBox() {
        comboBox.getItems().addAll(dictionaryList);
        comboBox.setValue(dictionaryList[0]);
    }

    private void setRecentWordStyle(Label label) {
        final String NORMAL =
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20;" +
                "-fx-font-family: Arial;" +
                "-fx-alignment: center;" +
                "-fx-padding: 0 0 0 50";
        final String HOVER =
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20;" +
                "-fx-font-family: Arial;" +
                "-fx-underline: true;" +
                "-fx-alignment: center;" +
                "-fx-padding: 0 0 0 50";

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

    private void initGridPane() {
        int gridRow = gridPane.getRowConstraints().size();
        int gridCol = gridPane.getColumnConstraints().size();
        for (int i = 0; i < recentWords.length; ++i) {
            int currentRow = i / gridCol;
            int currentCol = i % gridCol;
            Label label = new Label(recentWords[i]);
            setRecentWordStyle(label);

            gridPane.add(label, currentCol, currentRow);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
        initComboBox();
        initGridPane();
        listView.setFixedCellSize(listCellHeight);
        line.setVisible(false);
        webView.setVisible(false);
    }
}
