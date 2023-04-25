import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileChooserFrame extends JFrame {
    private File selectedTextFile;
    private File selectedStopWordFile;
    private JLabel textFileLabel;
    private JLabel stopWordFileLabel;

    public FileChooserFrame() {
        super("File Chooser");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        JButton textFileButton = new JButton("Select Text File");
        JButton stopWordButton = new JButton("Select Stop Word File");
        textFileLabel = new JLabel("No text file selected");
        stopWordFileLabel = new JLabel("No stop word file selected");

        textFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedTextFile = fileChooser.getSelectedFile();
                textFileLabel.setText(selectedTextFile.getName());
            }
        });

        stopWordButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedStopWordFile = fileChooser.getSelectedFile();
                stopWordFileLabel.setText(selectedStopWordFile.getName());
            }
        });

        panel.add(textFileButton);
        panel.add(textFileLabel);
        panel.add(stopWordButton);
        panel.add(stopWordFileLabel);

        add(panel, BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public File getSelectedTextFile() {
        return selectedTextFile;
    }

    public File getSelectedStopWordFile() {
        return selectedStopWordFile;
    }

    public static void main(String[] args) {
        // create and show the main JFrame
        FileChooserFrame frame = new FileChooserFrame();
        frame.setSize(400,400);
        frame.setVisible(true);

        // wait for the user to select the text file and stop word file
        while (frame.getSelectedTextFile() == null || frame.getSelectedStopWordFile() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // create the stop word filter
        StopWordFilter stopWordFilter = new StopWordFilter(frame.getSelectedStopWordFile());

        // create the tag extractor
        TagExtractor tagExtractor = new TagExtractor(frame.getSelectedTextFile(), stopWordFilter.getStopWords());
        tagExtractor.extractTags();

        // display  results
        ResultsPanel resultsPanel = new ResultsPanel();
        resultsPanel.displayResults(frame.getSelectedTextFile(), tagExtractor.getTagFrequency());

        // add the save button listener
        JFileChooser fileChooser = new JFileChooser();
        resultsPanel.addSaveButtonListener(fileChooser, frame.getSelectedTextFile(), tagExtractor.getTagFrequency());

        // create the main JFrame
        JFrame mainFrame = new JFrame("Tag Extractor");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(resultsPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
