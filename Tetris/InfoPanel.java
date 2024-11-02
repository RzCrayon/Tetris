import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class InfoPanel extends JPanel implements ActionListener {

    // HANDLES THE DISPLAY ON THE INFOMRATION PANEL ADJACENT TO THE GAME PANEL

    private int screenHeight;

    // CREATES AN EXACT COPY OF THE DISPLAY PANEL AS THAT IN THE TETRIS RUNNER SO
    // THAT IT CAN ACCESS ALL THE INDIVIDUAL VALUES OF THE DISPLAYPANEL INCLUDING
    // THE HIGH SCORE, LEVEL AND SCORE SO THAT IT DOES NOT HAVE TO USE MULTIPLE
    // STATIC METHODS FOR INTER CLASS COMMUNCATIONS.
    private DrawPanel displayPanel;

    private final int width = 200;

    private Timer timer;

    private Image whiteNextPieceIcon = Toolkit.getDefaultToolkit().getImage("images/nextPieceIcon.png");
    private Image whiteScoreIcon = Toolkit.getDefaultToolkit().getImage("images/scoreIcon.png");
    private Image whiteLevelIcon = Toolkit.getDefaultToolkit().getImage("images/levelIcon.png");
    private Image whiteHighScoreIcon = Toolkit.getDefaultToolkit().getImage("images/highSCoreIcon.png");

    private Image blackNextPieceIcon = Toolkit.getDefaultToolkit().getImage("images/nextPieceIcon2.png");
    private Image blackScoreIcon = Toolkit.getDefaultToolkit().getImage("images/scoreIcon2.png");
    private Image blackLevelIcon = Toolkit.getDefaultToolkit().getImage("images/levelIcon2.png");
    private Image blackHighScoreIcon = Toolkit.getDefaultToolkit().getImage("images/highSCoreIcon2.png");

    private Image tealIcon = Toolkit.getDefaultToolkit().getImage("images/wholeTealTetrisPiece.png");
    private Image blueIcon = Toolkit.getDefaultToolkit().getImage("images/wholeBlueTetrisPiece.png");
    private Image orangeIcon = Toolkit.getDefaultToolkit().getImage("images/wholeOrangeTetrisPiece.png");
    private Image yellowIcon = Toolkit.getDefaultToolkit().getImage("images/wholeYellowTetrisPiece.png");
    private Image greenIcon = Toolkit.getDefaultToolkit().getImage("images/wholeGreenTetrisPiece.png");
    private Image redIcon = Toolkit.getDefaultToolkit().getImage("images/wholeRedTetrisPiece.png");
    private Image purpleIcon = Toolkit.getDefaultToolkit().getImage("images/wholePurpleTetrisPiece.png");

    private Image whiteZeroIcon = Toolkit.getDefaultToolkit().getImage("images/0Icon.png");
    private Image whiteOneIcon = Toolkit.getDefaultToolkit().getImage("images/1Icon.png");
    private Image whiteTwoIcon = Toolkit.getDefaultToolkit().getImage("images/2Icon.png");
    private Image whiteThreeIcon = Toolkit.getDefaultToolkit().getImage("images/3Icon.png");
    private Image whiteFourIcon = Toolkit.getDefaultToolkit().getImage("images/4Icon.png");
    private Image whiteFiveIcon = Toolkit.getDefaultToolkit().getImage("images/5Icon.png");
    private Image whiteSixIcon = Toolkit.getDefaultToolkit().getImage("images/6Icon.png");
    private Image whiteSevenIcon = Toolkit.getDefaultToolkit().getImage("images/7Icon.png");
    private Image whiteEightIcon = Toolkit.getDefaultToolkit().getImage("images/8Icon.png");
    private Image whiteNineIcon = Toolkit.getDefaultToolkit().getImage("images/9Icon.png");

    private Image blackZeroIcon = Toolkit.getDefaultToolkit().getImage("images/0Icon2.png");
    private Image blackOneIcon = Toolkit.getDefaultToolkit().getImage("images/1Icon2.png");
    private Image blackTwoIcon = Toolkit.getDefaultToolkit().getImage("images/2Icon2.png");
    private Image blackThreeIcon = Toolkit.getDefaultToolkit().getImage("images/3Icon2.png");
    private Image blackFourIcon = Toolkit.getDefaultToolkit().getImage("images/4Icon2.png");
    private Image blackFiveIcon = Toolkit.getDefaultToolkit().getImage("images/5Icon2.png");
    private Image blackSixIcon = Toolkit.getDefaultToolkit().getImage("images/6Icon2.png");
    private Image blackSevenIcon = Toolkit.getDefaultToolkit().getImage("images/7Icon2.png");
    private Image blackEightIcon = Toolkit.getDefaultToolkit().getImage("images/8Icon2.png");
    private Image blackNineIcon = Toolkit.getDefaultToolkit().getImage("images/9Icon2.png");

    public InfoPanel(int screenHeight, DrawPanel displayPanel) {

        this.screenHeight = screenHeight;
        this.displayPanel = displayPanel;

        // repaint timer to update the screen constantly
        timer = new Timer(20, this);
        timer.start();

        setPreferredSize(new Dimension(width, screenHeight));
        setBackground(new Color(20, 20, 20));
    }

    /**
     * Paints the screen
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // paints the screen's background

        int xFactor = width / 8; // scale factor

        // ALL DRAW COMMANDS BELOW ARE DEPENDENT ON THE SCREEN'S COLOUR, SO THAT IT CAN
        // KNOW WHICH VERSION OF THE INFORMATION ICONS TO DISPLAY, THE LIGHT OR DARK
        // VERSIONS.

        // draws the nextPiece rect
        if (!this.getBackground().equals(Color.WHITE)) {
            g.setColor(Color.WHITE);
            g.fillRect(xFactor, 75 + xFactor, width - 2 * xFactor, width - 2 * xFactor);

            g.setColor(new Color(15, 15, 15));
            g.fillRect(xFactor + 2, 75 + xFactor + 2, width - 2 * (xFactor + 2), width - 2 * (xFactor + 2));
        } else {
            g.setColor(new Color(15, 15, 15));
            g.fillRect(xFactor, 75 + xFactor, width - 2 * xFactor, width - 2 * xFactor);

            g.setColor(Color.WHITE);
            g.fillRect(xFactor + 2, 75 + xFactor + 2, width - 2 * (xFactor + 2), width - 2 * (xFactor + 2));
        }

        Point imageSize = null;
        Image icon = null;

        // draws the nextPiece using a prebuilt model of each piece and the nextPiece's
        // colour as an identifier
        if (displayPanel.getNextPiece().getColor().equals(Color.CYAN)) {
            imageSize = new Point(((width - 2 * xFactor) - xFactor * 2),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.25));
            icon = tealIcon;
        } else if (displayPanel.getNextPiece().getColor().equals(Color.BLUE)) {
            imageSize = new Point((int) (((width - 2 * xFactor) - xFactor * 2) * 0.75),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.5));
            icon = blueIcon;
        } else if (displayPanel.getNextPiece().getColor().equals(Color.ORANGE)) {
            imageSize = new Point((int) (((width - 2 * xFactor) - xFactor * 2) * 0.75),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.5));
            icon = orangeIcon;
        } else if (displayPanel.getNextPiece().getColor().equals(Color.MAGENTA)) {
            imageSize = new Point((int) (((width - 2 * xFactor) - xFactor * 2) * 0.75),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.5));
            icon = purpleIcon;
        } else if (displayPanel.getNextPiece().getColor().equals(Color.YELLOW)) {
            imageSize = new Point((int) (((width - 2 * xFactor) - xFactor * 2) * 0.5),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.5));
            icon = yellowIcon;
        } else if (displayPanel.getNextPiece().getColor().equals(Color.RED)) {
            imageSize = new Point((int) (((width - 2 * xFactor) - xFactor * 2) * 0.75),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.5));
            icon = redIcon;
        } else if (displayPanel.getNextPiece().getColor().equals(Color.GREEN)) {
            imageSize = new Point((int) (((width - 2 * xFactor) - xFactor * 2) * 0.75),
                    (int) (((width - 2 * xFactor) - xFactor * 2) * 0.5));
            icon = greenIcon;
        }

        g.drawImage(icon, (xFactor + 2) + ((width - 2 * (xFactor + 2)) - imageSize.x) / 2,
                75 + xFactor + 2 + ((width - 2 * (xFactor + 2)) - imageSize.y) / 2, imageSize.x, imageSize.y, this);

        // draws the score display
        manageNumDisplays(g, displayPanel.getScore(), (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 4 * xFactor,
                xFactor);
        // draws the level display
        manageNumDisplays(g, displayPanel.getLevel(), (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 8 * xFactor,
                xFactor);
        // draws the high score display
        manageNumDisplays(g, displayPanel.getHighScore(),
                (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 11 * xFactor, xFactor);

    }

    /**
     * Draws any numerical displays
     * 
     * @param g       the graphics component to be used in drawing the display
     * @param score   the value to be drawn
     * @param height  the y position where the display is to be placed
     * @param xFactor the scaling factor to be used in the display
     */
    public void manageNumDisplays(Graphics g, String score, int height, int xFactor) {
        int numWidth = 0;
        // determines whether or not the numbers need to be compressed to fit in the
        // screen
        if (((width - 2 * (xFactor + 2)) / score.length()) > xFactor) {
            numWidth = xFactor;
        } else {
            numWidth = (width - 2 * (xFactor + 2)) / score.length();
        }
        for (int i = 0; i < score.length(); i++) {
            // determines which mode is turned on and updates the display based on the mode.
            if (!this.getBackground().equals(Color.WHITE)) {
                if (String.valueOf(score.charAt(i)).equals("0")) {
                    g.drawImage(whiteZeroIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("1")) {
                    g.drawImage(whiteOneIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("2")) {
                    g.drawImage(whiteTwoIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("3")) {
                    g.drawImage(whiteThreeIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("4")) {
                    g.drawImage(whiteFourIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("5")) {
                    g.drawImage(whiteFiveIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("6")) {
                    g.drawImage(whiteSixIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("7")) {
                    g.drawImage(whiteSevenIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("8")) {
                    g.drawImage(whiteEightIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("9")) {
                    g.drawImage(whiteNineIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                }
                // draws the labels
                g.drawImage(whiteNextPieceIcon, xFactor, (75 + xFactor) - 5 - xFactor / 2, width - 2 * xFactor,
                        xFactor / 2, this);
                g.drawImage(whiteScoreIcon, xFactor, (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 2 * xFactor,
                        width - 2 * xFactor, xFactor, this);
                g.drawImage(whiteLevelIcon, xFactor, (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 6 * xFactor,
                        width - 2 * xFactor, xFactor, this);
                g.drawImage(whiteHighScoreIcon, xFactor,
                        (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 10 * xFactor, width - 2 * xFactor,
                        xFactor / 2, this);
            } else {
                if (String.valueOf(score.charAt(i)).equals("0")) {
                    g.drawImage(blackZeroIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("1")) {
                    g.drawImage(blackOneIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("2")) {
                    g.drawImage(blackTwoIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("3")) {
                    g.drawImage(blackThreeIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("4")) {
                    g.drawImage(blackFourIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("5")) {
                    g.drawImage(blackFiveIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("6")) {
                    g.drawImage(blackSixIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("7")) {
                    g.drawImage(blackSevenIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("8")) {
                    g.drawImage(blackEightIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                } else if (String.valueOf(score.charAt(i)).equals("9")) {
                    g.drawImage(blackNineIcon, xFactor + i * numWidth, height, numWidth, xFactor, this);
                }
                g.drawImage(blackNextPieceIcon, xFactor, (75 + xFactor) - 5 - xFactor / 2, width - 2 * xFactor,
                        xFactor / 2, this);
                g.drawImage(blackScoreIcon, xFactor, (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 2 * xFactor,
                        width - 2 * xFactor, xFactor, this);
                g.drawImage(blackLevelIcon, xFactor, (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 6 * xFactor,
                        width - 2 * xFactor, xFactor, this);
                g.drawImage(blackHighScoreIcon, xFactor,
                        (75 + xFactor + 2) + (width - 2 * (xFactor + 2)) + 10 * xFactor, width - 2 * xFactor,
                        xFactor / 2, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
