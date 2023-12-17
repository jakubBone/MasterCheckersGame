import java.util.Random;

public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';

    void findPawnAndMove() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    System.out.println(i + " / " + j);
                    int compRow = i;
                    int compColumn = j;

                    if (compRow >= 2 && compRow <= 5 && compColumn >= 2 && compColumn <= 5) {
                        performBestMove(compRow, compColumn, compRow + 1, compRow + 2);
                        break;
                    }
                }
            }
        }
    }

    void performBestMove(int compRow, int compColumn, int rowBelow, int twoRowBelow) {
        int playerColumn = getPlayerColumn(rowBelow, compColumn);
        int jumpedColumn = getJumpedColumn(playerColumn, compColumn);

        if (!ifRiskOfCapture(twoRowBelow, playerColumn, compColumn) &&
                Board.board[rowBelow][jumpedColumn] == Board.emptyField) {
                capturePawn(compRow, compColumn, rowBelow, twoRowBelow, playerColumn, jumpedColumn);
        } else
            jumpToField(compRow, compColumn, rowBelow);
    }

    int getPlayerColumn(int rowBelow, int compColumn) {
        int playerColumn = 0;
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;
        if (Board.board[rowBelow][leftColumn] == Player.playerPAWN)
            playerColumn = leftColumn;
        else if (Board.board[rowBelow][rightColumn] == Player.playerPAWN)
            playerColumn = rightColumn;

        return playerColumn;
    }

    int getJumpedColumn(int playerColumn, int compColumn){
        int jumpedColumn = 0;
        if(playerColumn == compColumn - 1)
            jumpedColumn = compColumn - 2;
        else if(playerColumn == compColumn + 1)
            jumpedColumn = compColumn + 2;

        return jumpedColumn;
    }

    boolean ifRiskOfCapture(int twoRowsBelow, int playerColumn, int compColumn){
        boolean ifRisk = false;
        int jumpedColumn = getJumpedColumn(playerColumn, compColumn);
        System.out.println("1");
        int riskyPlayerRow = twoRowsBelow + 1;
        System.out.println("2");
        int riskyPlayerOnLeft = getJumpedColumn(playerColumn, compColumn) - 1;
        System.out.println("3");
        int riskyPlayerOnRight  = getJumpedColumn(playerColumn, compColumn) + 1;
        System.out.println("4");
            return (jumpedColumn > 0 && jumpedColumn < 7 &&
                    Board.board[riskyPlayerRow][riskyPlayerOnLeft] == Player.playerPAWN ||
                    Board.board[riskyPlayerRow][riskyPlayerOnRight] == Player.playerPAWN);
        }

    void capturePawn(int compRow, int compColumn,int rowBelow, int twoRowsBelow,
                     int playerColumn, int jumpedColumn){
            Board.board[compRow][compColumn] = Board.emptyField;
            Board.board[rowBelow][playerColumn] = Board.emptyField;
            Board.board[twoRowsBelow][jumpedColumn] = Computer.computerPAWN;
            Player.playerPawnNumbers -= 1;
            GameLogic.currentPlayer = "Human";
    }
    void jumpToField(int compRow, int compColumn, int rowBelow) {
        Random random = new Random();
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;
        if (Board.board[compRow][leftColumn] == Board.emptyField &&
                Board.board[compRow][rightColumn] == Board.emptyField) {
                int randomColumn = (random.nextBoolean() ? leftColumn : rightColumn);
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][randomColumn] = computerPAWN;
        } else {
            if(Board.board[compRow][leftColumn] == Board.emptyField){
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = computerPAWN;
            }
            else if(Board.board[compRow][rightColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = computerPAWN;
            }
        }
    }
}


