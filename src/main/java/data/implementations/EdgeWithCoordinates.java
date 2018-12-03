package data.implementations;

public class EdgeWithCoordinates {

    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private int length;

    public EdgeWithCoordinates(int fromX, int fromY, int toX, int toY, int length) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.length = length;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public int getLength() {
        return length;
    }
}
