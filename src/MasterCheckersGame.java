public class MasterCheckersGame {
    public static void main(String[] args) {
        Board board = new Board();
        Player movement = new Player();

        GameLogic gameLogic = new GameLogic();
        board.locateComputerPawns();
        board.locatePlayerPawns();
        board.setEmptyFields();
        /*Board.board[4][2] = Computer.computerPAWN; // Player added to test pawn capturing
        Board.board[1][1] = Board.emptyField; // added to Player test pawn capturing
        Board.board[0][2] = Board.emptyField; // added to Player test pawn capturing
        Board.board[5][5] = Board.emptyField; // added to Player test pawn capturing
        Board.board[4][4] = Computer.computerPAWN; // added to Player test pawn capturing
        Board.board[0][2] = Player.playerQueenPAWN; // added to Player test pawn capturing
        Board.board[1][3] = ' '; // added to Player test pawn capturing
        Board.board[3][3] = 'O' ; // added to Player test pawn capturing*/

        Board.board[3][1] = 'O' ; // added to Computer test pawn capturing*/
        Board.board[3][5] = 'O' ; // added to Computer test pawn capturing*/




        gameLogic.askForMove();



    }
}
