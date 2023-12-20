import java.util.Random;

public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';

    public void findPawnAndMove() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    System.out.println(i + " / " + j);
                    int compRow = i;
                    int compColumn = j;
                    //if (compRow >= 2 && compRow <= 5 && compColumn >= 2 && compColumn <= 5) {
                        performBestMove(compRow, compColumn, compRow + 1, compRow + 2);
                        return;
                    }
                }
            }
        }

    private boolean ifRiskAfterMove(int compColumn, int twoRowsBelow){
        int leftDirection= compColumn - 1;
        int rightDirection = compColumn + 1;
        return ((leftDirection > 0 && rightDirection < 7) &&
                (Board.board[twoRowsBelow][leftDirection - 1] == Player.playerPAWN ||
                        Board.board[twoRowsBelow][leftDirection + 1] == Player.playerPAWN) ||
                (Board.board[twoRowsBelow][rightDirection - 1] == Player.playerPAWN ||
                        Board.board[twoRowsBelow][rightDirection + 1] == Player.playerPAWN));
    }
    private boolean ifRiskOfAfterCapture(int compColumn, int twoRowsBelow){
        int leftDirection= compColumn - 2;
        int rightDirection = compColumn + 2;

        return((leftDirection > 0 && rightDirection < 7) &&
                (Board.board[twoRowsBelow][leftDirection - 1] == Player.playerPAWN ||
                        Board.board[twoRowsBelow][leftDirection + 1] == Player.playerPAWN) ||
                (Board.board[twoRowsBelow][rightDirection - 1] == Player.playerPAWN ||
                        Board.board[twoRowsBelow][rightDirection + 1] == Player.playerPAWN));
    }
    private boolean canMove(int compColumn, int rowBelow){
        return (Board.board[rowBelow][compColumn - 1] == Board.emptyField ||
                Board.board[rowBelow][compColumn + 1]  == Board.emptyField);

    }
    private boolean canCapture(int compColumn, int rowBelow, int twoRowsBelow){
        return ((Board.board[rowBelow][compColumn - 1] == Player.playerPAWN ||
                Board.board[rowBelow][compColumn + 1]  == Player.playerPAWN) &&
                (Board.board[twoRowsBelow][compColumn - 2] == Board.emptyField ||
                        Board.board[twoRowsBelow][compColumn + 2] == Board.emptyField));
    }

     private void performBestMove(int compRow, int compColumn, int rowBelow, int twoRowBelow) {
        int playerColumn = getPlayerColumn(rowBelow, compColumn);
        int afterCaptureColumn = getAfterCaptureColumn(playerColumn, compColumn);

             if (canCapture(compColumn, rowBelow, twoRowBelow) &&
                !ifRiskOfAfterCapture(compColumn, twoRowBelow) &&
                Board.board[rowBelow][afterCaptureColumn] == Board.emptyField) {
                    capturePawn(compRow, compColumn, rowBelow, twoRowBelow, playerColumn, afterCaptureColumn);
                    System.out.println("capture");
             } else if((canMove(compColumn, rowBelow) && !ifRiskAfterMove(compColumn, twoRowBelow))){
                 jumpToField(compRow, compColumn, rowBelow);
                 System.out.println("move");
             }
     }


    private int getPlayerColumn(int rowBelow, int compColumn) {
        int playerColumn = 0;
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;
        if (Board.board[rowBelow][leftColumn] == Player.playerPAWN)
            playerColumn = leftColumn;
        else if (Board.board[rowBelow][rightColumn] == Player.playerPAWN)
            playerColumn = rightColumn;

        return playerColumn;
    }

    private int getAfterCaptureColumn(int playerColumn, int compColumn){
        int afterCaptureColumn = 0;
        if(playerColumn == compColumn - 1)
            afterCaptureColumn = compColumn - 2;
        else if(playerColumn == compColumn + 1)
            afterCaptureColumn = compColumn + 2;

        return afterCaptureColumn;
    }
    private void capturePawn(int compRow, int compColumn,int rowBelow, int twoRowsBelow,
                     int playerColumn, int jumpedColumn){
            Board.board[compRow][compColumn] = Board.emptyField;
            Board.board[rowBelow][playerColumn] = Board.emptyField;
            Board.board[twoRowsBelow][jumpedColumn] = Computer.computerPAWN;
            Player.playerPawnNumbers -= 1;
            GameLogic.currentPlayer = "Human";
    }
    private void jumpToField(int compRow, int compColumn, int rowBelow) {
        Random random = new Random();
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;
        if (Board.board[rowBelow][leftColumn] == Board.emptyField &&
                Board.board[rowBelow][rightColumn] == Board.emptyField) {
                int randomColumn = (random.nextBoolean() ? leftColumn : rightColumn);
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][randomColumn] = computerPAWN;
                System.out.println("Two field to choose possible");
        } else {
            if(Board.board[rowBelow][leftColumn] == Board.emptyField){
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = computerPAWN;
                System.out.println("Only the left field to choose possible");
            }
            else if(Board.board[rowBelow][rightColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = computerPAWN;
                System.out.println("Only the right field to choose possible");
            }
        }
    }
}


