import java.util.ArrayList;

public class Board {
    final static char[][] board = new char[9][9];
    final static char emptyField = ' ';

    public static void printBoard() {
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

    public void prepareBoard() {
        locatePlayerPawns();
        locateComputerPawns();
        setEmptyFields();
    }

    private void locateComputerPawns() {
        for (int i = 0; i < 3; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = Computer.computerPAWN;
                    j++;
                }
            } else {
                for (int j = 1; j < 8; j++) {
                    board[i][j] = Computer.computerPAWN;
                    j++;
                }
            }
        }
    }

    private void locatePlayerPawns() {
        for (int i = 5; i < 8; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = Player.playerPAWN;
                    j++;
                }
            } else {
                for (int j = 1; j < 8; j++) {
                    board[i][j] = Player.playerPAWN;
                    j++;
                }
            }
        }
    }

    private void setEmptyFields() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(board[i][j] == Computer.computerPAWN) && !(board[i][j] == Player.playerPAWN))
                    board[i][j] = emptyField;
            }
        }
    }

    public static boolean arePawnsOnFinalSide(char pawn, int startRow, int endRow, int pawnNumbers) {
        ArrayList<int[]> playerList = new ArrayList<>();

        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == pawn) {
                    playerList.add(new int[]{i, j});
                }
            }
        }
        return (playerList.size() == pawnNumbers);
    }

}
