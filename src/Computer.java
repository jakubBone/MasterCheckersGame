import java.util.*;

public class Computer {
    int firstCompPawnRow;
    int firstCompPawnColumn;
    int rowBelow;
    int twoRowsBelow;
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';

    Map <Integer, Integer> compPawns = new HashMap<>();


    void move(){
        findFirstComputerPawnsRow();
    }
    void setFieldsDetails(){
        rowBelow = firstCompPawnRow + 1;
        twoRowsBelow = firstCompPawnRow + 2;
    }

    void findFirstComputerPawnsRow() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    System.out.println(i + " / " + j);
                    firstCompPawnRow = i;
                    firstCompPawnColumn = j;
                    compPawns.put(i, j);
                    //random

                    System.out.println(firstCompPawnRow);
                    System.out.println(firstCompPawnColumn);
                }
            }
        }
    }

    /*void findFirstComputerPawnsRow() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    System.out.println(i + " / " + j);
                    firstCompPawnRow = i;
                    firstCompPawnColumn = j;
                    setFieldsDetails();
                    if (firstCompPawnColumn >= 2 && firstCompPawnColumn <= 5) {
                        System.out.println("2 - 5");
                        if (isPawnOnLeft()) {
                            if (ifPossiblePawnLost(twoRowsBelow, firstCompPawnColumn))
                                System.out.println("do not capture");
                            else
                                System.out.println("capture");
                        } else if (isPawnOnRight()) {
                            if (ifPossiblePawnLost(twoRowsBelow, firstCompPawnColumn))
                                System.out.println("do not capture");
                            else
                                System.out.println("capture");
                        }
                    }
                }
                System.out.println(firstCompPawnRow);
                System.out.println(firstCompPawnColumn);
            }
        }
    }*/

    boolean ifPossiblePawnLost(int row, int col){
        int rowBelowJumpedRow = row - 1;
        return ((Board.board[rowBelowJumpedRow][col + 1] == Player.playerPAWN ||
                Board.board[rowBelowJumpedRow][col + 3] == Player.playerPAWN)) ||
                ((Board.board[rowBelowJumpedRow][col -1 ] == Player.playerPAWN ||
                        Board.board[rowBelowJumpedRow][col - 3] == Player.playerPAWN));
    }
    boolean isPawnOnLeft(){
        return (Board.board[rowBelow][firstCompPawnColumn - 1] == Player.playerPAWN);
    }
    boolean isPawnOnRight(){
        return (Board.board[rowBelow][firstCompPawnColumn + 1] == Player.playerPAWN);
    }


    void printMessageOfInvalidMove(){
        System.out.println("Error");
    }
}

