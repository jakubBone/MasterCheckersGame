public class MasterCheckersGame {
    public static void main(String[] args) {
        Board board = new Board();
        GameLogic gameLogic = new GameLogic();
        board.prepareBoard();

        Board.board[0][0] = Computer.computerPAWN; // Player added to test pawn capturing
        Board.board[1][2] = Board.emptyField; // added to Player test pawn capturing
        Board.board[0][4] = Board.emptyField; // added to Player test pawn capturing
        Board.board[0][6] = Board.emptyField; // added to Player test pawn capturing
        Board.board[1][1] = Computer.computerPAWN; // added to Player test pawn capturing
        Board.board[1][3] = Computer.computerPAWN; // added to Player test pawn capturing
        Board.board[1][5] = Board.emptyField; // added to Player test pawn capturing
        Board.board[1][7] = Board.emptyField; ; // added to Player test pawn capturing*/
        Board.board[2][0] = Computer.computerPAWN; // Player added to test pawn capturing
        Board.board[2][2] = Board.emptyField; // added to Player test pawn capturing
        Board.board[2][4] = Board.emptyField; // added to Player test pawn capturing
        Board.board[2][6] = Board.emptyField; // added to Player test pawn capturing
        Board.board[5][1] = Player.playerPAWN; // added to Player test pawn capturing
        Board.board[5][3] = Board.emptyField;; // added to Player test pawn capturing
        Board.board[5][5] = Board.emptyField; // added to Player test pawn capturing
        Board.board[5][7] = Player.playerPAWN;// added to Player test pawn capturing*/
        Board.board[6][0] = Board.emptyField; // Player added to test pawn capturing
        Board.board[6][2] = Board.emptyField; // added to Player test pawn capturing
        Board.board[6][4] = Board.emptyField; // added to Player test pawn capturing
        Board.board[6][6] = Board.emptyField; // added to Player test pawn capturing
        Board.board[7][1] = Board.emptyField; // added to Player test pawn capturing
        Board.board[7][3] = Board.emptyField; // added to Player test pawn capturing
        Board.board[7][5] = Board.emptyField;; // added to Player test pawn capturing
        Board.board[7][7] = Board.emptyField; // added to Player test pawn capturing*/

        gameLogic.askForMove();



    }
}
