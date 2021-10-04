package data.FreeDictionaryAPI;

import data.FreeDictionaryAPI.word.Phonetic;
import data.FreeDictionaryAPI.word.Word;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Test extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FreeDictionaryAPI freeDictionaryAPI = new FreeDictionaryAPI();
        ArrayList<Word> words = freeDictionaryAPI.getSuggestedWord("agriculture");
        words.get(0).pronounce(0);
    }
}
