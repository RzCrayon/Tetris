import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class TetrisRunner extends JFrame implements ActionListener, MenuListener {

    DrawPanel displayPanel;
    InfoPanel infoPanel;
    // THE ENTIRETY OF THE GAME'S SIZING IS DEPENDENT ON HOW LARGE THE INDIVIDUAL
    // RECTS OF EACH PIECE ARE.
    private int rectSize = 25;
    private int width = rectSize * 12;
    private int height = 2 * width;

    private JMenuBar menuBar;
    private JMenu help;
    private JMenu mode;
    private JMenu pause;
    private JRadioButtonMenuItem darkMode;
    private JRadioButtonMenuItem lightMode;

    public TetrisRunner() {

        // sets the screen's layout
        setSize(width + 215, height + 60);
        setTitle("Tetris");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // creates the display and info panels and adds them to the main screen
        displayPanel = new DrawPanel(new Point(width, height), 25);
        infoPanel = new InfoPanel(height, displayPanel);

        add(displayPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.EAST);

        // builds the menu bar for the screen and adds it to the screen
        menuBar = buildMenuBar();
        add(menuBar, BorderLayout.NORTH);

        setVisible(true);

    }

    public static void main(String[] args) {

        TetrisRunner tetris = new TetrisRunner();

    }

    /**
     * Builds the menu bar to be used in the screen
     * 
     * @return
     */
    public JMenuBar buildMenuBar() {

        menuBar = new JMenuBar();

        // adds a pause, help and mode feature to the menu bar.
        pause = new JMenu("Pause");
        pause.addMenuListener(this);

        help = new JMenu("Help");
        help.addMenuListener(this);

        mode = new JMenu("Mode");
        mode.addMenuListener(this);

        // adds a dark and light mode feature to the mode feature of the menu bar.
        darkMode = new JRadioButtonMenuItem("Dark Mode", true);
        darkMode.addActionListener(this);
        lightMode = new JRadioButtonMenuItem("Light Mode", false);
        lightMode.addActionListener(this);
        ButtonGroup group = new ButtonGroup();
        group.add(darkMode);
        group.add(lightMode);

        mode.add(darkMode);
        mode.add(lightMode);

        menuBar.add(mode);
        menuBar.add(help);
        menuBar.add(pause);

        return menuBar;
    }

    /**
     * Used for switching between modes, not press recognition on the menu bar
     * itself.
     * Updates both the infoPanel's display and the displayPanel's display based on
     * the mode and unpauses the game when a mode is selected.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(darkMode)) {
            displayPanel.setBackground(new Color(20, 20, 20));
            infoPanel.setBackground(new Color(20, 20, 20));
        } else if (e.getSource().equals(lightMode)) {
            displayPanel.setBackground(Color.WHITE);
            infoPanel.setBackground(Color.WHITE);
        }
    }

    /**
     * If the mode feature is selected it will allow the user to switch between dark
     * mode and light mode...
     * If the help feature is selected it will provide the user with a JOptionPane
     * that provides directions, an okay button is the only option on that
     * JOptionPane and as soon as the okay button is pressed the pane will be closed
     * and game unpaused
     * Both the above features will pause the game when selected and unpause it when
     * something is done.
     * If the pause/unpause feature is selected the game will pause/unpause based on
     * the current state of this button and the button's display will toggle as
     * well.
     */
    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource().equals(mode)) {
            displayPanel.pause();
        } else if (e.getSource().equals(help)) {
            displayPanel.pause();
            JOptionPane.showConfirmDialog(null,
                    "- Use the left and right arrow keys to move your pieces left and right\n- Use the up arrow key to rotate the piece\n- Use the down arrow to speed up piece movement\n- Clear rows to earn points and increase your level",
                    null, JOptionPane.CLOSED_OPTION);
        } else if (e.getSource().equals(pause)) {
            if (pause.getText().equals("Pause")) {
                pause.setText("Un-pause");
                displayPanel.pause();
            } else {
                pause.setText("Pause");
            }
        }
    }

    /**
     * Any time either the mode or help feature on the menu panel are deselected it
     * will unpause the game.
     */
    @Override
    public void menuDeselected(MenuEvent e) {
        if (e.getSource().equals(mode)) {
            displayPanel.unpause();
            displayPanel.setFocusable(true);
        } else if (e.getSource().equals(help)) {
            displayPanel.unpause();
            displayPanel.setFocusable(true);
        } else if (e.getSource().equals(pause)) {
            displayPanel.setFocusable(true);
            if (pause.getText().equals("Pause")) {
                displayPanel.unpause();
            }
        }
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }

}
