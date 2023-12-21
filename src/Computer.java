import java.util.Random;

public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';

    private boolean  movePerformed = false;

    public void findPawnAndMove() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    System.out.println(i + " / " + j);
                    int compRow = i;
                    int compColumn = j;
                    int rowBelow = compRow + 1;
                    int twoRowBelow = compRow + 2;
                        performBestMove(compRow, compColumn, compRow + 1, compRow + 2);
                        movePerformed = false;
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

     private void performBestMove(int compRow, int compColumn, int rowBelow, int twoRowBelow) {
                if (!ifRiskOfAfterCapture(compColumn, twoRowBelow)) {
                    capturePawn(compRow, compColumn, rowBelow, twoRowBelow);
                    System.out.println("capture");
                    System.out.println(movePerformed);
                    if(!movePerformed){
                        if(!ifRiskAfterMove(compColumn, twoRowBelow)) {
                            jumpToField(compRow, compColumn, rowBelow);
                            System.out.println("move");
                    }
                }
                }
            }

    private boolean isPlayerOnLeft(int compColumn, int rowBelow){
        int leftColumn = compColumn - 1;
        return(Board.board[rowBelow][leftColumn] == Player.playerPAWN);
    }

    private boolean isPlayerOnRight(int compColumn, int rowBelow){
        int rightColumn = compColumn + 1;
        return(Board.board[rowBelow][rightColumn] == Player.playerPAWN);
    }

    private boolean isPlayerOnBothSides(int compColumn, int rowBelow){
        return (isPlayerOnLeft(compColumn, rowBelow) && isPlayerOnRight(compColumn, rowBelow));
    }
    private void capturePawn(int compRow, int compColumn,int rowBelow, int twoRowsBelow) {
        int columnOnLeft = compColumn - 1;
        int columnOnRight = compColumn + 1;
        int leftAfterCapture = compColumn - 2;
        int rightAfterCapture = compColumn + 2;

        if (compColumn == 0 || compColumn == 1) {
            System.out.println("compColumn == 0 || compColumn == 1");
            if (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][columnOnRight] = Board.emptyField;
                Board.board[twoRowsBelow][rightAfterCapture] = Computer.computerPAWN;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                System.out.println("right");
                movePerformed = true;
            }
        } else if (compColumn == 6 || compColumn == 7) {
            System.out.println("compColumn == 6 || compColumn == 7");
            if (isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][columnOnLeft] = Board.emptyField;
                Board.board[twoRowsBelow][leftAfterCapture] = Computer.computerPAWN;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                System.out.println("left");
            }
        } else if(compColumn >= 2 || compColumn <= 5){
            System.out.println("compColumn == 2 || compColumn == 5");
            if (isPlayerOnBothSides(compColumn, rowBelow)) {
                System.out.println("3");
                if (Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField &&
                        Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                    System.out.println("random");
                    Random random = new Random();
                    int randomColumn = (random.nextBoolean() ? leftAfterCapture : rightAfterCapture);
                    if (randomColumn == leftAfterCapture) {
                        System.out.println("random left");
                        Board.board[compRow][compColumn] = Board.emptyField;
                        Board.board[rowBelow][columnOnLeft] = Board.emptyField;
                        Board.board[twoRowsBelow][leftAfterCapture] = computerPAWN;
                        Player.playerPawnNumbers -= 1;
                        GameLogic.currentPlayer = "Human";
                    } else {
                        System.out.println("random right");
                        Board.board[compRow][compColumn] = Board.emptyField;
                        Board.board[rowBelow][columnOnRight] = Board.emptyField;
                        Board.board[twoRowsBelow][rightAfterCapture] = computerPAWN;
                        Player.playerPawnNumbers -= 1;
                        GameLogic.currentPlayer = "Human";
                        System.out.println("");
                    }
                }
                System.out.println("Random pawn changed");
            } else if (isPlayerOnLeft(compColumn, rowBelow)) {
                System.out.println("left");
                if (Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField) {
                    Board.board[compRow][compColumn] = Board.emptyField;
                    System.out.println("Board.board[compRow][compColumn] = Board.emptyField;");
                    Board.board[rowBelow][columnOnLeft] = Board.emptyField;
                    System.out.println("Board.board[rowBelow][columnOnLeft] = Board.emptyField;");
                    Board.board[twoRowsBelow][leftAfterCapture] = computerPAWN;
                    System.out.println("Board.board[rowBelow][leftAfterCapture] = computerPAWN;");
                    Player.playerPawnNumbers -= 1;
                    GameLogic.currentPlayer = "Human";
                    System.out.println("left pawn changed");
                }
            } else if (isPlayerOnRight(compColumn, rowBelow)) {
                System.out.println("right");
                if (Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][columnOnRight] = Board.emptyField;
                    Board.board[twoRowsBelow][rightAfterCapture] = computerPAWN;
                    Player.playerPawnNumbers -= 1;
                    GameLogic.currentPlayer = "Human";
                    System.out.println("right pawn changed");
                }
                System.out.println("out");
            }
            System.out.println("final out ");
        }
        else {
            System.out.println("???");
        }
        movePerformed = true;
    }

    private void jumpToField(int compRow, int compColumn, int rowBelow) {
        Random random = new Random();
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;

        if (compColumn == 0 || compColumn == 7) {
            if(compColumn == 0 && Board.board[rowBelow][rightColumn] == Board.emptyField){
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][rightColumn] = computerPAWN;
                    System.out.println("Only the right field to choose possible");
            }
            else if(compColumn == 7  && Board.board[rowBelow][leftColumn] == Board.emptyField){
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][leftColumn] = computerPAWN;
                    System.out.println("Column 0");
            }
        } else {
            if (Board.board[rowBelow][leftColumn] == Board.emptyField &&
                    Board.board[rowBelow][rightColumn] == Board.emptyField) {
                int randomColumn = (random.nextBoolean() ? leftColumn : rightColumn);
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][randomColumn] = computerPAWN;
                System.out.println("Two field to choose possible");
            } else {
                if (Board.board[rowBelow][leftColumn] == Board.emptyField) {
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][leftColumn] = computerPAWN;
                    System.out.println("Only the left field to choose possible");
                } else if (Board.board[rowBelow][rightColumn] == Board.emptyField) {
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][rightColumn] = computerPAWN;
                    System.out.println("Only the right field to choose possible");
                }
            }
        }
    }

}


