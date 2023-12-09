public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board();
        Movement movement = new Movement();
        board.locateComputerPawns();
        board.locatePlayerPawns();
        board.setEmptyFields();
        Board.board[4][2] = Player.computerPAWN; // added to test pawn capturing
        Board.board[1][1] = Board.emptyField; // added to test pawn capturing
        Board.board[0][2] = Board.emptyField; // added to test pawn capturing
        Board.board[5][5] = Board.emptyField; // added to test pawn capturing
        Board.board[4][4] = Player.computerPAWN; // added to test pawn capturing
        Board.board[0][2] = Player.playerQueenPAWN; // added to test pawn capturing
        Board.board[1][3] = ' '; // added to test pawn capturing


        movement.askForMove();



    }
}
