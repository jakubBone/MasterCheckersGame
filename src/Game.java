import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    Movement move = new Movement();

    void askForMove() {
        while (Player.compPawnNumbers > 0 || Player.playerPawnNumbers > 0) {
            Board.printBoard();
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            move.pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            move.pawnColumn = scanner.nextInt();
            if (Board.board[move.pawnRow][move.pawnColumn] == Player.playerPAWN) {
                System.out.println();
                while (Player.currentPlayer.equals("Human")) {
                    System.out.println("WHICH FIELD TO MOVE?");
                    System.out.print("Select field row: ");
                    move.movementRow = scanner.nextInt();
                    System.out.print("Select field column: ");
                    move.movementColumn = scanner.nextInt();
                    System.out.println();
                    move.performMove();
                }
            } else
                System.out.println("Incorrect choice. It's not your pawn");
            // computerMove() y change player after movement
            Player.currentPlayer = "Human";
        }
        System.out.println("Game over");
    }

}

