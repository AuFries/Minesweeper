import java.util.*;

public class Board {
    Tile[][] tiles;
    int height;
    int width;
    int numMines;
    boolean firstClick = true;

    public Board(int height, int width, int numMines) {
        this.height = height;
        this.width = width;
        this.numMines = numMines;
        tiles = new Tile[height][width];
        //Initialize all the tiles to blank tile objects
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                tiles[i][j] = new Tile();
            }
        }
        placeMines();
        updateNumNearbyMines();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return  width;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private void placeMines() {
        int minesToPlace = numMines;
        Random rand = new Random();

        while(minesToPlace > 0) {
            int r = rand.nextInt(height); //Choose random row and col to place the mines
            int c = rand.nextInt(width);
            if(!tiles[r][c].getMine()) { //If a mine isn't already there place it
                tiles[r][c].setMine(true);
                minesToPlace--;
            }
        }
    }

    private void updateNumNearbyMines() { //Updates the number of nearby mines for each tile
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int nearbyMines = 0;
                for(int x = i-1; x <= i+1; x++) {
                    for(int y = j-1; y <= j+1; y++) {
                        if(x >= 0 && y >= 0 && x < height && y < width && tiles[x][y].getMine()) {
                            nearbyMines++;
                        }
                    }
                }
                tiles[i][j].setNumNearbyMines(nearbyMines);
            }
        }
    }

    private void updateVisible(int r, int c) { //Spreads outwards from the clicked tile
        Queue<int []> q = new LinkedList<>();
        q.add(new int[]{r,c});
        addNeighbors(r,c,q);
        while(!q.isEmpty()) {
            int row = q.peek()[0];
            int col = q.peek()[1];
            if(!tiles[row][col].getVisible() && tiles[row][col].getNumNearbyMines() > 0) {//If not visible yet and the number is greater than 0, set visible
                tiles[row][col].setVisible(true);
                tiles[row][col].setBorder(true);
            } else if (!tiles[row][col].getVisible() && tiles[row][col].getNumNearbyMines() == 0){ //Otherwise add neighbors to queue and set visible
                tiles[row][col].setVisible(true);
                addNeighbors(row,col,q);
            }
            q.remove();
        }
    }

    private void addNeighbors(int r,int c,Queue<int[]> q) { //Adds all neighbor cells to the queue
        for(int i = r-1; i <= r + 1; i++) {
            for(int j = c-1; j <= c + 1; j++) {
                if(i >=0 && j >= 0 && i < height && j < width && !(r == i && c == j)) {
                    q.add(new int[]{i,j});
                }
            }
        }
    }

    public boolean clickTile(int r, int c) { //returns true if clicked a mine otherwise updatesVisible tiles
        if(tiles[r][c].getMine()) {
            return true;
        } else if(firstClick){ //If no nearby mines on first click, update visbility
            boolean nearbyMines = false;
            for(int i = r-1; i <= r+1; i++) {
                for(int j = c-1; j <= c+1; j++) {
                    if(i >= 0 && j >= 0 && i < height && j < width && tiles[i][j].getMine()) {
                        nearbyMines = true;
                    }
                }
            }
            if(!nearbyMines) {
                updateVisible(r,c);
            }
            firstClick = !firstClick;
        } else if (tiles[r][c].getNumNearbyMines() > 0) {
            tiles[r][c].setVisible(true);
        } else{
            updateVisible(r,c);
        }
        return false;
    }

    public void flagTile(int r, int c) { //Toggles flagging a tile
        tiles[r][c].setFlagged(!tiles[r][c].getFlagged());
    }

    public boolean checkWon() { //Checks if all the mines on the grid are properly flagged
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(tiles[i][j].getMine() && !tiles[i][j].getFlagged()) {
                    return false;
                }
            }
        }
        return true;
    }
}
