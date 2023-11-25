public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        game.locateComputerPawns();
        game.locatePlayerPawns();
        game.setEmptyFields();
        game.printBoard();
        game.performMove();
        game.printBoard();



    }
}
