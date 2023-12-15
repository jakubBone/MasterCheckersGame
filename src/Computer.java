public class Computer {
    int firstPawnRow;
    int firstPawnColumn;

    void findFirstPawnInRow() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    firstPawnRow = i;
                    firstPawnColumn = j;
                }
            }
        }
    }
    final static char computerPAWN  = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';

    boolean isPawnOnLeft(){
        return (Board.board[firstPawnRow + 1][firstPawnColumn - 1] == Player.playerPAWN);
    }
    boolean isPawnOnRight(){
        return (Board.board [firstPawnRow+ 1][firstPawnColumn + 1] == Player.playerPAWN);
    }
    /*boolean isPawnOnLeft(){
        return (Board.board[pawnRow + 1][pawnColumn - 1] == Player.playerPAWN);
    }
    boolean isPawnOnRight(){
        return (Board.board [pawnRow + 1][pawnColumn +1] == Player.playerPAWN);
    }*/

    void performMove() {
        System.out.println("Comp Flag in performMove() 1 step");
        findFirstPawnInRow();
        if(firstPawnColumn >= 1 && firstPawnColumn <=7 && firstPawnRow >= 1 && firstPawnRow <= 7) {
            System.out.println("Comp Flag in performMove 2 step()");
            if (isPawnOnLeft()) {
                if (Board.board[firstPawnRow + 2][firstPawnColumn - 2] == ' ') {
                    Board.board[firstPawnRow + 1][firstPawnColumn - 1] = ' ';
                    Board.board[firstPawnRow + 2][firstPawnColumn - 2] = computerPAWN;
                }
            } else if (isPawnOnRight()) {
                if (Board.board[firstPawnRow + 2][firstPawnColumn + 2] == ' ') {
                    Board.board[firstPawnRow + 1][firstPawnColumn + 1] = ' ';
                    Board.board[firstPawnRow + 2][firstPawnColumn + 2] = computerPAWN;
                }
            } else if (Board.board[firstPawnRow + 1][firstPawnColumn - 1] == Board.emptyField) {
                Board.board[firstPawnRow + 1][firstPawnColumn - 1] = computerPAWN;
            } else if (Board.board[firstPawnRow + 1][firstPawnColumn + 1] == Board.emptyField) {
                Board.board[firstPawnRow + 1][firstPawnColumn + 1] = computerPAWN;
            } else
                printMessageOfInvalidMove();
        }
        GameLogic.currentPlayer = "Computer";
    }
    void printMessageOfInvalidMove(){
        System.out.println("Error");
    }


}
