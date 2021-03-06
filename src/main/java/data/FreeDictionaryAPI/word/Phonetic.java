package data.FreeDictionaryAPI.word;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.DownloadFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Phonetic {
    String pronounce = "";
    String audio = "";

    public Phonetic() {
    }

    public Phonetic(String pronounce, String audio) {
        if (pronounce == null) {
            pronounce = "";
        }
        if (audio == null) {
            audio = "";
        }
        this.pronounce = pronounce;
        this.audio = audio;

        if (audio.equals("")) {
            return;
        }
        final String tempFilePath = "src/main/resources/temp/" + this.toString();
        try {
            DownloadFile.download(tempFilePath, "https:" + audio);
        } catch (IOException ioException) {
            System.out.println("Cannot dowload this audio: " + audio);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(pronounce, audio);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        Phonetic otherPhonetic = (Phonetic) other;
        if (this.audio.equals(otherPhonetic.audio)
                && this.pronounce.equals(otherPhonetic.pronounce)) {
            return true;
        }
        return false;
    }

    public String getPronounce() {
        return this.pronounce;
    }

    public String getAudio() {
        return this.audio;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public void playSound() {
        if (audio.equals("")) {
            return;
        }
        String tempFilePath = "src/main/resources/temp/" + toString();
        File audioFile = new File(tempFilePath);
        Media media = new Media(audioFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * Không được sửa hàm này.
     */
    @Override
    public final String toString() {
        return pronounce;
    }

}
