public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board();
        board.locateComputerPawns();
        board.locatePlayerPawns();
        board.setEmptyFields();
        Board.board[4][2] = Player.computerPAWN; // added for test pawn capturing
        game.askForMove();



    }
}
