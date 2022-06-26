import java.util.Scanner;

public class Game {

    Board board;

    public Game(int mode) {
        switch(mode){
            case 1:
                board = new Board(9,9,10);
                break;
            case 2:
                board = new Board(16,16,40);
                break;
            case 3:
                board = new Board(16,30,99);
                break;
        }
    }

    public void play(Scanner s) {
        boolean gameOver = false;
        s.nextLine();
        while(!gameOver) {
//            printBoardMines();
            printBoard();
            System.out.print("Enter coordinates x,y to click tile. Enter x,y,f to place a flag: ");
            String[] input = s.nextLine().split(",");
            int r = Integer.parseInt(input[0]);
            int c = Integer.parseInt(input[1]);
            if(input.length == 3) {
                board.flagTile(r,c);
            } else if (board.getTiles()[r][c].getVisible()) { //Already visible,
                System.out.println("Tile already visible!");
            } else if (board.clickTile(r,c)) {
                System.out.println("You hit a mine! Gameover.");
                gameOver = true;
            } else if (board.checkWon()) {
                System.out.println("You flagged all the mines. You win!");
                gameOver = true;
            }
        }
    }

    private void printBoard() {
        StringBuilder b = new StringBuilder();
        b.append("   ");
        for(int n = 0; n < board.getWidth(); n++) {
            b.append(n).append(" ");
        }
        b.append("\n");
        for(int i = 0; i < board.getHeight(); i++) {
            b.append(i).append(" ");
            for(int j = 0; j < board.getWidth(); j++) {
                Tile t = board.getTiles()[i][j];
                if(t.getVisible() && t.getNumNearbyMines() > 0) { //If visible and a border, show number
                    b.append(" ").append(t.getNumNearbyMines());
                } else if (t.getVisible() && !t.getBorder()) {
                    b.append(" ").append("-");
                } else if (t.getFlagged()){
                    b.append(" f");
                } else {
                    b.append(" #");
                }
            }
            b.append("\n");
        }
        System.out.print(b);
    }

    private void printBoardMines() { //Used for debugging only
        StringBuilder b = new StringBuilder();
        b.append("  ");
        for(int n = 0; n < board.getWidth(); n++) {
            b.append(n).append(" ");
        }
        b.append("\n");
        for(int i = 0; i < board.getHeight(); i++) {
            b.append(i);
            for(int j = 0; j < board.getWidth(); j++) {
                if(board.getTiles()[i][j].getMine()) {
                    b.append(" B");
                } else {
                    b.append(" ").append(board.getTiles()[i][j].getNumNearbyMines());
                }
            }
            b.append("\n");
        }
        System.out.println(b);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter gamemode easy, medium, hard (1,2,3):");
        Game g = new Game(s.nextInt());
        g.play(s);
    }
}
