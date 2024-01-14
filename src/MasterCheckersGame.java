

public class MasterCheckersGame {
    public static void main(String[] args) {

        Board board = new Board();
        GameLogic gameLogic = new GameLogic();

        board.prepareBoard();
        gameLogic.playGame();
    }
}
