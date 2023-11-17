import java.util.Scanner;

public class Game {

    Scanner scanner = new Scanner(System.in);
    public int pawnNumbers;
    static char[][] board = new char[8][8];
    HumanPlayer player = new HumanPlayer();
    ComputerPlayer computer = new ComputerPlayer();

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
                if(!(board[i][j] == 'X') && !(board[i][j] == 'O'))
                    board[i][j] = ' ';
            }
        }
    }


    void performMove() {
        while (true) {
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            int pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            int pawnColumn = scanner.nextInt();
            System.out.println();
            System.out.println(pawnRow + pawnColumn);
            while (true) {
                System.out.println("WHICH FIELD TO MOVE?");
                System.out.print("Select field row: ");
                int movementRow = scanner.nextInt();
                System.out.print("Select field column: ");
                int movementColumn = scanner.nextInt();
                System.out.println();
                if ((movementRow == pawnRow - 1) && (movementColumn == pawnColumn - 1 || movementColumn == pawnColumn + 1)) {
                    board[movementRow][movementColumn] = 'O';
                    board[pawnRow][pawnColumn] = ' ';
                    printBoard();
                    break;

                } else
                    System.out.println("Incorrect field. Please, select the field diagonally :) \n");

            }
            System.out.println("Play again");
        }
    }
    // isolate the if above to ifMovementValid() method
    //(movementRow == pawnRow - 1) && (movementColumn == pawnColumn -1 || movementColumn == pawnColumn + 1) )

}