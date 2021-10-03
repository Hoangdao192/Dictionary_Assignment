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
            "-fx-background-radius: 28 28 0 0;" +
            "-fx-background-color: #3c4a7d;" +
            "-fx-padding: 0 0 0 30;" +
            "-fx-font-size: 20;" +
            "-fx-text-fill: white;" +
            "-fx-font-family: Arial;" +
            "-fx-font-weight: bold;" +
            "-fx-background-insets: 0";
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
        hideListView();
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
        showListView();
    }

    public void showFoundWord(String wordTarget) {
        searchBox.clear();
        listView.setVisible(false);
        showListView();
        webView.setFontScale(1.5);
        webView.getEngine().loadContent("<i>abacist</i><br/><ul><li><b><i> danh từ</i></b><ul><li><font color='#cc0000'><b> người gãy bàn phím</b></font></li></ul><ul><li><font color='#cc0000'><b> người kế toán</b></font></li></ul></li></ul>");
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
        String wordTarget = listView.getSelectionModel().getSelectedItem();
        System.out.println(wordTarget);
        webView.getEngine().loadContent(wordTarget);

        resetSearchField();
    }

    public void searchBoxOnCharacterTyped(KeyEvent event) {
        final char ENTER_CODE = (char) 13;
        final char BACKSPACE_CODE = (char) 8;

        String hasTyped = "";

        if (event.getCharacter().charAt(0) == ENTER_CODE) {
            hasTyped = searchBox.getText();
            String[] suggestedWords = getSuggestedWord(hasTyped);
            if (suggestedWords.length > 0) {
                showFoundWord(suggestedWords[0]);
            }
        } else if (event.getCharacter().charAt(0) == BACKSPACE_CODE && searchBox.getText().isEmpty()) {
               hideListView();
        } else {
            hasTyped = searchBox.getText() + event.getCharacter();
            System.out.println(hasTyped);
            showSuggestedWords(getSuggestedWord(hasTyped));
        }
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
        initComboBox();
        initGridPane();
        initListView();

        webView.setFontScale(1.5);
    }
}
