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
        System.out.println("Step 1");
        for(int[] pawnPosition : computerPawns) {
            movePerformed = false;
            int row = pawnPosition[0];
            int col = pawnPosition[1];
            System.out.println(pawnPosition[0] + " || " + pawnPosition[1]);
            captureOnSides(row, col, row + 1);
            if(movePerformed)
                break;
        }
        System.out.println("Step 2");
        if(!movePerformed){
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
            while(counter < pawnsPriority.size() && !movePerformed){
                int[] bestPriorityPawn = pawnsPriority.get(counter);
                int bestRow = bestPriorityPawn[0];
                int bestColumn = bestPriorityPawn[1];
                System.out.print("BestMove: ");
                System.out.println("[" + bestRow + "][" + bestColumn + "]");

                int rowBelow = bestRow + 1;
                int twoRowsBelow = bestRow + 2;

                if (bestPriorityPawn[2] == 10)
                    capture(bestRow, bestColumn, rowBelow, twoRowsBelow);
                else if(bestPriorityPawn[2] == 8)
                    move(bestRow, bestColumn, rowBelow);
                else if(bestPriorityPawn[2] == 5)
                    capture(bestRow, bestColumn, rowBelow, twoRowsBelow);
                else if(bestPriorityPawn[2] == 4)
                    move(bestRow, bestColumn, rowBelow);

                counter++;
            }
        }

    }
    private void move(int compRow, int compColumn, int rowBelow){
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;
        if(isMoveInRange(rowBelow, leftColumn) && isMoveInRange(rowBelow, rightColumn)) {
            if(Board.board[rowBelow][leftColumn] == Board.emptyField){
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = computerPAWN;
                System.out.println("moved to left");
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Move performed");
            } else if (Board.board[rowBelow][rightColumn] == Board.emptyField){
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = computerPAWN;
                System.out.println("moved to right");
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Move performed");
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
    }

    private void capture(int compRow, int compColumn, int rowBelow, int twoRowsBelow) {
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;
        if(isMoveInRange(compRow, leftColumn) && isMoveInRange(rowBelow, rightColumn)){
            if (isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftColumn - 1] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = Board.emptyField;
                Board.board[twoRowsBelow][leftColumn - 1] = Computer.computerPAWN;
                Player.playerPawnNumbers -= 1;
                System.out.println("capture on left");
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Move performed");
            } else if (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightColumn + 1] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = Board.emptyField;
                Board.board[twoRowsBelow][rightColumn + 1] = Computer.computerPAWN;
                Player.playerPawnNumbers -= 1;
                System.out.println("capture on right");
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Move performed");
            } else if (isPlayerOnBothSides(compColumn, rowBelow)) { // BOTH
                if (Board.board[twoRowsBelow][leftColumn - 1] == Board.emptyField) { //left
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][leftColumn] = Board.emptyField;
                    Board.board[twoRowsBelow][leftColumn - 1] = Computer.computerPAWN;
                    Player.playerPawnNumbers -= 1;
                    System.out.println("Both: capture on left");
                    GameLogic.currentPlayer = "Human";
                    movePerformed = true;
                    System.out.println("Move performed");
                } else if (Board.board[twoRowsBelow][rightColumn + 1] == Board.emptyField) { //right
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][rightColumn] = Board.emptyField;
                    Board.board[rowBelow][rightColumn + 1] = Computer.computerPAWN;
                    Player.playerPawnNumbers -= 1;
                    System.out.println("Both: capture on right");
                    GameLogic.currentPlayer = "Human";
                    movePerformed = true;
                    System.out.println("Move performed");
                } else if (Board.board[twoRowsBelow][leftColumn - 1] == Board.emptyField &&
                        Board.board[twoRowsBelow][rightColumn + 1] == Board.emptyField ) { //both
                    int randomColumn = getRandomColumn(leftColumn - 1, rightColumn + 1);
                    Board.board[compRow][compColumn] = Board.emptyField;
                    Board.board[rowBelow][randomColumn] = computerPAWN;
                    if(randomColumn == leftColumn - 1)
                        Board.board[compRow][leftColumn] = Board.emptyField;
                    else
                        Board.board[compRow][rightColumn] = Board.emptyField;
                    Player.playerPawnNumbers -= 1;
                    System.out.println("moved randomly");
                    GameLogic.currentPlayer = "Human";
                    movePerformed = true;
                    System.out.println("Move performed");
                }
            }
        }
    }

    private void captureOnSides(int compRow, int compColumn, int rowBelow) {
        int twoRowsBelow = compRow + 2;
        int capturedLeft = compColumn - 1;
        int capturedRight = compColumn + 1;
        if(isMoveInRange(compRow, compColumn)) {
            if (compColumn == 2 && isPlayerOnLeft(compColumn, rowBelow) && Board.board[compRow + 2][compColumn - 2] == Board.emptyField) {
                System.out.println(">>>>>>>>>2<<<<<<<<<<");
                Board.board[compRow + 1][capturedLeft] = Board.emptyField;
                Board.board[compRow][compColumn] = Board.emptyField;
                System.out.println("captured column: " + capturedLeft);
                Board.board[twoRowsBelow][compColumn - 2] = computerPAWN;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Capture performed");
            } else if (compColumn == 5 && isPlayerOnRight(compColumn, rowBelow) && Board.board[compRow + 2][compColumn + 2] == Board.emptyField) {
                System.out.println(">>>>>>>>>2<<<<<<<<<<");
                Board.board[compRow + 1][capturedRight] = Board.emptyField;
                Board.board[compRow][compColumn] = Board.emptyField;
                System.out.println("captured column: " + capturedLeft);
                Board.board[twoRowsBelow][compColumn + 2] = computerPAWN;
                Player.playerPawnNumbers -= 1;
                GameLogic.currentPlayer = "Human";
                movePerformed = true;
                System.out.println("Capture performed");
            } else
                System.out.println("Out of range in Capture on side");
        }
    }

    private int calculatePriority(int compColumn, int compRow, int rowBelow, int twoRowsBelow, int threeRowsBelow) {
        if (isCapturePossible(compColumn, rowBelow, twoRowsBelow)) {
            System.out.println("CAPTURE POSSIBLE");
            return isRiskAfterCapture(compRow,compColumn, rowBelow, threeRowsBelow) ? 5 : 10;
        } else {
            return isRiskAfterMove(compRow, compColumn, twoRowsBelow) ? 4 : 8;
        }
    }

    private boolean isPlayerOnleftAfterCaputre(int compColumn, int threeRowsBelow, int leftColumn){
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
        //if (compRow >= 0 && compRow <= 4 && isMoveInRange(threeRowsBelow, leftColumn) || isMoveInRange(threeRowsBelow, rightColumn)) {
        if (compRow >= 0 && compRow <= 4 && (isMoveInRange(threeRowsBelow, leftColumn) || isMoveInRange(threeRowsBelow, rightColumn))) {
            System.out.println("X");
            if(compColumn == 3 || compColumn == 4) {
                if (isPlayerOnBothSides(compColumn, rowBelow)) {
                    return isPlayerOnBothSidesAfterCaputre(compColumn, threeRowsBelow, leftColumn, rightColumn);
                } else if (isPlayerOnLeft(compColumn, rowBelow)) {
                    return isPlayerOnleftAfterCaputre(compColumn, threeRowsBelow, leftColumn);
                } else if (isPlayerOnRight(compColumn, rowBelow)) {
                    System.out.println("XX");
                    return isPlayerOnRightAfterCaputre(compColumn, threeRowsBelow, rightColumn);
                }
            }
            else if(compColumn == 0 || compColumn == 1 || compColumn == 2){
                if(isPlayerOnRight(compColumn, rowBelow)){
                    System.out.println("XXX");
                    return isPlayerOnRightAfterCaputre(compColumn, threeRowsBelow, rightColumn);
                }
            }
            else if(compColumn == 5 || compColumn == 6 || compColumn == 7){
                    System.out.println("XYZ");
                if (isPlayerOnLeft(compColumn, rowBelow)) {
                    return isPlayerOnleftAfterCaputre( compColumn, threeRowsBelow, leftColumn);
                }
            }
            }
        return false;
        }

    private boolean isRiskAfterMove(int compRow, int compColumn, int twoRowsBelow) {
        if (compRow >= 0 && compRow <= 5) { // 5 zamiast 4
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
            if (compColumn == 0 || compColumn == 1) {
                return isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField;
            } else if((compColumn == 6 || compColumn == 7)){
                System.out.println("możliwe bicie z col 6");
                return isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField;
            } else if (compColumn >= 2 && compColumn <= 5) {
            return ((isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField) ||
                    (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField));
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
