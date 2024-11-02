import java.awt.Color;
import java.awt.Point;

public class TealPiece extends Piece {

    // SEE SUPERCLASS FOR ANY METHOD REFERENCES
    // CONSTRUCTOR SIMPLY CREATES A NEW PIECE USING THE SCREENSIZE AND RECTSIZE
    // THE ROTATE ALGORITHM USED IN ROTATE IS SPECIFIC TO THIS PIECE AND SIMPLY
    // REARRANGES THE RECTS IN A SPECIFIC ORDER.

    int state = 1; // 2 states

    public TealPiece(Point screenSize, int rectSize) {
        super(new Point[] { new Point(screenSize.x / rectSize / 2 - 2, 0),
                new Point(screenSize.x / rectSize / 2 - 1, 0), new Point(screenSize.x / rectSize / 2, 0),
                new Point(screenSize.x / rectSize / 2 + 1, 0) }, screenSize, rectSize);
    }

    @Override
    public boolean rotatable() {

        if (!super.isLocked()) {
            if (state == 1) {
                if (rects[1].y > 0 && (rects[1].y + 1) * rectSize < screenSize.y) {
                    return true;
                }
            } else {
                if (rects[1].x > 0 && (rects[1].x + 1) * rectSize < screenSize.x) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void rotate() {

        if (rotatable()) {
            Point[] temp = super.createCopyOfRects();
            int prevState = state;
            if (state == 1) { // horizontal
                if ((rects[1].y + 1) * rectSize == screenSize.y - rectSize) {
                    temp[0].x += 2;
                    temp[0].y -= 2;
                    temp[1].x++;
                    temp[1].y--;
                    temp[3].x--;
                    temp[3].y += 1;
                } else {
                    temp[0].x++;
                    temp[0].y--;
                    temp[2].x--;
                    temp[2].y++;
                    temp[3].x -= 2;
                    temp[3].y += 2;
                }
                state = 2;
            } else { // vertical
                if ((rects[1].x + 1) * rectSize == screenSize.x - rectSize) {
                    temp[0].x -= 2;
                    temp[0].y += 2;
                    temp[1].x--;
                    temp[1].y++;
                    temp[3].x++;
                    temp[3].y--;
                } else {
                    temp[0].x--;
                    temp[0].y++;
                    temp[2].x++;
                    temp[2].y--;
                    temp[3].x += 2;
                    temp[3].y -= 2;
                }
                state = 1;
            }
            if (!super.checkRotateCollision(temp)) {
                rects = temp;
            } else {
                state = prevState;
            }
        }
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }

}
