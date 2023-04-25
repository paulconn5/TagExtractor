import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ResultsPanel extends JPanel {
    private JTextArea resultsTextArea;
    private JScrollPane resultsScrollPane;
    private JButton saveButton;

    public ResultsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 500));
        setBorder(BorderFactory.createTitledBorder("Results"));

        resultsTextArea = new JTextArea();
        resultsTextArea.setEditable(false);
        resultsScrollPane = new JScrollPane(resultsTextArea);
        add(resultsScrollPane, BorderLayout.CENTER);

        saveButton = new JButton("Save Results");
        add(saveButton, BorderLayout.SOUTH);
    }

    public void displayResults(File inputFile, Map<String, Integer> tagFrequency) {
        resultsTextArea.append("Results for file: " + inputFile.getName() + "\n\n");

        for (Map.Entry<String, Integer> entry : tagFrequency.entrySet()) {
            resultsTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    public void addSaveButtonListener(JFileChooser fileChooser, File inputFile, Map<String, Integer> tagFrequency) {
        saveButton.addActionListener(e -> {FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("Results for file: " + inputFile.getName() + "\n\n");

                    for (Map.Entry<String, Integer> entry : tagFrequency.entrySet()) {
                        writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
