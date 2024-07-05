package patcher;

import org.atilika.kuromoji.Token;

public class PatchedToken {
    Token originalToken;
    String surface;
    String reading;
    String pronunciation;

    PatchedToken(Token originalToken) {
        this.originalToken = originalToken;
        this.surface = originalToken.getSurfaceForm();
        String[] features = originalToken.getAllFeaturesArray();
        this.reading = originalToken.getReading();
        this.pronunciation = features.length > 8 ? features[8] : null; // Assuming pronunciation is at index 8
    }

    public String getSurface() {
        return surface;
    }

    public String getReading() {
        return reading;
    }
    public String getPronunciation() {
        return pronunciation;
    }

    public String getPos() {
        return originalToken.getPartOfSpeech();
    }

}
