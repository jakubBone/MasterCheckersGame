public class Game {

    private char playerPawn = 'O';
    private char computerPawn = 'X';

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
}