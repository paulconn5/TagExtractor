import java.io.*;
import java.util.*;

public class TagExtractor {
    private File file;
    private Set<String> stopWords;
    private Map<String, Integer> tagFrequency;

    public TagExtractor(File file, Set<String> stopWords) {
        this.file = file;
        this.stopWords = stopWords;
        tagFrequency = new HashMap<>();
    }

    public void extractTags() {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split("[^a-zA-Z]+");

                for (String word : words) {
                    String lowercaseWord = word.toLowerCase();

                    if (!stopWords.contains(lowercaseWord)) {
                        tagFrequency.put(lowercaseWord, tagFrequency.getOrDefault(lowercaseWord, 0) + 1);
                    }
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public Map<String, Integer> getTagFrequency() {
        return tagFrequency;
    }
}
