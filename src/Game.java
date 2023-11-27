import java.util.Scanner;

public class Game {

    Scanner scanner = new Scanner(System.in);
    static char[][] board = new char[8][8];
    HumanPlayer player = new HumanPlayer();
    ComputerPlayer computer = new ComputerPlayer();

    private char emptyField = ' ';

    static String currentPlayer = "Human";

    void printBoard() {
        System.out.println("    0   1   2   3   4   5   6   7");
        System.out.print("  ");
        for (int i = 0; i < 8; i++)
            System.out.print("+---");
        System.out.print("+\n");
        for (int i = 0; i < 8; i++) {
            System.out.print((i) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.print("|\n  ");
            for (int k = 0; k < 8; k++)
                System.out.print("+---");
            System.out.print("+\n");
        }
    }

    void locateComputerPawns() {
        for (int i = 0; i < 3; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = computer.PAWN;
                    j++;
                }
            } else {
                for (int j = 1; j < 8; j++) {
                    board[i][j] = computer.PAWN;
                    j++;
                }
            }
        }
    }

    void locatePlayerPawns() {
        for (int i = 5; i < 8; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = player.PAWN;
                    j++;
                }
            } else {
                for (int j = 1; j < 8; j++) {
                    board[i][j] = player.PAWN;
                    j++;
                }
            }
        }
    }

    void setEmptyFields() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(board[i][j] == computer.PAWN) && !(board[i][j] == player.PAWN))
                    board[i][j] = emptyField;
            }
        }
    }

    void askForMove() {
        while (ComputerPlayer.compPawnNumbers > 0 || HumanPlayer.playerPawnNumbers > 0) {
            printBoard();
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            int pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            int pawnColumn = scanner.nextInt();

            if (board[pawnRow][pawnColumn] == player.PAWN) {
                System.out.println();
                while (currentPlayer.equals("Human")) {
                    System.out.println("WHICH FIELD TO MOVE?");
                    System.out.print("Select field row: ");
                    int movementRow = scanner.nextInt();
                    System.out.print("Select field column: ");
                    int movementColumn = scanner.nextInt();
                    System.out.println();
                    performMove(movementRow, movementColumn, pawnRow, pawnColumn);
                }
            } else
                System.out.println("Incorrect choice. It's not your pawn");

            // computerMove() y change player after movement
            currentPlayer = "Human";
        }
        System.out.println("Game over");
    }

    void performMove(int movementX, int movementY, int pawnX, int pawnY) {
        int moveLeft = pawnY - 1;
        int moveRight = pawnY + 1;
        int rowAbove = pawnX - 1;
        int doubleRowAbove = pawnX - 2;
        int transitionFieldX = movementX + 1;
        int transitionFieldY = movementY - 1;

        if (isMovementValid(pawnX, pawnY, movementX, movementY)) {
            ComputerPlayer.compPawnNumbers -= 1;
            currentPlayer = "Computer";
            if (movementX == rowAbove && (movementY == moveLeft || movementY == moveRight)) {
                board[movementX][movementY] = player.PAWN;
                board[pawnX][pawnY] = emptyField;
                System.out.println("0"); // flag 0
            } else if (movementX == doubleRowAbove && board[movementX][movementY] == emptyField) {
                if (board[transitionFieldX][transitionFieldY] == computer.PAWN) {
                    board[movementX][movementY] = player.PAWN;
                    board[transitionFieldX][transitionFieldY] = emptyField;
                    board[pawnX][pawnY] = emptyField;
                    System.out.println("1"); // flag 1
                } else
                System.out.println("You cannot jump 2 field above");
            }
        } else
            if(!(board[movementX][movementY] == emptyField))
                System.out.println("There is another pawn on the field. Please, try again");
            else
                System.out.println("Incorrect field. Please, select the field diagonally :) \n");
    }

    boolean isMovementValid(int pawnX, int pawnY, int movementX, int movementY) {
        int doubleRowAbove = pawnX - 2;
        return ((movementX >= doubleRowAbove) && !(movementY == pawnY) && board[movementX][movementY] == emptyField);
    }

    // use condition below
    //(movementRow == pawnRow - 1) && (movementColumn == pawnColumn -1 || movementColumn == pawnColumn + 1) )
}
