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
    }

    private boolean isRiskAfterMove(int compColumn, int compRow, int twoRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            int leftColumn = compColumn - 2;
            int rightColumn = compColumn + 2;

            System.out.println("riskMove " + leftColumn + "/" + rightColumn);

            return Board.board[twoRowsBelow][leftColumn] == Player.playerPAWN ||
                    Board.board[twoRowsBelow][rightColumn] == Player.playerPAWN;
        }

        return false;
    }

    private boolean isRiskAfterCapture(int compColumn, int compRow, int twoRowsBelow, int threeRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            int leftColumn = compColumn - 2;
            int rightColumn = compColumn + 2;

            System.out.println("isRiskAfterCapture compRow = 0 - 4");

            return (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN ||
                    Board.board[twoRowsBelow][leftColumn] == Player.playerPAWN);
        }
        return false;
    }

    private void performBestMove(int compRow, int compColumn, int rowBelow, int twoRowBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, threeRowsBelow)) {
            System.out.println("capture possible: " + isCapturePossible(compColumn, rowBelow, threeRowsBelow));

            if (!isRiskAfterCapture(compColumn, compRow, threeRowsBelow, threeRowsBelow)) {
                capturePawn(compRow, compColumn, rowBelow, twoRowBelow);
                System.out.println("capture");
                movePerformed = true;
            } else {
                if (!isRiskAfterMove(compColumn, compRow, twoRowBelow)) {
                    jumpToField(compRow, compColumn, rowBelow);
                    System.out.println("jump");
                    movePerformed = true;
                } else {
                    System.out.println("All moves are under risk");
                }
            }
        }
        else
            System.out.println("capture possible: " + isCapturePossible(compColumn, rowBelow, twoRowBelow));
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
            if (Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField &&
                    Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                int randomColumn = getRandomColumn(columnOnLeft, columnOnRight);
                if(randomColumn == columnOnLeft)
                    performCapture(compRow, columnOnLeft, twoRowsBelow, randomColumn, compColumn);
                else
                    performCapture(compRow, columnOnRight, twoRowsBelow, randomColumn, compColumn);
            }
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
        Board.board[twoRowsBelow][newColumn] = Computer.computerPAWN;
        Player.playerPawnNumbers -= 1;
        GameLogic.currentPlayer = "Human";
    }
    private boolean isCapturePossible(int compColumn, int rowBelow, int twoRowsBelow) {
        int leftAfterCapture = compColumn - 2;
        int rightAfterCapture = compColumn + 2;

        if (compColumn == 0 || compColumn == 1) {
            return isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField;
        } else if (compColumn == 6 || compColumn == 7) {
            return isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField;
        } else if (compColumn >= 2 && compColumn <= 5) {
            return ((isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField)
            || (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField));
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
        Board.board[rowBelow][newColumn] = computerPAWN;
        movePerformed = true;
    }
}
