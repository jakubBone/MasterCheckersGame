public class Game {

    private char playerPawn = 'O';
    private char computerPawn = 'X';

    private char [][] validComputerFields = new char[8][8];
    private char [][] validPlayerFields = new char[8][8];

    private static char[][] board = new char[8][8];

    static void printBoard() {
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.print("  ");
        for (int i = 0; i < 8; i++)
            System.out.print("+---");
        System.out.print("+\n");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.print("|\n  ");
            for (int k = 0; k < 8; k++)
                System.out.print("+---");
            System.out.print("+\n");
        }
    }

    void setStartPosition() {
        for (int i = 0; i < 3;) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = computerPawn;
                j += 2;
            }
            i+=2;
        }
    }

    void setValidField() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0)
                    validComputerFields[i][j] = board[i][j];
                else
                    validPlayerFields[i][j] = board [i][j];
            }
        }

    }
}