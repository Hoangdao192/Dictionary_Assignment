package data.FreeDictionaryAPI.word;

import java.util.Objects;

public class Phonetic {
    String pronounce;
    String audio;

    public Phonetic() {
        this.pronounce = "";
        this.audio = "";
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
    }

    @Override
    public int hashCode() {
        return Objects.hash(pronounce, audio);
    }

    @Override
    public boolean equals(Object other) {
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
}
