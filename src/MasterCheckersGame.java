public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board();
        board.locateComputerPawns();
        board.locatePlayerPawns();
        board.setEmptyFields();
        Board.board[4][2] = Player.computerPAWN; // added to test pawn capturing
        Board.board[1][1] = Board.emptyField; // added to test pawn capturing
        Board.board[0][2] = Board.emptyField; // added to test pawn capturing
        game.askForMove();



    }
}
