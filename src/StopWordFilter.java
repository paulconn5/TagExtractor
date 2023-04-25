import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class StopWordFilter {
    private Set<String> stopWords;

    public StopWordFilter(File stopWordsFile) {
        stopWords = new HashSet<>();

        try {
            for (String word : Files.readAllLines(stopWordsFile.toPath())) {
                stopWords.add(word.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getStopWords() {
        return stopWords;
    }
}
