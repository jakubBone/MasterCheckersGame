
import java.util.Scanner;

public class GameLogic {
    Scanner scanner = new Scanner(System.in);
    public static String currentPlayer = "Human";
    Computer computer = new Computer();
    Player player = new Player();
    Board board = new Board();


    public void playGame(){
        System.out.println("Welcome in the MasterCheckersGame \n ");


        while (true) {
            System.out.println("Let's play the game!");
            System.out.println("Start game: 1");
            System.out.println("The rules: 2");
            System.out.println("Exit: 0");
            System.out.print("Enter: ");
            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Bye bye!");
                    return;
                case "1":
                    askForMove();
                    return;
                case "2":
                    System.out.println("Rules printing... \n");
                    break;
                default:
                    System.out.println("Error, please try again.");
                    break;
            }

        }
    }

        /*while(scanner.hasNextInt()){
            //int input = scanner.nextInt();
            String input = String.valueOf(scanner.nextLine());
            switch (input) {
                case "1" :
                    askForMove();
                    break;
                case "2" :
                    System.out.println("Rules printing...");
                    break;
                case "0" :
                    System.out.println("Bye bye!");
                    return;
                default:
                    System.out.print("Error try again: ");
            }
        }*/



    public void askForMove() {
        while (Computer.compPawnNumbers > 0 && Player.playerPawnNumbers > 0 &&
                !(board.arePawnsOnFinalSide(Player.playerPAWN, 2, 7)) &&
                !(board.arePawnsOnFinalSide(Computer.computerPAWN, 0, 5))) {

                        Board.printBoard();
            boolean validMove = false;

            while (!validMove) {
                try {
                    System.out.println("COMPUTER: " + Player.playerPawnNumbers);
                    System.out.println("PLAYER: " + Computer.compPawnNumbers);
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


