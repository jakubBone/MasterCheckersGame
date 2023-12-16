import java.util.Random;

public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';

    int getPlayerColumn(int compRow, int compColumn) {
        int playerToCapture = 0;
        int fieldOnLeftAbove = compColumn - 1;
        int fieldOnRightAbove = compColumn + 1;
        if (Board.board[compRow + 1][fieldOnLeftAbove] == Player.playerPAWN)
            playerToCapture = fieldOnLeftAbove;
        else if (Board.board[compRow + 1][fieldOnRightAbove] == Player.playerPAWN)
            playerToCapture = fieldOnRightAbove;

        return playerToCapture;
    }
    boolean ifRiskOfCapturePossible(int playerRow, int playerColumn){
        int playerOnLeft = playerColumn - 1;
        int playerOnRight = playerColumn  + 1;
        return (Board.board[playerRow + 1][playerOnLeft] == Player.playerPAWN ||
                Board.board[playerRow + 1][playerOnRight] == Player.playerPAWN);

    }
    int getJumpedColumn(int playerColumn, int rowBelow){
        int jumpedColumn = 0;
        int playerOnLeft = playerColumn - 1;
        int playerOnRight = playerColumn + 1;
        if (Board.board[rowBelow][playerOnLeft] == Player.playerPAWN)
            jumpedColumn = playerOnLeft;
        else if (Board.board[rowBelow][playerOnRight] == Player.playerPAWN)
            jumpedColumn = playerOnRight;

        return jumpedColumn;
    }
    void findPawnAndMove() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    System.out.println(i + " / " + j);
                    int compPawnRow = i;
                    int compPawnColumn = j;
                    int rowBelow = compPawnRow + 1;
                    int twoRowsBelow = compPawnRow + 2;
                    if(compPawnRow >= 2 && compPawnRow <= 5 && compPawnColumn >= 2 && compPawnColumn <=5){
                        int playerColumn = getPlayerColumn(i, j);
                        int jumpedColumn = getJumpedColumn(playerColumn, rowBelow);

                        capturePawn(compPawnRow, compPawnColumn, rowBelow, twoRowsBelow, playerColumn,
                                jumpedColumn);
                        if(GameLogic.currentPlayer.equals("Humman")){
                            break;
                        }
                        System.out.println("dupa");
                    }

                }
            }
        }
    }
    void capturePawn(int compPawnRow, int compPawnColumn,int rowBelow, int twoRowsBelow,
                     int playerColumn, int jumpedField){
        if(!ifRiskOfCapturePossible(rowBelow, playerColumn) &&
                Board.board[rowBelow][jumpedField] == Board.emptyField){
            Board.board[compPawnRow][compPawnColumn] = Board.emptyField;
            Board.board[rowBelow][playerColumn] = Board.emptyField;
            Board.board[twoRowsBelow][jumpedField] = Computer.computerPAWN;
            Player.playerPawnNumbers -= 1;
            GameLogic.currentPlayer = "Humman";
        }
        else
            System.out.println("Risk of capture");
    }
    void jumpToField(int compPawnRow, int compPawnColumn, int rowBelow) {
        Random random = new Random();
        int column1 = compPawnColumn - 1;
        int column2 = compPawnColumn + 1;
        if (Board.board[compPawnRow][column1] == Board.emptyField &&
                Board.board[compPawnRow][column2] == Board.emptyField) {
                int randomColumn = (random.nextBoolean() ? column1 : column2);
                Board.board[compPawnRow][compPawnColumn] = Board.emptyField;
                Board.board[rowBelow][randomColumn] = computerPAWN;
        } else {
            if(Board.board[compPawnRow][column1] == Board.emptyField){
                Board.board[compPawnRow][compPawnColumn] = Board.emptyField;
                Board.board[rowBelow][column1] = computerPAWN;
            }
            else if(Board.board[compPawnRow][column2] == Board.emptyField) {
                Board.board[compPawnRow][compPawnColumn] = Board.emptyField;
                Board.board[rowBelow][column2] = computerPAWN;
            }
        }
    }
    void printMessageOfInvalidMove(){
        System.out.println("Error");
    }
}

