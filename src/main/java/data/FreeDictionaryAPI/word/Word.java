package data.FreeDictionaryAPI.word;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import util.DownloadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Word extends data.Word{
    HashSet<Phonetic> phonetics = new HashSet<Phonetic>();
    ArrayList<Definition> definitions = new ArrayList<Definition>();

    public Word() {

    }

    public Word(String wordTarget) {
        this.setWord_target(wordTarget);
    }

    public HashSet<Phonetic> getPhonetics() {
        return this.phonetics;
    }

    public ArrayList<Definition> getDefinitions() {
        return this.definitions;
    }

    public boolean sameWordTarget(Word other) {
        return this.getWord_target().equals(other.getWord_target());
    }

    public void pronounce(int phoneticIndex) {
        final String tempFilePath = "src/main/resources/temp/audio.mp3";

        if (phoneticIndex > phonetics.size()) {
            System.out.println("phoneticIndex out of range.");
        }
        Phonetic[] phoneticArray = phonetics.toArray(new Phonetic[phonetics.size()]);
        try {
            DownloadFile.download(tempFilePath, "https:" + phoneticArray[phoneticIndex].getAudio());
        } catch (IOException ioException) {
            System.out.println("Cannot find this path " + tempFilePath);
        }

        File audioFile = new File(tempFilePath);
        Media media = new Media(audioFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void print() {
        System.out.println("Word: " + getWord_target());
        for (Phonetic a : phonetics) {
            System.out.println("Pronounce: " + a.getPronounce());
            System.out.println("Audio: " + a.getAudio());
        }

        for (Definition d: definitions) {
            System.out.println("Definition: " + d.getDefinition());
            System.out.println("PartOfSpeech: " + d.getPartOfSpeech());
            System.out.println("SYNONYMS");
            HashSet<String> synonyms = d.getSynonyms();
            String[] strarr = synonyms.toArray(new String[synonyms.size()]);
            for (int i = 0; i < strarr.length; ++i) {
                System.out.println(strarr[i]);
            }
            System.out.println("ANTONYMS");
            HashSet<String> antonyms = d.getAntonyms();
            strarr = antonyms.toArray(new String[antonyms.size()]);
            for (int i = 0; i < strarr.length; ++i) {
                System.out.println(strarr[i]);
            }
        }
    }
}
