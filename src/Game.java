import java.util.Scanner;

public class Game {

    Scanner scanner = new Scanner(System.in);

    private char playerPawn = 'O';
    private char computerPawn = 'X';

    private char[][] board = new char[8][8];

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
                        board[i][j] = computerPawn;
                        j++;
                    }
                } else {
                    for (int j = 1; j < 8; j++) {
                        board[i][j] = computerPawn;
                        j++;
                    }
                }
        }
    }

    void locatePlayerPawns() {
        for (int i = 5; i < 8; i++) {
                if (i % 2 == 0) {
                    for (int j = 0; j < 8; j++) {
                        board[i][j] = playerPawn;
                        j++;
                    }
                } else {
                    for (int j = 1; j < 8; j++) {
                        board[i][j] = playerPawn;
                        j++;
                    }
                }
        }
    }

    void setEmptyFields() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j] != 'X' && board[i][j] != 'O')
                    board[i][j] = ' ';
            }
        }
    }


    void performMove(){
        System.out.println("Which pawn you want to move?");
        System.out.print("Select row: ");
        int pawnRow = scanner.nextInt();
        System.out.println();
        System.out.print("Select column: ");
        int pawnColumn = scanner.nextInt();
        System.out.println();

        System.out.println("Choose the field to move");
        System.out.print("Select row: ");
        int row = scanner.nextInt();
        System.out.println();
        System.out.print("Select column: ");
        int column = scanner.nextInt();
        System.out.println();
        if(ifMovementValid(board[row][column])) // board 2 4
            board[row][column] = 'O';
    }

    boolean ifMovementValid(char field){
        return field == ' ';
    }

}