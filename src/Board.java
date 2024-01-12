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
    public boolean arePawnsOnFinalSide(char pawn, int startRow, int endRow) {
        ArrayList<Character> playerList = new ArrayList<>();
        for (int i = startRow; i <= endRow; i++) { // 2 i 7
            for (int j = 0; j < 8; j++) {
                    if(board[i][j] == pawn)
                        playerList.add(board[i][j]);
                }
            }
            return (playerList.isEmpty());
        }
    public void printGameOverMessage(){
        System.out.println();
        System.out.println(">>> Game over <<< \n");
            if(Player.playerPawnNumbers == 0)
                System.out.println("You lost... Computer captured all of your pawns :(");
            else if(Computer.compPawnNumbers == 0)
                System.out.println("Congratulations, you won! All of computer's pawns captured! :)");
            else if(arePawnsOnFinalSide(Player.playerPAWN, 2, 7))
                System.out.println("You won! All of your pawns in the computer's side :)");
            else if (arePawnsOnFinalSide(Computer.computerPAWN, 0, 5))
                System.out.println("You lost...All of computer's pawns in your side :( ");
    }

}
