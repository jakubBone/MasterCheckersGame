import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    final static char computerQueenPawn = '#';
    private boolean movePerformed = false;


    public void findPawnAndMove() {
        List<int[]> computerPawns = new ArrayList<>();
        int highestPriority = Integer.MIN_VALUE;
        int currentPriority = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN || Board.board[i][j] == computerQueenPawn) {
                    computerPawns.add(new int[]{i, j});
                }
            }
        }
        for (int[] pawnPosition : computerPawns) {
            movePerformed = false;
            System.out.println(pawnPosition[0] + " / " + pawnPosition[1]);
            int compRow = pawnPosition[0];
            int compColumn = pawnPosition[1];
            int rowBelow = compRow + 1;
            int twoRowsBelow = compRow + 2;
            int threeRowsBelow = compRow + 3;

            performBestMove(compRow, compColumn, rowBelow, twoRowsBelow, threeRowsBelow);

            if(movePerformed){
                System.out.println("move");
                break;
            }
            else {
                currentPriority = calculatePriority(compColumn, compRow, rowBelow, twoRowsBelow, threeRowsBelow);
            }

            if (currentPriority > highestPriority) {
                highestPriority = currentPriority;
                bestMove = pawnPosition.clone();
            }
        }

        if (!movePerformed && bestMove != null) {
            System.out.println("BestMove");
            int compRow = bestMove[0];
            int compColumn = bestMove[1];
            int rowBelow = compRow + 1;
            int twoRowsBelow = compRow + 2;
            int threeRowsBelow = compRow + 3;
            performBestMove(compRow, compColumn, rowBelow, twoRowsBelow, threeRowsBelow);
        }
    }

    private int calculatePriority(int compColumn, int compRow, int rowBelow, int twoRowsBelow, int threeRowsBelow) {
        int priority = 0;
        if (isCapturePossible(compColumn, rowBelow, twoRowsBelow) && isRiskAfterCapture(compColumn, compRow, threeRowsBelow)) {
            priority = 10;
        }
        else if (isRiskAfterMove(compColumn, compRow, twoRowsBelow))
                priority = 5;

            return priority;
    }

    private boolean isMoveInRange(int row, int column) {
        return (row >= 0 && row < 9 && column >= 0 && column < 9);
    }

    //////////////// Queen move ///////////////////

    public static boolean canQueenCapture(int compRow, int compColumn) {
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] direction : directions) {
            int directionRow = direction[0];
            int directionColumn = direction[1];

            int newX = compRow + directionRow;
            int newY = compColumn + directionColumn;

                if (Board.board[newX][newY] != 0 && (Board.board[newX][newY] != Player.playerPAWN || Board.board[newX][newY] != Player.playerQueenPAWN)) {
                    return true;  // can capture
                }
            }
        return false;  // can't capture
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
    private void performJumpQueen(int compRow, int compColumn, int newRow, int newColumn) {
        Board.board[compRow][compColumn] = Board.emptyField;
        Board.board[newRow][newColumn] = computerQueenPawn;
    }


    //////////////// Queen move ///////////////////


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
    private boolean isRiskAfterCapture(int compColumn, int compRow, int threeRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            if (compColumn == 3 || compColumn == 4 || (compColumn == 2 && !(Board.board[compRow + 1][compColumn - 1] == Player.playerPAWN)) ||
                    (compColumn == 5 && !(Board.board[compRow + 1][compColumn - 1] == Player.playerPAWN) ||
                            (compColumn == 6 && !(Board.board[compRow + 1][compColumn + 1] == Player.playerPAWN)))){
                int leftColumn = compColumn - 3;
                int rightColumn = compColumn + 3;
                return ((isMoveInRange(threeRowsBelow, rightColumn) && (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN || Board.board[threeRowsBelow][compColumn + 1] == Player.playerPAWN)) ||
                        (isMoveInRange(threeRowsBelow, leftColumn) && (Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN || Board.board[threeRowsBelow][compColumn - 1] == Player.playerPAWN)));
            }
        }
        return false;
    }
    private void performBestMove(int compRow, int compColumn, int rowBelow, int twoRowBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, threeRowsBelow)) {
            if (isRiskAfterCapture(compColumn, compRow, threeRowsBelow)) {
                if (!isRiskAfterMove(compColumn, compRow, twoRowBelow))
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
            int randomColumn = getRandomColumn(leftAfterCapture, rightAfterCapture);
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
        System.out.println("captured column: " + capturedColumn);
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
