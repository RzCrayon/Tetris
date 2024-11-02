
import java.awt.Color;
import java.awt.Point;

public class YellowPiece extends Piece {

    // SEE SUPERCLASS FOR ANY METHOD REFERENCES
    // CONSTRUCTOR SIMPLY CREATES A NEW PIECE USING THE SCREENSIZE AND RECTSIZE

    public YellowPiece(Point screenSize, int rectSize) {
        super(new Point[] { new Point(screenSize.x / rectSize / 2 - 1, 0), new Point(screenSize.x / rectSize / 2, 0),
                new Point(screenSize.x / rectSize / 2 - 1, 1), new Point(screenSize.x / rectSize / 2, 1) }, screenSize,
                rectSize);
    }

    @Override
    public Color getColor() {
        return Color.yellow;
    }

}
