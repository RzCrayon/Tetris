import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author ank54
 *
 */
/**
 * @author ank54
 *
 */
public class Piece {

    // USED AS THE BASIC STRUCTURE FOR ALL PIECES
    // ALL FUNCTIONS FOUND IN EACH PIECE ARE SPECIFIC OVERRIDEN FUNCTIONS OF THOSE
    // FOUND IN THE SUPER CLASS, THIS CLASS
    // EACH INDIVIDUAL PIECE SUBCLASS HAS A PRIVATE STATE VARIABLE - THIS VARIABLE
    // SIMPLY MONITORS THE PIECE'S ROTATION STATE TO KNOW WHAT POSITION THE PIECE IS
    // IN AT ALL TIMES.

    protected Point[] rects; // a list of rectangles, identifiable by position that make up the piece.
    protected Point screenSize; // the screen's size
    protected int rectSize; // the size of each rect in the piece

    // THE WAY THAT THE TIMER WORKS TO AVOID AN IMMEDIATE GENERATION OF A NEW PIECE
    // AS SOON AS THIS ONE IS LOCKED A SPAWN COUNT IS USED TO DELAY THE SPAWNING OF
    // A NEW PIECE BY 10 MILLISECONDS.
    // THIS GIVES THE PLAYER 10 MILLISECONDS TO MOVE THE PIECE LEFT OR RIGHT,
    // ALLOWING THE PLAYER TO MOVE THE PIECE EVEN IF IT CAN NOT MOVE DOWN FURTHER...
    // IF THE CODE RECOGNISES THAT THE PLAYER CAN NOT MOVE THE PIECE EITHER TO THE
    // LEFT OR RIGHT THAN IT WILL AUTOMATICALLY SET THE SPAWN COUNT TO 10 TRIGGERING
    // THE IMMEDIATE SPAWN OF A NEW PIECE.
    // IF THE PLAYER HAS NOT CARRIED OUT ANY ACTIONS IN 10 MILLISECONDS, A NEW PIECE
    // WILL BE SPAWNED.
    private int spawnCount = 0; // always has to reach 10

    private ArrayList<Piece> blocks = new ArrayList<Piece>(); // a list of every single other piece in the game

    public Piece(Point[] rects, Point screenSize, int rectSize) {
        this.rects = rects;
        this.screenSize = screenSize;
        this.rectSize = rectSize;
    }

    /**
     * @return returns the positions of each rect in the piece
     */
    public Point[] getRectStructure() {
        return rects;
    }

    /**
     * Called with every iteration of the timer
     * Updates the copy list of all the pieces on the board, if a piece has been
     * completely deleted it will remove it from the list copy to avoid errors.
     * 
     * @param blocks An array list of all the pieces on the board
     */
    public void updateBlocks(ArrayList<Piece> blocks) {
        this.blocks = blocks;
        for (int i = 0; i < blocks.size(); i++) {
            int count = 0;
            for (int j = 0; j < blocks.get(i).getRectStructure().length; j++) {
                if (blocks.get(i).getRectStructure()[j] == null) {
                    count++;
                }
            }
            if (count == 4) {
                blocks.remove(i);

            }
        }
    }

    /**
     * Determines whether or not a piece is positioned and immovable.
     * 
     * @return Returns true if a piece is locked.
     */
    public boolean isLocked() {

        int lowestIndex = -1;
        int lowestYValue = Integer.MIN_VALUE;

        for (int i = 0; i < rects.length; i++) {
            // checks every rect in the piece making sure that it still exists and the rect
            // underneath it does not belong to another piece...
            // if so it will start a 10 millisecond count to lock the piece
            // otherwise it will find the lowest rect and check to see if the lowest rect's
            // position is within the screen, if it is not it will check to see if the piece
            // can move left or right and start a 10 millisecond count to lock the piece.
            if (rects[i] != null && isFilledPoint(new Point(rects[i].x, rects[i].y + 1))) {
                if (spawnCount == 0) {
                    spawnCount++;
                }
                return true;
            }
            if (rects[i] != null && rects[i].y > lowestYValue) {

                lowestIndex = i;
                lowestYValue = rects[i].y;
            }
        }

        if (lowestIndex > -1 && (rects[lowestIndex].y + 2) * rectSize > screenSize.y) {
            if (spawnCount == 0) {
                if (canMoveLeft() || canMoveRight()) {
                    spawnCount++;
                } else {
                    spawnCount = 10;
                }
            }
            return true;
        }
        return false;

    }

    /**
     * Allows the spawn count to be changed in the DrawPanel class
     * 
     * @param spawnCount spawn count
     */
    public void setSpawnCount(int spawnCount) {
        this.spawnCount = spawnCount;
    }

    /**
     * Allows the spawn count to be accessed in the DrawPanel class
     * 
     * @return returns the spawn count
     */
    public int getSpawnCount() {
        return spawnCount;
    }

    /**
     * Finds the uppermost rect of the piece
     * 
     * @return returns the uppermost rect's index
     */
    public Point getTopMostPiece() {
        int topMostIndex = 0;
        int topMostValue = rects[0].y;

        for (int i = 0; i < rects.length; i++) {
            if (rects[i] != null && rects[i].y < topMostValue) {
                topMostIndex = i;
                topMostValue = rects[i].y;
            }
        }

        return rects[topMostIndex];
    }

    /**
     * Moves the piece downwards at a constant rate if it is not locked.
     */
    public void constantMove() {
        if (!isLocked()) {
            for (int i = 0; i < rects.length; i++) {
                if (rects[i] != null) {
                    rects[i].y++;
                }
            }
        }
    }

    /**
     * Determines whether or not the piece can move right
     * 
     * @return returns true if it can.
     */
    public boolean canMoveRight() {

        int rightMostIndex = -1;
        int rightMostValue = Integer.MIN_VALUE;

        // finds the right most rect index and for every rect it checks it checks to see
        // whether or not the rect to the right of that is already filled by another
        // piece's rect, returns false if so.
        for (int i = 0; i < rects.length; i++) {
            if (isFilledPoint(new Point(rects[i].x + 1, rects[i].y))) {
                return false;
            }
            if (rects[i].x > rightMostValue) {
                rightMostValue = rects[i].x;
                rightMostIndex = i;
            }
        }

        // if the right most rect can move to the right and the piece is still within
        // the screen, returns true.
        if (!((rects[rightMostIndex].x + 2) * rectSize > screenSize.x)) {

            return true;
        }
        return false;
    }

    /**
     * Determines whether or not the piece can move left
     * 
     * @return Returns true if it can
     */
    public boolean canMoveLeft() {
        int leftMostIndex = -1;
        int leftMostValue = Integer.MAX_VALUE;

        // finds the left most rect index and for every rect it checks it checks to see
        // whether or not the rect to the left of that is already filled by another
        // piece's rect, returns false if so.
        for (int i = 0; i < rects.length; i++) {
            if (isFilledPoint(new Point(rects[i].x - 1, rects[i].y))) {
                // movable = false;
                return false;
            }
            if (rects[i].x < leftMostValue) {
                leftMostValue = rects[i].x;
                leftMostIndex = i;
            }
        }

        // if the left most rect can move to the left and piece is still within the
        // screen, returns true.
        if (!((rects[leftMostIndex].x - 1) * rectSize < 0)) {
            return true;
        }
        return false;
    }

    /**
     * Moves the piece 1 tile to the right.
     */
    public void moveRight() {
        if (canMoveRight()) {
            for (int i = 0; i < rects.length; i++) {
                rects[i].x++;
            }
        }
    }

    /**
     * Moves the piece 1 tile to the left.
     */
    public void moveLeft() {
        if (canMoveLeft()) {
            for (int i = 0; i < rects.length; i++) {
                rects[i].x--;
            }
        }
    }

    /**
     * Checks to see if a given point, identifying a rect is a rect of another
     * piece.
     * 
     * @param point the rect to be considered.
     * @return returns true if the point is already filled, false otherwise.
     */
    public boolean isFilledPoint(Point point) {

        for (int i = 0; i < blocks.size(); i++) { // checks each piece in the copy of the complete list of pieces that
                                                  // each piece object ahs.
            if (!this.equals(blocks.get(i))) { // checks to see if the piece being considered is this one, if not,
                                               // continues,
                for (int j = 0; j < blocks.get(i).getRectStructure().length; j++) { // checks every rect in the
                                                                                    // considered piece
                    if (blocks.get(i).getRectStructure()[j] != null
                            && blocks.get(i).getRectStructure()[j].equals(point)) { // if the considered rect has not
                                                                                    // been deleted and is equal to the
                                                                                    // given point it returns true.
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Creates a copy of the piece to be used in the subclasses when checking for
     * rotation
     * 
     * @return returns an immutable copy of the piece.
     */
    public Point[] createCopyOfRects() {
        Point[] temp = new Point[rects.length];
        for (int i = 0; i < rects.length; i++) {
            temp[i] = new Point(rects[i]);
        }
        return temp;
    }

    /**
     * Checks to see if the rotation of this piece causes a collision with another
     * piece.
     * 
     * @param temp a rotated copy of the current piece.
     * @return returns true if a collision is caused, false otherwise.
     */
    public boolean checkRotateCollision(Point[] temp) {
        for (int i = 0; i < temp.length; i++) {
            if (isFilledPoint(temp[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of rects this piece has in a given row of the screen.
     * 
     * @param row the row of the screen to be checked
     * @return returns the number of rects this piece has in the row
     */
    public int countNumInRow(int row) {
        int count = 0;
        for (int i = 0; i < rects.length; i++) {
            if (rects[i] != null && rects[i].y == row) {
                count++;
            }
        }
        return count;
    }

    /**
     * Deletes every rect of this piece in a given row of the screen
     * 
     * @param row the row to be checked
     */
    public void removeInRow(int row) {
        for (int i = 0; i < rects.length; i++) {
            if (rects[i] != null && rects[i].y == row) {
                rects[i] = null;
            }
        }
    }

    /**
     * Checks to see if one piece equals another by comparing their rect
     * configurations.
     */
    @Override
    public boolean equals(Object o) {
        Piece other = (Piece) (o);
        return rects.equals(other.getRectStructure());
    }

    /**
     * Determines whether or not a piece is rotatable
     * Overridden in each subclass of piece
     * IN EACH SUBCLASS: Checks to make sure that if when the piece is rotated all
     * rects of the piece will still be in the screen and makes sure that the piece
     * is not already locked, returns true if so.
     * 
     * @return Returns false as a default, however the return value is subject to
     *         each piece's individual subclass computations.
     */
    public boolean rotatable() {
        return false;
    }

    /**
     * Rotates a piece
     * Overridden in each subclass of piece.
     * IN EACH SUBCLASS: If the piece is rotatable, it creates a copy of the piece
     * using createCopyOfRects() and rotates this copy depending on the piece's
     * state, then it checks to see that all the rects of this rotated piece do not
     * overlap with any other pieces' rects, if so then it rotates the actual piece,
     * otherwise it simply ignores the call.
     */
    public void rotate() {
    }

    /**
     * Returns the piece's colour for display and identification purposes
     * 
     * @return Returns null as default but is overridden in each subclass and each
     *         subclass has their own respective colours.
     */
    public Color getColor() {
        return null;
    }

}
