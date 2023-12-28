import java.util.Random;

public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';
    private boolean movePerformed = false;

    public void findPawnAndMove() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    movePerformed = false;
                    System.out.println(i + " / " + j);
                    int compRow = i;
                    int compColumn = j;
                    int rowBelow = compRow + 1;
                    int twoRowsBelow = compRow + 2;
                    int threeRowsBelow = compRow + 3;
                    performBestMove(compRow, compColumn, rowBelow, twoRowsBelow, threeRowsBelow);
                    System.out.println("move performed: " + movePerformed);
                    if (movePerformed) {
                        return;
                    }
                }
            }
        }
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    movePerformed = false;
                    System.out.println(i + " / " + j);
                    int compRow = i;
                    int compColumn = j;
                    int rowBelow = compRow + 1;
                    int twoRowsBelow = compRow + 2;
                    int threeRowsBelow = compRow + 3;
                    if(isCapturePossible(compColumn, rowBelow, twoRowsBelow)){
                        capturePawn(compRow, compColumn, rowBelow, twoRowsBelow);
                    }
                    else {
                        jumpToField(compRow, compColumn, rowBelow);
                    }

                    if (movePerformed) {
                        return;
                    }
                }
            }
        }
    }
    private boolean isMoveInRange(int row, int column) {
        return (row >= 0 && row < 9 && column >= 0 && column < 9);
    }

    //////////////// QUEEN PART ///////////////////////////
    /*private void performPawnMove() {
        if (isPawnMoveValid()) {
            if (isRowAbovePawnSelected()) {
                jumpToField();
            } else if (areTwoRowsAbovePawnSelected()) {
                capturePawn();
            }
        } else {
            printMessageOfInvalidMove();
        }
        GameLogic.currentPlayer = "Computer";
    }
    private void performQueenMove() {
        if (isQueenMoveValid()) {
            jumpQueenToField();
            if (isEnemyOnQueenRoad()) {
                capturePawnWithQueen();
            }
        }
        GameLogic.currentPlayer = "Player";
    }*/
    // Later add Board.board[i][j] == '@'
    private boolean isPawnOnQueenRoad(int compRow, int newRow, int compColumn, int newColumn){
        for (int i = Math.min(compRow, newRow) + 1; i < Math.max(compRow, newRow); i++) {
            for (int j = Math.min(compColumn, newColumn) + 1; j < Math.max(compColumn, newColumn); j++) {
                if (Board.board[i][j] == Player.playerPAWN) {
                    return true;
                }
            }
        }
        return false;
    }
    private void capturePawnWithQueen(int compRow, int newRow, int compColumn, int newColumn){
        for (int i = Math.min(compRow, newRow) + 1; i < Math.max(compRow, newRow); i++) {
            for (int j = Math.min(compColumn, newColumn) + 1; j < Math.max(compColumn, newColumn); j++) {
                if (Board.board[i][j] != Board.emptyField && Board.board[i][j] != Player.playerPAWN) {
                    Board.board[i][j] = Board.emptyField;
                }
            }
        }
        Computer.compPawnNumbers -= 1;
    }

    private void jumpQueenToField(int compRow, int compColumn, int newRow, int newColumn) {
        Board.board[compRow][compColumn] = Board.emptyField;
        Board.board[newRow][newColumn] = computerQueenPawn;
    }

    //////////////// QUEEN PART ///////////////////////////


    private boolean isRiskAfterMove(int compColumn, int compRow, int twoRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            int leftColumn = compColumn - 2;
            int rightColumn = compColumn + 2;

            System.out.println("riskMove " + leftColumn + "/" + rightColumn);
            return ((isMoveInRange(twoRowsBelow, leftColumn) && Board.board[twoRowsBelow][leftColumn] == Player.playerPAWN) ||
                    (isMoveInRange(twoRowsBelow, rightColumn) && Board.board[twoRowsBelow][rightColumn] == Player.playerPAWN));
        }
        return false;
    }

    private boolean isRiskAfterCapture(int compColumn, int compRow, int twoRowsBelow, int threeRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            if (compColumn == 3 || compColumn == 4) {
                int leftColumn = compColumn - 3;
                int rightColumn = compColumn + 3;
                return ((isMoveInRange(threeRowsBelow, rightColumn) && Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN) ||
                        (isMoveInRange(threeRowsBelow, rightColumn) && Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN) ||
                        (isMoveInRange(threeRowsBelow, compColumn) && Board.board[threeRowsBelow][compColumn] == Player.playerPAWN));
            }
            else {
                int leftColumn = compColumn - 2;
                int rightColumn = compColumn + 2;

                System.out.println("isRiskAfterCapture compRow = 0 - 4");

                return ((isMoveInRange(twoRowsBelow, leftColumn) && Board.board[twoRowsBelow][leftColumn] == Player.playerPAWN) ||
                        (isMoveInRange(twoRowsBelow, rightColumn) && Board.board[twoRowsBelow][rightColumn] == Player.playerPAWN));
            }
        }
        return false;
    }

    private void performBestMove(int compRow, int compColumn, int rowBelow, int twoRowBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, threeRowsBelow)) {
            if (isRiskAfterCapture(compColumn, compRow, twoRowBelow, threeRowsBelow)) {
                if (isRiskAfterMove(compColumn, compRow, twoRowBelow)) {
                    capturePawn(compRow, compColumn, rowBelow, twoRowBelow);
                } else
                    jumpToField(compRow, compColumn, rowBelow);
            } else
                capturePawn(compRow, compColumn, rowBelow, twoRowBelow);
        } else if (!isRiskAfterMove(compColumn, compRow, twoRowBelow)) {
            jumpToField(compRow, compColumn, rowBelow);
        }
    }

    private boolean isPlayerOnLeft(int compColumn, int rowBelow) {
        int leftColumn = compColumn - 1;
        return (Board.board[rowBelow][leftColumn] == Player.playerPAWN);
    }

    private boolean isPlayerOnRight(int compColumn, int rowBelow) {
        int rightColumn = compColumn + 1;
        return (Board.board[rowBelow][rightColumn] == Player.playerPAWN);
    }

    private boolean isPlayerOnBothSides(int compColumn, int rowBelow) {
        return (isPlayerOnLeft(compColumn, rowBelow) && isPlayerOnRight(compColumn, rowBelow));
    }

    private void capturePawn(int compRow, int compColumn, int rowBelow, int twoRowsBelow) {
        int columnOnLeft = compColumn - 1;
        int columnOnRight = compColumn + 1;
        int leftAfterCapture = compColumn - 2;
        int rightAfterCapture = compColumn + 2;

        if (compColumn == 0 || compColumn == 1) {
            handleCaptureMove(columnOnRight, twoRowsBelow, rightAfterCapture, compColumn, compRow);
        } else if (compColumn == 6 || compColumn == 7) {
            handleCaptureMove(columnOnLeft, twoRowsBelow, leftAfterCapture, compColumn, compRow);
        } else if (compColumn >= 2 && compColumn <= 5) {
            handleBothSidesCapture(compRow, compColumn, rowBelow, columnOnLeft, columnOnRight, twoRowsBelow, leftAfterCapture, rightAfterCapture);
        }
    }

    private void handleCaptureMove(int capturedColumn, int twoRowsBelow, int newColumn, int compColumn, int compRow) {
        if (Board.board[twoRowsBelow][newColumn] == Board.emptyField) {
            performCapture(compRow, capturedColumn, twoRowsBelow, newColumn, compColumn);
        }
    }

    private void handleBothSidesCapture(int compRow, int compColumn, int rowBelow, int columnOnLeft,
                                        int columnOnRight, int twoRowsBelow, int leftAfterCapture, int rightAfterCapture) {
        if (isPlayerOnBothSides(compColumn, rowBelow)) {
            System.out.println("both sides");
            int randomColumn = getRandomColumn(columnOnLeft, columnOnRight);
            if(randomColumn == columnOnLeft && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField)
                performCapture(compRow, columnOnLeft, twoRowsBelow, randomColumn, compColumn);
            else if(randomColumn == columnOnRight && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField)
                    performCapture(compRow, columnOnRight, twoRowsBelow, randomColumn, compColumn);
        } else if (isPlayerOnLeft(compColumn, rowBelow)) {
            System.out.println("left");
            handleCaptureMove(columnOnLeft, twoRowsBelow, leftAfterCapture, compColumn, compRow);
        } else if (isPlayerOnRight(compColumn, rowBelow)) {
            System.out.println("right");
            handleCaptureMove(columnOnRight, twoRowsBelow, rightAfterCapture, compColumn, compRow);
        }
    }
    private int getRandomColumn(int leftAfterCapture, int rightAfterCapture) {
        Random random = new Random();
        return random.nextBoolean() ? leftAfterCapture : rightAfterCapture;
    }

    private void performCapture(int compRow, int capturedColumn, int twoRowsBelow, int newColumn, int compColumn) {
        Board.board[compRow + 1][capturedColumn] = Board.emptyField;
        Board.board[compRow][compColumn] = Board.emptyField;
        System.out.println(capturedColumn);
        if(twoRowsBelow == 7)
            Board.board[twoRowsBelow][newColumn] = computerQueenPawn;
        else
            Board.board[twoRowsBelow][newColumn] = computerPAWN;
        Player.playerPawnNumbers -= 1;
        GameLogic.currentPlayer = "Human";
        movePerformed = true;
    }
    private boolean isCapturePossible(int compColumn, int rowBelow, int twoRowsBelow) {
        int leftAfterCapture = compColumn - 2;
        int rightAfterCapture = compColumn + 2;
            if (compColumn == 0 || compColumn == 1) {
                System.out.println("X");
                System.out.println("Y");
                return isMoveInRange(twoRowsBelow, rightAfterCapture) && isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField;
            } else if (compColumn == 6 || compColumn == 7) {
                return isMoveInRange(twoRowsBelow, leftAfterCapture) && isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField;
            } else if (compColumn >= 2 && compColumn <= 5) {
                return ((isMoveInRange(twoRowsBelow, leftAfterCapture) &&isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField)
                || (isMoveInRange(twoRowsBelow, rightAfterCapture) && isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField));
            }
        return false;
    }


    private void jumpToField(int compRow, int compColumn, int rowBelow) {
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;

        if (compColumn == 0 || compColumn == 7) {
            handleJumpMove(compRow, compColumn, rowBelow, rightColumn);
        } else {
            if (Board.board[rowBelow][leftColumn] == Board.emptyField &&
                    Board.board[rowBelow][rightColumn] == Board.emptyField) {
                int randomColumn = getRandomColumn(leftColumn, rightColumn);
                performJump(compRow, compColumn, rowBelow, randomColumn);
            } else {
                handleJumpMove(compRow,compColumn, rowBelow, leftColumn);
                handleJumpMove(compRow, compColumn, rowBelow, rightColumn);
            }
        }
    }

    private void handleJumpMove(int compRow, int compColumn, int rowBelow, int newColumn) {
        if (Board.board[rowBelow][newColumn] == Board.emptyField) {
            performJump(compRow, compColumn, rowBelow, newColumn);
        }
    }

    private void performJump(int compRow, int compColumn, int rowBelow, int newColumn) {
        Board.board[compRow][compColumn] = Board.emptyField;
        if(rowBelow == 7)
            Board.board[rowBelow][newColumn] = computerQueenPawn;
        else
            Board.board[rowBelow][newColumn] = computerPAWN;
        movePerformed = true;
    }

}


