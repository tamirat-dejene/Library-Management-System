import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LibraryView extends JFrame {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 650;

    private JPanel backgroundPanel;
    private JPanel northPanel;
    private JPanel westPanel;
    private JPanel southPanel;

    private JButton loginButton;
    private JButton signUpButton;
    private JButton logoutButton;
    private JButton showEmployeeButton;
    private JButton showUserButton;

    private JButton addBookButton;
    private JButton verifyUserButton;
    private JButton allBookButton;
    private JButton lendBookButton;
    private JButton showLendHistory;

    public LibraryView() {
        super("Library Management System");
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Helper methods
        setBackgroundImage(); 
        setAppIcon();

        // Top Navigation Bar
        createTopNavigationBar();
          
        // Left Navigation Bar
        createLeftNavigationBar();   

        pack();
    }

    private void createLeftNavigationBar() {
        westPanel = new JPanel(new GridLayout(3, 1, 5, 30));
        westPanel.setBackground(new Color(255, 255, 255, 0));

        ImageIcon userIcon = new ImageIcon("../resources/UserIcon.png");
        userIcon.setImage(userIcon.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH));    
        verifyUserButton = new JButton("Verify User", userIcon);
        verifyUserButton.setLayout(new BorderLayout());
        verifyUserButton.setVerticalTextPosition(JButton.BOTTOM);
        verifyUserButton.setHorizontalTextPosition(JButton.CENTER);
        verifyUserButton.setIconTextGap(10);
        verifyUserButton.setFocusable(false);
        westPanel.add(verifyUserButton);
        
        lendBookButton = new JButton("Lend-Book");
        lendBookButton.setFocusable(false);
        westPanel.add(lendBookButton);

        showLendHistory = new JButton("Lend-History");
        showLendHistory.setFocusable(false);
        westPanel.add(showLendHistory);

        add(westPanel, BorderLayout.WEST);
    }

    private void createTopNavigationBar() {
        northPanel = new JPanel(new GridLayout(1, 5, 30, 5));
        northPanel.setBackground(new Color(255, 255, 255, 0));
        northPanel.setMinimumSize(new Dimension(50, 70));

        showUserButton = new JButton("Users List");
        showUserButton.setFocusable(false);
        northPanel.add(showUserButton);

        ImageIcon bookImage = new ImageIcon("../resources/BookIcon.png");
        bookImage.setImage(bookImage.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH));
        
        addBookButton = new JButton("Add Book", bookImage);
        addBookButton.setLayout(new BorderLayout());
        addBookButton.setVerticalTextPosition(JButton.CENTER);
        addBookButton.setHorizontalTextPosition(JButton.RIGHT);
        addBookButton.setFocusable(false);
        northPanel.add(addBookButton);


        loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        northPanel.add(loginButton);

        signUpButton = new JButton("Signup");
        signUpButton.setFocusable(false);
        northPanel.add(signUpButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFocusable(false);
        northPanel.add(logoutButton);
        
        add(northPanel, BorderLayout.NORTH);
    }

    private void setBackgroundImage() {
        ImageIcon bgImage = new ImageIcon("../resources/LibraryImage.jpg");
        JPanel backgroundPanel = new JPanel() {
            private final Image backgroundImage = bgImage.getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        setContentPane(backgroundPanel);
        getContentPane().setLayout(new BorderLayout());
    }

    private void setAppIcon() {
        Image appIcon = new ImageIcon("../resources/LibraryIcon3.png").getImage();
        setIconImage(appIcon);
    }


    // private void addComponent(Component component, int gridy, int gridx, int gridheight, int gridwidth) {
    //     GridBagConstraints gbc = new GridBagConstraints();

    //     gbc.gridx = gridx;
    //     gbc.gridy = gridy;
    //     gbc.gridheight = gridheight;
    //     gbc.gridwidth = gridwidth;
    //     gbc.fill = GridBagConstraints.BOTH;
    //     gbc.weightx = 1;
    //     gbc.weighty = (gridy == 0) ? 1 : 1;

    //     add(component, gbc);
    // }
    
    
    public static void main(String[] args) {
        LibraryView gui = new LibraryView();
        gui.setVisible(true);

    }

    
}
