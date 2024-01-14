import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Computer {
    final static char computerPAWN = 'X';
    static int compPawnNumbers = 12;
    private boolean movePerformed = false;
    public void findPawnAndMove() {
        List<int[]> computerPawns = new ArrayList<>();
        ArrayList<int[]> pawnsPriority = new ArrayList<>();
        int currentPriority = Integer.MIN_VALUE;

        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == computerPAWN) {
                    computerPawns.add(new int[]{i, j});
                }
            }
        }
        performMove(computerPawns, pawnsPriority, currentPriority);
    }

    private void performMove(List<int[]> computerPawns, ArrayList<int[]> pawnsPriority,int currentPriority){
        System.out.println("Step 1");
        for(int[] pawnPosition : computerPawns) {
            movePerformed = false;
            int row = pawnPosition[0];
            int column = pawnPosition[1];
            int rowBelow = row + 1;
            int twoRowsBelow = row + 2;

            System.out.println(pawnPosition[0] + " || " + pawnPosition[1]);
            captureOnSideIfMoveSafe(row, column, rowBelow, twoRowsBelow);
            if(movePerformed)
                break;
        }
        setAndSortPriority(computerPawns, pawnsPriority, currentPriority);
        findAnotherBestMove(pawnsPriority);
    }

    private void captureOnSideIfMoveSafe(int compRow, int compColumn, int rowBelow, int twoRowsBelow) {
        int capturedLeft = compColumn - 1;
        int capturedRight = compColumn + 1;
        if (isMoveInRange(compRow, compColumn)) {
            if (compColumn == 2 && isPlayerOnLeft(compColumn, rowBelow) && Board.board[compRow + 2][compColumn - 2] == Board.emptyField) {
                handleCaptureOnSide(compRow, compColumn, twoRowsBelow, capturedLeft);
            }
        } else if (compColumn == 5 && isPlayerOnRight(compColumn, rowBelow) && Board.board[compRow + 2][compColumn + 2] == Board.emptyField) {
            handleCaptureOnSide(compRow, compColumn, twoRowsBelow, capturedRight);
        }
    }
    private void setAndSortPriority(List<int[]> computerPawns, ArrayList<int[]> pawnsPriority, int currentPriority) {
        System.out.println("Step 2");
        if (!movePerformed) {
            System.out.println("In Step 2");
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
                pawnsPriority.add(new int[]{compRow, compColumn, currentPriority});
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
        }
    }
    private void findAnotherBestMove(ArrayList<int[]> pawnsPriority) {
        int counter = 0;
        while(counter < pawnsPriority.size() && !movePerformed){
            int[] bestPriorityPawn = pawnsPriority.get(counter);
            int bestRow = bestPriorityPawn[0];
            int bestColumn = bestPriorityPawn[1];
            System.out.print("BestMove: ");
            System.out.println("[" + bestRow + "][" + bestColumn + "]");

            int rowBelow = bestRow + 1;
            int twoRowsBelow = bestRow + 2;
            int leftColumn = bestColumn - 1;
            int rightColumn = bestColumn + 1;

            if (bestPriorityPawn[2] == 10 || bestPriorityPawn[2] == 5)
                handleCapture(bestRow, bestColumn, rowBelow, twoRowsBelow, leftColumn, rightColumn);
            else if (bestPriorityPawn[2] == 8 || bestPriorityPawn[2] == 4)
                handleMove(bestRow, bestColumn, rowBelow, leftColumn, rightColumn);
            counter++;
        }
    }

    private void  handleCaptureOnSide(int compRow, int compColumn, int twoRowsBelow, int capturedColumn){
        System.out.println(">>>>>>>>>2<<<<<<<<<<");
        Board.board[compRow + 1][capturedColumn] = Board.emptyField;
        Board.board[compRow][compColumn] = Board.emptyField;
        System.out.println("captured column: " + capturedColumn);
        Board.board[twoRowsBelow][compColumn - 2] = computerPAWN;
        Player.playerPawnNumbers -= 1;
        GameLogic.currentPlayer = "Human";
        movePerformed = true;
        System.out.println("Capture performed");
    }

    private void handleMove(int compRow, int compColumn, int rowBelow, int leftColumn, int rightColumn){

        System.out.println(isMoveInRange(rowBelow, leftColumn)+ " " + " " + isMoveInRange(rowBelow, rightColumn));

        if(isMoveInRange(rowBelow, leftColumn)) {
            if (Board.board[rowBelow][leftColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = computerPAWN;
                System.out.println("moved to left");
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Move performed");
            }
        } else if(isMoveInRange(rowBelow, rightColumn)) {
            if (Board.board[rowBelow][rightColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = computerPAWN;
                System.out.println("moved to right");
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Move performed");
            }
        } else if(Board.board[rowBelow][leftColumn] == Board.emptyField && Board.board[rowBelow][rightColumn] == Board.emptyField){
            int randomColumn = getRandomColumn(leftColumn, rightColumn);
            Board.board[compRow][compColumn] = Board.emptyField;
            Board.board[rowBelow][randomColumn] = computerPAWN;
            System.out.println("moved randomly");
            GameLogic.currentPlayer = "Human";
            movePerformed = true;
            System.out.println("Move performed");
        }
    }
    private void handleCapture(int compRow, int compColumn, int rowBelow, int twoRowsBelow, int leftColumn, int rightColumn) {
        if (tryCapture(compRow, compColumn, rowBelow, twoRowsBelow, leftColumn, rightColumn - 1, -1)) {
            System.out.println("Capture on left");
        } else if (tryCapture(compRow, compColumn, rowBelow, twoRowsBelow, rightColumn, rightColumn + 1, 1)) {
            System.out.println("Capture on right");
        } else if (isPlayerOnBothSides(compColumn, rowBelow)) {
            if (tryCapture(compRow, compColumn, rowBelow, twoRowsBelow, leftColumn, leftColumn - 1, -1)) {
                System.out.println("Capture on left");
            } else if (tryCapture(compRow, compColumn, rowBelow, twoRowsBelow, rightColumn, rightColumn + 1, 1)) {
                System.out.println("Capture on right");
            } else {
                handleRandomCapture(compRow, compColumn, rowBelow, leftColumn, rightColumn);
            }
        }
    }

    private boolean tryCapture(int compRow, int compColumn, int rowBelow, int twoRowsBelow, int captureColumn, int newColumn, int direction) {
        if (isMoveInRange(rowBelow, captureColumn) && isPlayerToCapture(compColumn, rowBelow, captureColumn) &&
                Board.board[twoRowsBelow][newColumn] == Board.emptyField) {
            performCapture(compRow, compColumn, rowBelow, twoRowsBelow, captureColumn, newColumn);
            return true;
        }
        return false;
    }
    private void performCapture(int compRow, int compColumn, int rowBelow, int twoRowsBelow, int captureColumn, int newColumn) {
        Board.board[compRow][compColumn] = Board.emptyField;
        Board.board[rowBelow][captureColumn] = Board.emptyField;
        Board.board[twoRowsBelow][newColumn] = Computer.computerPAWN;
        Player.playerPawnNumbers -= 1;
        GameLogic.currentPlayer = "Human";
        movePerformed = true;
        System.out.println("Capture performed");
    }
    private void handleRandomCapture(int compRow, int compColumn, int rowBelow, int leftColumn, int rightColumn) {
        int randomColumn = getRandomColumn(leftColumn, rightColumn);
        Board.board[compRow][compColumn] = Board.emptyField;
        Board.board[rowBelow][randomColumn] = computerPAWN;
        System.out.println("Moved randomly");
        GameLogic.currentPlayer = "Human";
        movePerformed = true;
        System.out.println("Move performed");
    }

    private boolean isPlayerToCapture(int compColumn, int rowBelow, int captureColumn) {
        return (Board.board[rowBelow][captureColumn] == Player.playerPAWN ||
                Board.board[rowBelow][compColumn + (captureColumn - compColumn)] == Player.playerPAWN);
    }

    private int calculatePriority(int compColumn, int compRow, int rowBelow, int twoRowsBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, twoRowsBelow)) {
            System.out.println("CAPTURE POSSIBLE");
            if(isRiskAfterCapture(compRow, compColumn, rowBelow, threeRowsBelow)){
                if(!isRiskAfterMove(compColumn, compColumn, twoRowsBelow)){
                    return 8;
                }
                else
                    return 4;
            } else
                return 10;
        } else {
            return isRiskAfterMove(compRow, compColumn, twoRowsBelow) ? 4 : 8;
        }
    }
    private boolean isPlayerOnLeftAfterCaputre(int compColumn, int threeRowsBelow, int leftColumn){
        return (Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN
                || Board.board[threeRowsBelow][compColumn - 1] == Player.playerPAWN);
    }
    private boolean isPlayerOnRightAfterCaputre(int compColumn, int threeRowsBelow, int rightColumn){
        return (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN
                || Board.board[threeRowsBelow][compColumn + 1] == Player.playerPAWN);
    }
    private boolean isPlayerOnBothSidesAfterCaputre(int compColumn, int threeRowsBelow, int leftColumn, int rightColumn){
        return ((Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN || Board.board[threeRowsBelow][compColumn + 1] == Player.playerPAWN) ||
                (Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN || Board.board[threeRowsBelow][compColumn - 1] == Player.playerPAWN));
    }
    private boolean isRiskAfterCapture(int compRow, int compColumn, int rowBelow, int threeRowsBelow) {
        int leftColumn = compColumn - 3;
        int rightColumn = compColumn + 3;
        System.out.println("AS");

        if (compRow >= 0 && compRow <= 4 && (isMoveInRange(threeRowsBelow, leftColumn) || isMoveInRange(threeRowsBelow, rightColumn))) {
            System.out.println("X");

            if ((compColumn == 3 || compColumn == 4) && isPlayerOnBothSides(compColumn, rowBelow)) {
                return isPlayerOnBothSidesAfterCaputre(compColumn, threeRowsBelow, leftColumn, rightColumn);
            } else if ((compColumn == 3 || compColumn == 4) && isPlayerOnLeft(compColumn, rowBelow)) {
                return isPlayerOnLeftAfterCaputre(compColumn, threeRowsBelow, leftColumn);
            } else if ((compColumn == 3 || compColumn == 4) && isPlayerOnRight(compColumn, rowBelow)) {
                System.out.println("XX");
                return isPlayerOnRightAfterCaputre(compColumn, threeRowsBelow, rightColumn);
            } else if ((compColumn == 0 || compColumn == 1 || compColumn == 2) && isPlayerOnRight(compColumn, rowBelow)) {
                System.out.println("XXX");
                return isPlayerOnRightAfterCaputre(compColumn, threeRowsBelow, rightColumn);
            } else if ((compColumn == 5 || compColumn == 6 || compColumn == 7) && isPlayerOnLeft(compColumn, rowBelow)) {
                System.out.println("XYZ");
                return isPlayerOnLeftAfterCaputre(compColumn, threeRowsBelow, leftColumn);
            }
        }
        return false;
    }


    private boolean isRiskAfterMove(int compRow, int compColumn, int twoRowsBelow) {
        if (compRow >= 0 && compRow <= 4) { // 5 zamiast 4
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

        if (isMoveInRange(twoRowsBelow, leftAfterCapture) || isMoveInRange(twoRowsBelow, rightAfterCapture)) {
            if ((compColumn == 0 || compColumn == 1) && isPlayerOnRight(compColumn, rowBelow)) {
                return Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField;
            } else if ((compColumn == 6 || compColumn == 7) && isPlayerOnLeft(compColumn, rowBelow)) {
                System.out.println("możliwe bicie z col 6");
                return Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField;
            } else if (compColumn >= 2 && compColumn <= 5) {
                return (isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField) ||
                        (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField);
            }
        }
        return false;
    }

    private boolean isMoveInRange(int row, int column) {
        return (row >= 0 && row < 9 && column >= 0 && column < 9);
    }
    private boolean isPlayerOnLeft(int compColumn, int rowBelow) {
        System.out.println("1-2");
        int leftColumn = compColumn - 1;
        return (Board.board[rowBelow][leftColumn] == Player.playerPAWN && isMoveInRange(rowBelow, leftColumn));
    }
    private boolean isPlayerOnRight(int compColumn, int rowBelow) {
        int rightColumn = compColumn + 1;
        return (Board.board[rowBelow][rightColumn] == Player.playerPAWN && isMoveInRange(rowBelow, rightColumn));
    }
    private boolean isPlayerOnBothSides(int compColumn, int rowBelow) {
        return (isPlayerOnLeft(compColumn, rowBelow) && isPlayerOnRight(compColumn, rowBelow) );
    }
    private int getRandomColumn(int leftAfterCapture, int rightAfterCapture) {
        Random random = new Random();
        return random.nextBoolean() ? leftAfterCapture : rightAfterCapture;
    }
}