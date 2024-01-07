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
        ArrayList<int[]> pawnsPriority = new ArrayList<>();
        int currentPriority = Integer.MIN_VALUE;

        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN || Board.board[i][j] == computerQueenPawn) {
                    computerPawns.add(new int[]{i, j});
                }
            }
        }

        // Set all pawns priority
        for (int[] pawnPosition : computerPawns) {
            movePerformed = false;
            System.out.println("[" + pawnPosition[0] + "][" + pawnPosition[1] + "]");
            int compRow = pawnPosition[0];
            int compColumn = pawnPosition[1];
            int rowBelow = compRow + 1;
            int twoRowsBelow = compRow + 2;
            int threeRowsBelow = compRow + 3;

            currentPriority = calculatePriority(compColumn, compRow, rowBelow, twoRowsBelow, threeRowsBelow);
                pawnsPriority.add(new int[]{compRow,compColumn, currentPriority});

    }
        // Sort pawns by priority
        int n = pawnsPriority.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pawnsPriority.get(j)[2] < pawnsPriority.get(j + 1)[2]) {
                    int[] temp = pawnsPriority.get(j);
                    pawnsPriority.set(j, pawnsPriority.get(j + 1));
                    pawnsPriority.set(j + 1, temp);
                }
            }
        }

        // Print sorted pawns
        for (int[] sortedPawn : pawnsPriority) {
            int row = sortedPawn[0];
            int column = sortedPawn[1];
            int priorityScore = sortedPawn[2];
            System.out.println("Row: " + row + ", Column: " + column + ", Movement priority: " + priorityScore);
        }

        // Find the best pawn to move
            int counter = 0;
            while(!movePerformed){
                int[] bestPriorityPawn = pawnsPriority.get(counter);
                int bestRow = bestPriorityPawn[0];
                int bestColumn = bestPriorityPawn[1];
                System.out.print("BestMove: ");
                System.out.println("[" + bestRow + "][" + bestColumn + "]");

                int rowBelow = bestRow + 1;
                int twoRowsBelow = bestRow + 2;

                if (bestPriorityPawn[2] == 10 || bestPriorityPawn[2] == 5) {
                    capturePawn(bestRow, bestColumn, rowBelow, twoRowsBelow);
                } else if (bestPriorityPawn[2] == 8 || bestPriorityPawn[2] == 4) {
                    jumpToField(bestRow, bestColumn, rowBelow);
                }
                counter++;
            }
    }
    private int calculatePriority(int compColumn, int compRow, int rowBelow, int twoRowsBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, twoRowsBelow)) {
            return isRiskAfterCapture(compColumn, compRow, threeRowsBelow) ? 5 : 10;
        } else {
            return isRiskAfterMove(compColumn, compRow, twoRowsBelow) ? 4 : 8;
        }
    }

    private boolean isRiskAfterCapture(int compColumn, int compRow, int threeRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            if (compColumn == 3 || compColumn == 4 ||
                    (compColumn == 0 && !(Board.board[compRow + 1][compColumn + 1] == Player.playerPAWN)) ||
                    (compColumn == 7 && !(Board.board[compRow + 1][compColumn - 1] == Player.playerPAWN)) ||
                    (compColumn == 2 && !(Board.board[compRow + 1][compColumn - 1] == Player.playerPAWN)) ||
                    (compColumn == 5 && !(Board.board[compRow + 1][compColumn - 1] == Player.playerPAWN)) ||
                    (compColumn == 1 && !(Board.board[compRow + 1][compColumn - 1] == Player.playerPAWN) ||
                            (compColumn == 6 && !(Board.board[compRow + 1][compColumn + 1] == Player.playerPAWN)))){
                int leftColumn = compColumn - 3;
                int rightColumn = compColumn + 3;
                return ((isMoveInRange(threeRowsBelow, rightColumn) && (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN || Board.board[threeRowsBelow][compColumn + 1] == Player.playerPAWN)) ||
                        (isMoveInRange(threeRowsBelow, leftColumn) && (Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN || Board.board[threeRowsBelow][compColumn - 1] == Player.playerPAWN)));
            }
        }
        return false;
    }

    private boolean isRiskAfterMove(int compColumn, int compRow, int twoRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            int leftColumn = compColumn - 2;
            int rightColumn = compColumn + 2;

            System.out.println("Move is risky");
            return ((isMoveInRange(twoRowsBelow, leftColumn) && Board.board[twoRowsBelow][leftColumn] == Player.playerPAWN) ||
                    (isMoveInRange(twoRowsBelow, rightColumn) && Board.board[twoRowsBelow][rightColumn] == Player.playerPAWN));
        }
        return false;
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
    private void handleJumpMove(int compRow, int compColumn, int rowBelow, int newColumn) {
        if (Board.board[rowBelow][newColumn] == Board.emptyField) {
            performJump(compRow, compColumn, rowBelow, newColumn);
        }
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
        System.out.println("Capture performed");
    }

    private void performJump(int compRow, int compColumn, int rowBelow, int newColumn) {
        Board.board[compRow][compColumn] = Board.emptyField;
        if(rowBelow == 7)
            Board.board[rowBelow][newColumn] = computerQueenPawn;
        else
            Board.board[rowBelow][newColumn] = computerPAWN;
        movePerformed = true;
    }

    private boolean isMoveInRange(int row, int column) {
        return (row >= 0 && row < 9 && column >= 0 && column < 9);
    }
}
