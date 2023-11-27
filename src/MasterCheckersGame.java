public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        ComputerPlayer c = new ComputerPlayer();
        game.locateComputerPawns();
        game.locatePlayerPawns();
        game.setEmptyFields();
        Game.board[4][2] = c.PAWN; // added for test pawn capturing
        game.askForMove();



    }
}
