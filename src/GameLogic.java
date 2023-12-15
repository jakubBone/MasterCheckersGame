import java.util.Scanner;

public class GameLogic {
    Scanner scanner = new Scanner(System.in);
    static String currentPlayer = "Human";
    Computer computer = new Computer();
    Player player = new Player();

    void askForMove() {
        while (Computer.compPawnNumbers > 0 || Player.playerPawnNumbers > 0) {
            Board.printBoard();
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            player.pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            player.pawnColumn = scanner.nextInt();
            if (player.ifPlayerPawnSelected()) {
                System.out.println();
                while (currentPlayer.equals("Human")) {
                    System.out.println("WHICH FIELD TO MOVE?");
                    System.out.print("Select field row: ");
                    player.movementRow = scanner.nextInt();
                    System.out.print("Select field column: ");
                    player.movementColumn = scanner.nextInt();
                    System.out.println();
                    player.performMove();
                }
            } else
                System.out.println("Incorrect choice. It's not your pawn");
            computer.performMove();
            currentPlayer = "Human";
        }
        System.out.println("Game over");
    }
}
