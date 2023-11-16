public class MasterCheckersGame {
    public static void main(String[] args) {
        Game game = new Game();
        Game.printBoard();
        game.locateComputerPawns();
        game.locatePlayerPawns();
        Game.printBoard();


    }
}
