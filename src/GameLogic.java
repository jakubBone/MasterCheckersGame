import java.util.Scanner;

public class GameLogic {
    Scanner scanner = new Scanner(System.in);
    public static String currentPlayer = "Human";
    Computer computer = new Computer();
    Player player = new Player();

    public void askForMove() {
        while (Computer.compPawnNumbers > 0 || Player.playerPawnNumbers > 0 ||
                Board.arePawnsOnFinalSide(Player.playerPAWN, 0,2, Player.playerPawnNumbers) ||
                Board.arePawnsOnFinalSide(Computer.computerPAWN, 6,8, Computer.compPawnNumbers)) {
            Board.printBoard();
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            player.pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            player.pawnColumn = scanner.nextInt();
            if (player.ifPawnSelected()) {
                System.out.println();
                while (currentPlayer.equals("Human")) {
                    System.out.println("WHICH FIELD TO MOVE?");
                    System.out.print("Select field row: ");
                    player.movementRow = scanner.nextInt();
                    System.out.print("Select field column: ");
                    player.movementColumn = scanner.nextInt();
                    System.out.println();
                    player.performPawnMove();
                }
            } else
                System.out.println("Incorrect choice. It's not your pawn");
            computer.findPawnAndMove();
            currentPlayer = "Human";
        }
        System.out.println("Game over");
    }

}
