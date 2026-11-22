import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CounterApp {
    // State — stored as instance variables so they persist between events
    private int count = 0;
    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 20;

    private JLabel countLabel;
    private JLabel statusLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private JButton resetButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CounterApp().createAndShow());
    }

    private void createAndShow() {
        JFrame frame = new JFrame("Counter Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // ---- Display (CENTER) ----
        countLabel = new JLabel("0", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.BOLD, 72));
        frame.add(countLabel, BorderLayout.CENTER);

        // ---- Buttons (SOUTH) ----
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        decrementButton = new JButton("−");
        decrementButton.setFont(new Font("Arial", Font.BOLD, 24));
        decrementButton.setPreferredSize(new Dimension(80, 60));

        incrementButton = new JButton("+");
        incrementButton.setFont(new Font("Arial", Font.BOLD, 24));
        incrementButton.setPreferredSize(new Dimension(80, 60));

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setPreferredSize(new Dimension(90, 60));

        buttonPanel.add(decrementButton);
        buttonPanel.add(incrementButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // ---- Status (NORTH) ----
        statusLabel = new JLabel("Ready (Use buttons or ↑, ↓, R keys)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(statusLabel, BorderLayout.NORTH);

        // ---- EVENT HANDLERS ----

        // Increment handler
        incrementButton.addActionListener(e -> {
            if (count < MAX_VALUE) {
                count++;
                updateDisplay();
                statusLabel.setText("Incremented to " + count);
            } else {
                statusLabel.setText("Maximum reached!");
            }
        });

        // Decrement handler
        decrementButton.addActionListener(e -> {
            if (count > MIN_VALUE) {
                count--;
                updateDisplay();
                statusLabel.setText("Decremented to " + count);
            } else {
                statusLabel.setText("Minimum reached!");
            }
        });

        // Reset handler
        resetButton.addActionListener(e -> {
            count = 0;
            updateDisplay();
            statusLabel.setText("Counter reset");
        });

        // ---- KEYBOARD SHORTCUTS (Step 4.3) ----

        // Bind Up arrow to increment
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("UP"), "increment");
        frame.getRootPane().getActionMap()
            .put("increment", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    incrementButton.doClick();
                }
            });

        // Bind Down arrow to decrement
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("DOWN"), "decrement");
        frame.getRootPane().getActionMap()
            .put("decrement", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    decrementButton.doClick();
                }
            });

        // Bind R key to reset
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("R"), "reset");
        frame.getRootPane().getActionMap()
            .put("reset", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetButton.doClick();
                }
            });

        frame.setVisible(true);
    }

    // Helper method to update the display and styling
    private void updateDisplay() {
        countLabel.setText(String.valueOf(count));

        if (count >= MAX_VALUE) {
            countLabel.setForeground(Color.RED);
        } else if (count <= MIN_VALUE) {
            countLabel.setForeground(new Color(200, 100, 0)); // orange
        } else {
            countLabel.setForeground(Color.BLACK);
        }
    }
}