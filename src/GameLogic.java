
import java.util.Scanner;

public class GameLogic {
    Scanner scanner = new Scanner(System.in);
    public static String currentPlayer = "Human";
    Computer computer = new Computer();
    Player player = new Player();
    Board board = new Board();

    public void askForMove() {
        while (Computer.compPawnNumbers > 0 && Player.playerPawnNumbers > 0 &&
                !(board.arePawnsOnFinalSide(Player.playerPAWN, 2, 7)) &&
                !(board.arePawnsOnFinalSide(Computer.computerPAWN, 0, 5))) {

            Board.printBoard();
            boolean validMove = false;

            while (!validMove) {
                try {
                    System.out.println("SELECT PAWN:");
                    System.out.print("Row: ");
                    player.pawnRow = scanner.nextInt();
                    System.out.print("Column: ");
                    player.pawnColumn = scanner.nextInt();

                    if (player.ifPawnSelected()) {
                        validMove = true;
                    } else {
                        System.out.println("It's not your pawn. Please try again ;) \n");

                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer ;) \n");
                    scanner.next();
                }
            }

            // Ruch pionem
            while (currentPlayer.equals("Human")) {
                try {
                    System.out.println("MOVE TO: ");
                    System.out.print("Row: ");
                    player.movementRow = scanner.nextInt();
                    System.out.print("Column: ");
                    player.movementColumn = scanner.nextInt();
                    System.out.println();
                    player.performPawnMove();
                    //currentPlayer = "Computer";
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer. \n");
                    scanner.next();
                }
            }
            computer.findPawnAndMove();
            currentPlayer = "Human";
        }
        board.printGameOverMessage();
    }




}


