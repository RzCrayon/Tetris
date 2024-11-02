import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.Timer;

public class DrawPanel extends JPanel implements ActionListener, KeyListener {

    // HANDLES THE ACTUAL GAME LOGIC, INCLUDING THE DISPLAY PANEL

    private Point[][] grid;
    private int rectSize;
    private int speed = 400;
    private Point size;
    private Point trueSize;

    private Timer timer;
    private Timer drawTimer;

    private Image tealIcon = Toolkit.getDefaultToolkit().getImage("images/tealTetrisPiece.png");
    private Image blueIcon = Toolkit.getDefaultToolkit().getImage("images/blueTetrisPiece.png");
    private Image redIcon = Toolkit.getDefaultToolkit().getImage("images/redTetrisPiece.png");
    private Image orangeIcon = Toolkit.getDefaultToolkit().getImage("images/orangeTetrisPiece.png");
    private Image yellowIcon = Toolkit.getDefaultToolkit().getImage("images/yellowTetrisPiece.png");
    private Image greenIcon = Toolkit.getDefaultToolkit().getImage("images/greenTetrisPiece.png");
    private Image purpleIcon = Toolkit.getDefaultToolkit().getImage("images/purpleTetrisPiece.png");
    private Image grayIcon = Toolkit.getDefaultToolkit().getImage("images/grayTetrisPiece.png");

    private Piece nextPiece;
    private ArrayList<Piece> blocks = new ArrayList<Piece>();

    private int score = 10;
    private int threshold = 250;
    private int level = 1;

    private boolean gameOver = false;

    private JFileChooser chooser = new JFileChooser();
    private int highScore = 0;

    public DrawPanel(Point size, int rectSize) {

        this.rectSize = rectSize;
        this.trueSize = size;
        this.size = new Point(size.x - 2 * rectSize, size.y - 2 * rectSize); // sets the screen's size based on the
                                                                             // rectSize

        grid = new Point[size.x / rectSize][size.y / rectSize]; // creates a new screen

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Point(i * rectSize, j * rectSize);
            }
        }

        setPreferredSize(new Dimension(size.x, size.y));

        // two different timers
        // timer is used for constant movement downwards and moves at a slower pace than
        // drawTimer which is used to update the screen and monitor non-constant piece
        // movement
        timer = new Timer(speed, this);
        timer.start();

        drawTimer = new Timer(speed / 8, this);
        drawTimer.start();

        addKeyListener(this); // allows the code to recognise key presses

        setFocusable(true);
        requestFocusInWindow();

        setBackground(new Color(20, 20, 20));

        spawn(); // spawns a new piece

        // retrieves the existing highScore, if there is none the highScore is simply
        // set to 0.
        chooser.setSelectedFile(new File("highScore.txt"));
        setHighScore();

    }

    /**
     * Draws the screen
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // paints the background

        // paints the gray piece border to the screen
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < trueSize.y / rectSize; i++) {
                if (i % 2 == 0) {
                    g.drawImage(grayIcon, rectSize * i / 2, 0, rectSize, rectSize, this);
                    g.drawImage(grayIcon, rectSize * i / 2, trueSize.y - rectSize, rectSize, rectSize, this);
                }
                g.drawImage(grayIcon, 0, rectSize * i, rectSize, rectSize, this);
                g.drawImage(grayIcon, trueSize.x - rectSize, rectSize * i, rectSize, rectSize, this);
            }
        }

        // paints each rect of each piece based on the complete list of pieces and each
        // individual piece's colour as an identifying trait to use the correct png.;

        for (int i = 0; i < blocks.size(); i++) {
            Point[] rects = blocks.get(i).getRectStructure();
            for (int j = 0; j < rects.length; j++) {
                if (rects[j] != null) {
                    Color color = blocks.get(i).getColor();
                    if (color.equals(Color.CYAN)) {
                        g.drawImage(tealIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    } else if (color.equals(Color.BLUE)) {
                        g.drawImage(blueIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    } else if (color.equals(Color.RED)) {
                        g.drawImage(redIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    } else if (color.equals(Color.GREEN)) {
                        g.drawImage(greenIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    } else if (color.equals(Color.ORANGE)) {
                        g.drawImage(orangeIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    } else if (color.equals(Color.MAGENTA)) {
                        g.drawImage(purpleIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    } else if (color.equals(Color.YELLOW)) {
                        g.drawImage(yellowIcon, rects[j].x * rectSize + rectSize, rects[j].y * rectSize + rectSize,
                                rectSize, rectSize, this);
                    }
                }
            }
        }

    }

    /**
     * Actions to be carried out with every iteration of the timers
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) { // if the action is called by the constant timer, simply move the only active
                                           // piece, the last piece in the complete list at a constant rate downward.

            blocks.get(blocks.size() - 1).constantMove();

            repaint();
        }
        // if the action is called by the drawTimer...
        else if (e.getSource().equals(drawTimer)) {
            if (blocks.get(blocks.size() - 1).isLocked()) {
                // SEE PIECE CLASS FOR BETTER DESCRIPTION OF SPAWNCOUNT
                // if the last piece is locked but the spawn count is less than 10, increment
                // the spawn count
                // otherwise spawn a new piece and update every piece's copy of the complete
                // list of all pieces.
                if (blocks.get(blocks.size() - 1).getSpawnCount() < 10) {
                    blocks.get(blocks.size() - 1).setSpawnCount(blocks.get(blocks.size() - 1).getSpawnCount() + 1);
                } else {
                    spawn();
                    for (int i = 0; i < blocks.size(); i++) {
                        blocks.get(i).updateBlocks(blocks);
                    }
                }
            }

            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) { // if the game is not over...
            if (e.getKeyCode() == e.VK_SPACE) {
                if (timer.isRunning()) {
                    pause();
                } else {
                    unpause();
                }
            }
            if (e.getKeyCode() == e.VK_DOWN) { // if the down key is pressed increase the constantMove rate and if the
                                               // piece can not move further automatically lock it.
                if (blocks.get(blocks.size() - 1).isLocked()) {
                    blocks.get(blocks.size() - 1).setSpawnCount(10);
                }
                timer.setDelay(speed / 5);
            } else if (e.getKeyCode() == e.VK_RIGHT && timer.getDelay() != speed / 4) { // if the player is not speeding
                                                                                        // their piece and clicks on the
                                                                                        // right key...
                // if the piece can not move to the right and is locked, automatically spawn a
                // new piece.
                // otherwise move the piece to the right and reset the spawn count
                if (!blocks.get(blocks.size() - 1).canMoveRight() && blocks.get(blocks.size() - 1).isLocked()) {
                    blocks.get(blocks.size() - 1).setSpawnCount(10);
                } else {
                    if (blocks.get(blocks.size() - 1).isLocked()) {
                        blocks.get(blocks.size() - 1).setSpawnCount(0);
                    }
                    blocks.get(blocks.size() - 1).moveRight();
                }
            } else if (e.getKeyCode() == e.VK_LEFT && timer.getDelay() != speed / 4) { // if the player is not speeding
                                                                                       // their piece and clicks on the
                                                                                       // left key...
                // if the piece can not move to the left and is locked, automatically spawn a
                // new piece.
                // otherwise move the piece to the left and reset the spawn count
                if (!blocks.get(blocks.size() - 1).canMoveLeft() && blocks.get(blocks.size() - 1).isLocked()) {
                    blocks.get(blocks.size() - 1).setSpawnCount(10);
                } else {
                    if (blocks.get(blocks.size() - 1).isLocked()) {
                        blocks.get(blocks.size() - 1).setSpawnCount(0);
                    }
                    blocks.get(blocks.size() - 1).moveLeft();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            // If the player lets go of the down key it will return the constantMove rate to
            // its regular speed
            if (e.getKeyCode() == e.VK_DOWN) {
                timer.setDelay(speed);
            }
            // if the player presses the down key and lets go the piece will be rotate
            // function for the active piece will be called, this way the player has to
            // click twice on the down key if they want to rotate the piece twice.
            else if (e.getKeyCode() == e.VK_UP) {
                blocks.get(blocks.size() - 1).rotate();
            }
        }
    }

    public void spawn() {

        // if there exist pieces on the board and the active piece has at least moved or
        // there exist no pieces on the board...
        if ((blocks.size() > 0 && !(blocks.get(blocks.size() - 1).getTopMostPiece().y == 0
                || blocks.get(blocks.size() - 1).getTopMostPiece().y == 1)) || blocks.size() == 0) {

            // calculates the player's score by clearing rows and counting the number of
            // rows cleared
            int linesCleared = 0;

            for (int i = 0; i < size.y / rectSize; i++) {
                if (isRowFilled(i)) {
                    removeFromRow(i);
                    removeMove(i);
                    linesCleared++;
                }
            }

            if (linesCleared == 1) {
                score += 40 * level;
            } else if (linesCleared == 2) {
                score += 100 * level;
            } else if (linesCleared == 3) {
                score += 300 * level;
            } else if (linesCleared == 4) {
                score += 1200 * level;
            }

            // if there exist pieces on the board, spawn will place the previously next
            // piece, now current piece on the board...
            if (blocks.size() > 0) {
                blocks.add(nextPiece);
            }

            // calculates the new speed, level and points threshold for the level to move on
            // to the next level if the current points threshold has been surpassed.
            int temp = level;

            if (score > threshold) {
                level++;
                threshold = threshold + threshold * 3 / 4;
            }

            if (temp != level) {
                speed -= speed / 8;
            }

            // randomly selects a piece.
            selectPiece();

            // sets the constant move timer to the newly updated speed which also updates
            // the drawTimer's speed
            timer.setDelay(speed);

            // if there exist no pieces on the board, add the next piece and spawn a new
            // one.
            if (blocks.size() == 0) {
                blocks.add(nextPiece);
                selectPiece();
            }

        } else if (((blocks.get(blocks.size() - 1).getTopMostPiece().y == 0
                || blocks.get(blocks.size() - 1).getTopMostPiece().y == 1)) || blocks.size() == 0) { // if the game is
                                                                                                     // technically
                                                                                                     // over...
            if (gameOver) { // if the game has been recorded has being over...

                // display the endgame message, display a new high score message if the player
                // has beaten the previous highscore and update the highscore.
                String message = "Would you like to play again?";

                if (score > highScore) {
                    updateHighScore();
                    message = "NEW HIGH SCORE\n" + message;
                }

                // if the user would like to play again...
                if (InputMethods.getChoiceYNJOP(message)) {

                    // allow the user to select on which level they would like to start...
                    String input = InputMethods.getDropdownMenuChoiceJOP_modified(
                            "On which level would you like to start?",
                            new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
                    if (input == null) { // if the pplayer exits this screen take them back to the, "would you like to
                                         // play again?" screen
                        gameOver = true;
                    } else {
                        switch (input) {
                            case "1":
                                level = 1;
                                break;
                            case "2":
                                level = 2;
                                break;
                            case "3":
                                level = 3;
                                break;
                            case "4":
                                level = 4;
                                break;
                            case "5":
                                level = 5;
                                break;
                            case "6":
                                level = 6;
                                break;
                            case "7":
                                level = 7;
                                break;
                            case "8":
                                level = 8;
                                break;
                            case "9":
                                level = 9;
                                break;
                            case "10":
                                level = 10;
                                break;
                        }
                        // restart the game by resetting all variables and updating the points threshold
                        // and speed based on the level the user selected.
                        threshold = 250;
                        speed = 400;
                        for (int i = 0; i < level - 1; i++) {
                            threshold = threshold + threshold * 3 / 4;
                            speed -= speed / 8;
                        }
                        gameOver = false;
                        timer.restart();
                        drawTimer.restart();
                        blocks.clear();
                        score = 0;
                        spawn();
                    }
                } else {
                    System.exit(0); // if the user chooses not to play again... exit the entire program
                }
            } else {
                gameOver = true; // record that the game is over
            }
        }

    }

    /**
     * Randomly selects a piece and assigns it to nextPiece.
     */
    public void selectPiece() {
        int piece = (int) (Math.random() * 7);

        switch (piece) {
            case 0:
                nextPiece = new TealPiece(size, rectSize);
                break;
            case 1:
                nextPiece = new YellowPiece(size, rectSize);
                break;
            case 2:
                nextPiece = new PurplePiece(size, rectSize);
                break;
            case 3:
                nextPiece = new BluePiece(size, rectSize);
                break;
            case 4:
                nextPiece = new OrangePiece(size, rectSize);
                break;
            case 5:
                nextPiece = new RedPiece(size, rectSize);
                break;
            case 6:
                nextPiece = new GreenPiece(size, rectSize);
                break;
        }
    }

    /**
     * Checks to see if a row is filled and needs to be cleared
     * 
     * @param row the row to be checked.
     * @return returns true if the row is filled, false otherwise.
     */
    public boolean isRowFilled(int row) {
        int count = 0;
        for (int i = 0; i < blocks.size(); i++) {
            count += blocks.get(i).countNumInRow(row);
        }
        // if the number of rects that exist in that row from all the pieces on the
        // board equals the screen's width divided by each rect's width... return true
        if (count * rectSize == size.x) {
            return true;
        }
        return false;
    }

    /**
     * Removes from a given row all the rects in that row.
     * 
     * @param row the row to be cleared
     */
    public void removeFromRow(int row) {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).removeInRow(row);
        }
    }

    /**
     * Moves all the rects above the cleared row down 1 step
     * 
     * @param row the row that was cleared
     */
    public void removeMove(int row) {
        for (int i = 0; i < blocks.size(); i++) { // for every piece on the board...
            for (int j = 0; j < blocks.get(i).getRectStructure().length; j++) { // get the piece's rect structure
                if (blocks.get(i).getRectStructure()[j] != null && blocks.get(i).getRectStructure()[j].y < row) { // if
                                                                                                                  // the
                                                                                                                  // rect
                                                                                                                  // has
                                                                                                                  // not
                                                                                                                  // already
                                                                                                                  // been
                                                                                                                  // deleted
                                                                                                                  // and
                                                                                                                  // its
                                                                                                                  // position
                                                                                                                  // is
                                                                                                                  // less
                                                                                                                  // than
                                                                                                                  // the
                                                                                                                  // cleared
                                                                                                                  // row's
                                                                                                                  // y
                                                                                                                  // position
                    blocks.get(i).getRectStructure()[j].y++; // move the rect down 1 space
                }
            }
        }
    }

    /**
     * Allows the next piece to be accessible in infoPanel
     * 
     * @return returns the nextPiece
     */
    public Piece getNextPiece() {
        return nextPiece;
    }

    /**
     * Allows the score to be accessible in infoPanel
     * 
     * @return returns the score
     */
    public String getScore() {
        return String.valueOf(score);
    }

    /**
     * Allows the current level to be accessible in infoPanel
     * 
     * @return returns the current level
     */
    public String getLevel() {
        return String.valueOf(level);
    }

    /**
     * Allows the current high score to be accessible in infoPanel
     * 
     * @return returns the current high score.
     */
    public String getHighScore() {
        return String.valueOf(highScore);
    }

    /**
     * Gets the background colour of the display screen, to be used in TetrisRunner
     * 
     * @return returns the screen's background colour.
     */
    public Color getBackgroundColour() {
        return this.getBackground();
    }

    /**
     * Pauses the program, to be used in TetrisRunner
     */
    public void pause() {
        timer.stop();
        drawTimer.stop();
    }

    /**
     * Unpauses the program, to be used in TetrisRunner.
     */
    public void unpause() {
        timer.start();
        drawTimer.start();
    }

    /**
     * Sets the highScore in the class by reading the highScore from a txt file
     * using a JFileChooser to make read easier and sets the highScore if it exists.
     */
    public void setHighScore() {
        try {
            Scanner reader = new Scanner(chooser.getSelectedFile());
            while (reader.hasNextLine()) {
                try {
                    highScore = Integer.parseInt(reader.nextLine());
                } catch (Exception e) {
                }
            }
            reader.close();

        } catch (IOException e) {
        }

    }

    /**
     * Updates the highScore by writing it to the file using the file path and then
     * setting the runner's current high score monitor
     * Even if it can not successfully update the highScore it will still update the
     * runner's current high score monitor.
     */
    public void updateHighScore() {
        try {
            Path path = Path.of(chooser.getSelectedFile().getPath());
            Files.writeString(path, String.valueOf(score));
            setHighScore();
        } catch (IOException e) {
        }

        setHighScore();
    }

}
