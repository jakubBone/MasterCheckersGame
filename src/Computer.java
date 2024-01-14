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
        for(int[] pawnPosition : computerPawns) {
            movePerformed = false;
            int row = pawnPosition[0];
            int column = pawnPosition[1];
            int rowBelow = row + 1;
            int twoRowsBelow = row + 2;

            System.out.println(pawnPosition[0] + " || " + pawnPosition[1]);
            captureOnSideIfPossible(row, column, rowBelow, twoRowsBelow);
            if(movePerformed)
                break;
        }
        setAndSortPriority(computerPawns, pawnsPriority, currentPriority);
        findAnotherBestMove(pawnsPriority);
    }
    private void captureOnSideIfPossible(int compRow, int compColumn, int rowBelow, int twoRowsBelow) {
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
        if (!movePerformed) {
            for (int[] pawnPosition : computerPawns) {
                movePerformed = false;

                int compRow = pawnPosition[0];
                int compColumn = pawnPosition[1];
                int rowBelow = compRow + 1;
                int twoRowsBelow = compRow + 2;
                int threeRowsBelow = compRow + 3;

                currentPriority = calculatePriority(compColumn, compRow, rowBelow, twoRowsBelow, threeRowsBelow);
                pawnsPriority.add(new int[]{compRow, compColumn, currentPriority});
            }

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
        }
    }
    private void findAnotherBestMove(ArrayList<int[]> pawnsPriority) {
        int counter = 0;
        while(counter < pawnsPriority.size() && !movePerformed){
            int[] bestPriorityPawn = pawnsPriority.get(counter);
            int bestRow = bestPriorityPawn[0];
            int bestColumn = bestPriorityPawn[1];
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
        Board.board[compRow + 1][capturedColumn] = Board.emptyField;
        Board.board[compRow][compColumn] = Board.emptyField;
        Board.board[twoRowsBelow][compColumn - 2] = computerPAWN;
        Player.playerPawnNumbers -= 1;
        GameLogic.currentPlayer = "Human";
        movePerformed = true;
    }
    private void handleMove(int compRow, int compColumn, int rowBelow, int leftColumn, int rightColumn){

        if(isMoveInRange(rowBelow, leftColumn)) {
            if (Board.board[rowBelow][leftColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = computerPAWN;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
            }
        } else if(isMoveInRange(rowBelow, rightColumn)) {
            if (Board.board[rowBelow][rightColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = computerPAWN;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
            }
        } else if(Board.board[rowBelow][leftColumn] == Board.emptyField && Board.board[rowBelow][rightColumn] == Board.emptyField){
            int randomColumn = getRandomColumn(leftColumn, rightColumn);
            Board.board[compRow][compColumn] = Board.emptyField;
            Board.board[rowBelow][randomColumn] = computerPAWN;
            GameLogic.currentPlayer = "Human";
            movePerformed = true;
        }
    }
    private void handleCapture(int compRow, int compColumn, int rowBelow, int twoRowsBelow, int leftColumn, int rightColumn) {

        if (isMoveInRange(compRow, leftColumn) && isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftColumn - 1] == Board.emptyField) {
            Board.board[compRow][compColumn] = Board.emptyField;
            Board.board[rowBelow][leftColumn] = Board.emptyField;
            Board.board[twoRowsBelow][leftColumn - 1] = Computer.computerPAWN;
            Player.playerPawnNumbers -= 1;
            GameLogic.currentPlayer = "Human";
            movePerformed = true;
        } else if (isMoveInRange(rowBelow, rightColumn) && isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightColumn + 1] == Board.emptyField) {
            Board.board[compRow][compColumn] = Board.emptyField;
            Board.board[rowBelow][rightColumn] = Board.emptyField;
            Board.board[twoRowsBelow][rightColumn + 1] = Computer.computerPAWN;
            Player.playerPawnNumbers -= 1;
            GameLogic.currentPlayer = "Human";
            movePerformed = true;
        } else if (isPlayerOnBothSides(compColumn, rowBelow) && isMoveInRange(compRow, leftColumn) && isMoveInRange(rowBelow, rightColumn)) { // BOTH
            if (Board.board[twoRowsBelow][leftColumn - 1] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = Board.emptyField;
                Board.board[twoRowsBelow][leftColumn - 1] = Computer.computerPAWN;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
            } else if (Board.board[twoRowsBelow][rightColumn + 1] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn + 1] = Computer.computerPAWN;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
            } else if (Board.board[twoRowsBelow][leftColumn - 1] == Board.emptyField &&
                    Board.board[twoRowsBelow][rightColumn + 1] == Board.emptyField ) {
                int randomColumn = getRandomColumn(leftColumn - 1, rightColumn + 1);
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][randomColumn] = computerPAWN;
                if(randomColumn == leftColumn - 1)
                    Board.board[compRow][leftColumn] = Board.emptyField;
                else
                    Board.board[compRow][rightColumn] = Board.emptyField;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
            }
        }
    }
    private int calculatePriority(int compColumn, int compRow, int rowBelow, int twoRowsBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, twoRowsBelow)) {
            if (isRiskAfterCapture(compRow, compColumn, rowBelow, threeRowsBelow)) {
                return isRiskAfterMove(compRow, compColumn, twoRowsBelow) ? 4 : 8;
            } else {
                return 10;
            }
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

        if (compRow >= 0 && compRow <= 4 && (isMoveInRange(threeRowsBelow, leftColumn) || isMoveInRange(threeRowsBelow, rightColumn))) {
            if ((compColumn == 3 || compColumn == 4) && isPlayerOnBothSides(compColumn, rowBelow)) {
                return isPlayerOnBothSidesAfterCaputre(compColumn, threeRowsBelow, leftColumn, rightColumn);
            } else if ((compColumn == 3 || compColumn == 4) && isPlayerOnLeft(compColumn, rowBelow)) {
                return isPlayerOnLeftAfterCaputre(compColumn, threeRowsBelow, leftColumn);
            } else if ((compColumn == 3 || compColumn == 4) && isPlayerOnRight(compColumn, rowBelow)) {
                return isPlayerOnRightAfterCaputre(compColumn, threeRowsBelow, rightColumn);
            } else if ((compColumn == 0 || compColumn == 1 || compColumn == 2) && isPlayerOnRight(compColumn, rowBelow)) {
                return isPlayerOnRightAfterCaputre(compColumn, threeRowsBelow, rightColumn);
            } else if ((compColumn == 5 || compColumn == 6 || compColumn == 7) && isPlayerOnLeft(compColumn, rowBelow)) {
                return isPlayerOnLeftAfterCaputre(compColumn, threeRowsBelow, leftColumn);
            }
        }
        return false;
    }
    private boolean isRiskAfterMove(int compRow, int compColumn, int twoRowsBelow) {
        if (compRow >= 0 && compRow <= 4) {
            int leftColumn = compColumn - 2;
            int rightColumn = compColumn + 2;
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