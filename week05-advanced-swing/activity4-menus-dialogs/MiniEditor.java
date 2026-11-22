import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MiniEditor {

    private JFrame frame;
    private JTextArea textArea;
    private File currentFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniEditor().createAndShow());
    }

    private void createAndShow() {
        frame = new JFrame("Mini Editor — Untitled");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // ---- Menu Bar ----
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        // ---- Toolbar ----
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton newBtn = new JButton("New");
        JButton openBtn = new JButton("Open");
        JButton saveBtn = new JButton("Save");

        toolbar.add(newBtn);
        toolbar.add(openBtn);
        toolbar.add(saveBtn);
        toolbar.addSeparator();
        toolbar.add(new JLabel(" Word count: "));
        JLabel wordCountLabel = new JLabel("0");
        toolbar.add(wordCountLabel);

        frame.add(toolbar, BorderLayout.NORTH);

        // ---- Text Area ----
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // ---- Status Bar ----
        JLabel statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(statusBar, BorderLayout.SOUTH);

        // ============================================================
        // EVENT HANDLERS
        // ============================================================

        // NEW
        Runnable newAction = () -> {
            textArea.setText("");
            currentFile = null;
            frame.setTitle("Mini Editor — Untitled");
            statusBar.setText("New file created");
        };
        newItem.addActionListener(e -> newAction.run());
        newBtn.addActionListener(e -> newAction.run());

        // OPEN — JFileChooser in action
        Runnable openAction = () -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                try {
                    String content = Files.readString(currentFile.toPath());
                    textArea.setText(content);
                    frame.setTitle("Mini Editor — " + currentFile.getName());
                    statusBar.setText("Opened: " + currentFile.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error opening file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        openItem.addActionListener(e -> openAction.run());
        openBtn.addActionListener(e -> openAction.run());

        // SAVE AS (declared before SAVE so saveAction can invoke it)
        Runnable saveAsAction = () -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = chooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
                try {
                    Files.writeString(currentFile.toPath(), textArea.getText());
                    frame.setTitle("Mini Editor — " + currentFile.getName());
                    statusBar.setText("Saved as: " + currentFile.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error saving: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        saveAsItem.addActionListener(e -> saveAsAction.run());

        // SAVE
        Runnable saveAction = () -> {
            if (currentFile == null) {
                saveAsAction.run();
            } else {
                try {
                    Files.writeString(currentFile.toPath(), textArea.getText());
                    statusBar.setText("Saved: " + currentFile.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error saving: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        saveItem.addActionListener(e -> saveAction.run());
        saveBtn.addActionListener(e -> saveAction.run());

        // EXIT
        exitItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame,
                    "Exit without saving? Unsaved changes will be lost.",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // ABOUT — JOptionPane
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Mini Editor v1.0\nBuilt with Java Swing\nWeek 5 Activity 4",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // CUT / COPY / PASTE
        cutItem.addActionListener(e -> textArea.cut());
        copyItem.addActionListener(e -> textArea.copy());
        pasteItem.addActionListener(e -> textArea.paste());

        // Real-time word count
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateWordCount(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateWordCount(); }
            @Override
            public void changedUpdate(DocumentEvent e) { }

            private void updateWordCount() {
                String text = textArea.getText().trim();
                int words = text.isEmpty() ? 0 : text.split("\\s+").length;
                wordCountLabel.setText(String.valueOf(words));
            }
        });

        frame.setVisible(true);
    }
}