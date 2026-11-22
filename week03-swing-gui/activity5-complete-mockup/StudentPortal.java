import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentPortal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentPortal::createAndShow);
    }

    private static void createAndShow() {
        // ============================================================
        // 1. THE MAIN WINDOW
        // ============================================================
        JFrame frame = new JFrame("Riara University Student Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(920, 620);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ============================================================
        // 1B. CUSTOM MENU BAR (JMenuBar)
        // ============================================================
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 240, 248)); // Soft blush pink

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Download Transcript"));
        fileMenu.add(new JMenuItem("Print Fee Statement"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Exit"));

        JMenu academicsMenu = new JMenu("Academics");
        academicsMenu.add(new JMenuItem("BBIT Course Units"));
        academicsMenu.add(new JMenuItem("Exam Timetable"));
        academicsMenu.add(new JMenuItem("Lecturer Evaluation"));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("Student Support"));
        helpMenu.add(new JMenuItem("About Portal"));

        menuBar.add(fileMenu);
        menuBar.add(academicsMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        // ============================================================
        // 2. HEADER (NORTH) - Vibrant Cyan Blue
        // ============================================================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 180, 216)); // Bright cyan blue
        header.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel title = new JLabel("Riara University Student Portal  |  BBIT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 105, 180)); // Hot pink
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setOpaque(true); // Ensures color displays on macOS
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);

        header.add(title, BorderLayout.WEST);
        header.add(logoutButton, BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        // ============================================================
        // 3. SIDEBAR (WEST) - Soft Blush Pink Panel
        // ============================================================
        JPanel sidebar = new JPanel(new GridLayout(6, 1, 8, 8));
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBackground(new Color(255, 228, 242)); // Cute pastel pink
        sidebar.setBorder(new EmptyBorder(15, 12, 15, 12));

        String[] menuItems = {
            "Dashboard", "My Courses", "Grades & GPA",
            "Fee Statement", "Timetable", "Messages"
        };

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(180, 40, 110)); // Berry pink text
            btn.setFont(new Font("Arial", Font.BOLD, 13));
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            sidebar.add(btn);
        }
        frame.add(sidebar, BorderLayout.WEST);

        // ============================================================
        // 4. MAIN CONTENT AREA (CENTER)
        // ============================================================
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(22, 24, 22, 24));
        content.setBackground(new Color(252, 254, 255)); // Clean icy white

        // Personalized Welcome Message
        JLabel welcome = new JLabel("Welcome back, Kimberly Mwenesi!");
        welcome.setFont(new Font("Arial", Font.BOLD, 23));
        welcome.setForeground(new Color(0, 130, 170)); // Deep cyan
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(welcome);

        JLabel subtitle = new JLabel("Bachelor of Business Information Technology  •  Year 3");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(219, 80, 145)); // Rose pink
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(subtitle);
        content.add(Box.createVerticalStrut(20));

        // ---- Cute Pastel & Vibrant Stat Cards ----
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        cardsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));
        cardsPanel.setBackground(new Color(252, 254, 255));
        cardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardsPanel.add(createStatCard("Current GPA", "3.88", new Color(255, 115, 179))); // Bubblegum pink
        cardsPanel.add(createStatCard("Completed Units", "35 / 56", new Color(0, 180, 216))); // Bright cyan
        cardsPanel.add(createStatCard("Fee Balance", "KES 0 (Cleared)", new Color(165, 120, 235))); // Lavender-lilac

        content.add(cardsPanel);
        content.add(Box.createVerticalStrut(28));

        // ---- Notifications Section ----
        JLabel notifTitle = new JLabel("Campus Updates & Notifications");
        notifTitle.setFont(new Font("Arial", Font.BOLD, 16));
        notifTitle.setForeground(new Color(190, 45, 115)); // Berry pink
        notifTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(notifTitle);
        content.add(Box.createVerticalStrut(12));

        String[] notifications = {
            "• Wireless Networks & Mobile Computing lab report graded",
            "• Corporate World Skills group presentation scheduled this week",
            "• Semester fee clearance confirmed — exam card ready for download",
            "• Library notice: No overdue books on your account"
        };

        for (String note : notifications) {
            JLabel noteLabel = new JLabel(note);
            noteLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            noteLabel.setForeground(new Color(65, 75, 85));
            noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            content.add(noteLabel);
            content.add(Box.createVerticalStrut(7));
        }

        frame.add(content, BorderLayout.CENTER);

        // ============================================================
        // 5. STATUS BAR (SOUTH) - Soft Ice Cyan
        // ============================================================
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(218, 245, 252)); // Pastel cyan
        statusBar.setBorder(new EmptyBorder(6, 12, 6, 12));

        JLabel statusLeft = new JLabel("Status: Logged in as Kimberly Mwenesi (BBIT)");
        statusLeft.setForeground(new Color(0, 110, 140));

        JLabel statusRight = new JLabel("Last login: 22 Nov 2026");
        statusRight.setForeground(new Color(180, 40, 110));

        statusBar.add(statusLeft, BorderLayout.WEST);
        statusBar.add(statusRight, BorderLayout.EAST);
        frame.add(statusBar, BorderLayout.SOUTH);

        // ============================================================
        // 6. SHOW IT!
        // ============================================================
        frame.setVisible(true);
        System.out.println("Customized Student Portal running on: " + Thread.currentThread().getName());
    }

    // Helper method to create a stat card
    private static JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(new EmptyBorder(15, 16, 15, 16));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value, SwingConstants.RIGHT);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 21));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}