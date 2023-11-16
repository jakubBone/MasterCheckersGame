public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        game.printBoard();
        game.locateComputerPawns();
        game.locatePlayerPawns();
        game.printBoard();

        game.performMove();
        game.printBoard();
    }
}
