public class Tile {

    private boolean isBorder = false;
    private boolean isVisible = false;
    private boolean isFlagged = false;
    private boolean isMine = false;
    private int numNearbyMines;

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public boolean getFlagged() {
        return isFlagged;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean getMine() {
        return isMine;
    }

    public void setNumNearbyMines(int numNearbyMines) {
        this.numNearbyMines = numNearbyMines;
    }

    public int getNumNearbyMines() {
        return numNearbyMines;
    }

    public boolean getBorder() {
        return isBorder;
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }
}
