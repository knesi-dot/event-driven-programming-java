import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkingGaragePrototype extends JFrame {

    private static final int TOTAL_CAPACITY = 10;
    private int occupiedSpaces = 0;

    private JLabel statusLabel;
    private JLabel capacityLabel;
    private JTextArea eventLog;
    private JButton entryButton;
    private JButton exitButton;
    private JToggleButton emergencyToggle;
    private JButton resetButton;

    public ParkingGaragePrototype() {
        setTitle("Parking Garage Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Status & Header Panel (NORTH)
        JPanel northPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel("System Operational - Gate Closed", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        capacityLabel = new JLabel("Available Spaces: " + (TOTAL_CAPACITY - occupiedSpaces) + " / " + TOTAL_CAPACITY, SwingConstants.CENTER);
        capacityLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        northPanel.add(statusLabel);
        northPanel.add(capacityLabel);
        add(northPanel, BorderLayout.NORTH);

        // 2. Real-time Event Log (CENTER)
        eventLog = new JTextArea(12, 35);
        eventLog.setEditable(false);
        eventLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(eventLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Event Audit Log"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Control Panel with Interactive Event Sources (SOUTH)
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        entryButton = new JButton("Vehicle Entry (Ticket Issued)");
        exitButton = new JButton("Vehicle Exit (Fee Paid)");
        emergencyToggle = new JToggleButton("Emergency Mode: OFF");
        resetButton = new JButton("Reset Garage Count");

        // Styling buttons
        entryButton.setBackground(new Color(200, 230, 201));
        exitButton.setBackground(new Color(187, 222, 251));
        emergencyToggle.setBackground(new Color(255, 205, 210));

        // --- EVENT HANDLER 1: Vehicle Entry ---
        entryButton.addActionListener(e -> {
            if (occupiedSpaces < TOTAL_CAPACITY) {
                occupiedSpaces++;
                updateDisplayState();
                logEvent("ENTRY: Vehicle entered. Gate opened & closed.");
                statusLabel.setText("Vehicle Entered Successfully");
                statusLabel.setForeground(new Color(0, 128, 0));
            } else {
                logEvent("ALERT: Entry rejected. Garage is FULL!");
                statusLabel.setText("DENIED: Garage Full!");
                statusLabel.setForeground(Color.RED);
            }
        });

        // --- EVENT HANDLER 2: Vehicle Exit ---
        exitButton.addActionListener(e -> {
            if (occupiedSpaces > 0) {
                occupiedSpaces--;
                updateDisplayState();
                logEvent("EXIT: Vehicle paid fee and departed. Gate cycled.");
                statusLabel.setText("Vehicle Exited Successfully");
                statusLabel.setForeground(new Color(0, 102, 204));
            } else {
                logEvent("WARNING: Exit triggered but garage is already empty!");
                statusLabel.setText("Warning: No cars in garage!");
                statusLabel.setForeground(Color.ORANGE);
            }
        });

        // --- EVENT HANDLER 3: Emergency Override ---
        emergencyToggle.addActionListener(e -> {
            if (emergencyToggle.isSelected()) {
                emergencyToggle.setText("Emergency Mode: ACTIVE");
                entryButton.setEnabled(false);
                exitButton.setEnabled(false);
                statusLabel.setText("EMERGENCY: ALL BARRIERS OPEN!");
                statusLabel.setForeground(Color.RED);
                logEvent("CRITICAL: Emergency mode ACTIVATED. Barriers held open.");
            } else {
                emergencyToggle.setText("Emergency Mode: OFF");
                entryButton.setEnabled(true);
                exitButton.setEnabled(true);
                statusLabel.setText("System Operational - Barriers Normalized");
                statusLabel.setForeground(Color.BLACK);
                logEvent("RESOLVED: Emergency mode deactivated.");
            }
        });

        // --- EVENT HANDLER 4: Administrative Reset ---
        resetButton.addActionListener(e -> {
            occupiedSpaces = 0;
            updateDisplayState();
            statusLabel.setText("System Reset Completed");
            statusLabel.setForeground(Color.BLACK);
            logEvent("ADMIN: Occupancy reset to 0.");
        });

        controlPanel.add(entryButton);
        controlPanel.add(exitButton);
        controlPanel.add(emergencyToggle);
        controlPanel.add(resetButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Window setup
        setSize(520, 480);
        setLocationRelativeTo(null);
        logEvent("System initialized. Total Capacity: " + TOTAL_CAPACITY);
    }

    private void updateDisplayState() {
        int available = TOTAL_CAPACITY - occupiedSpaces;
        capacityLabel.setText("Available Spaces: " + available + " / " + TOTAL_CAPACITY);

        if (available == 0) {
            capacityLabel.setForeground(Color.RED);
            statusLabel.setText("GARAGE FULL");
        } else {
            capacityLabel.setForeground(Color.DARK_GRAY);
        }
    }

    private void logEvent(String eventText) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        eventLog.append("[" + timestamp + "] " + eventText + "\n");
        eventLog.setCaretPosition(eventLog.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new ParkingGaragePrototype().setVisible(true);
        });
    }
}