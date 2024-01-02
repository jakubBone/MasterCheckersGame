public class Player {
    public int movementRow;
    public int movementColumn;
    public int pawnRow;
    public int pawnColumn;
    private int leftUp;
    private int rightUp;
    private int leftUpAfterCapture;
    private int rightUpAfterCapture;
    private int rowAbovePawn;
    private int twoRowsAbovePawn;
    final static char playerPAWN  = 'O';
    static int playerPawnNumbers = 12;
    final static char playerQueenPAWN = '@';

    public void performMove() {
        setFieldsDetials();
        if (ifQueenSelected()) {
            performQueenMove();
        } else if (ifPawnSelected()) {
            performPawnMove();
        }
    }
    void setFieldsDetials(){
        leftUp = pawnColumn - 1;
        rightUp = pawnColumn + 1;
        leftUpAfterCapture = pawnColumn - 2;
        rightUpAfterCapture = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
    }
    private void performPawnMove() {
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
        } else {
            System.out.println("Message Invalid");
            printMessageOfInvalidQueenMove();
        }
        GameLogic.currentPlayer = "Computer";
    }
    private void capturePawn() {
            if (isEnemyOnPawnRoad(leftUp, leftUpAfterCapture) && movementColumn >= 0) {
                captureEnemyPawn(rowAbovePawn, leftUp);
            } else if (isEnemyOnPawnRoad(rightUp, rightUpAfterCapture) && movementColumn <= 8) {
                captureEnemyPawn(rowAbovePawn, rightUp);
            } else
                System.out.println("There is no enemy pawn in the transition field");
    }
    private void captureEnemyPawn(int row, int column){
        Board.board[row][column] = Board.emptyField;
        jumpToField();
        Computer.compPawnNumbers -= 1;
    }
    private void capturePawnWithQueen(){
        for (int i = Math.min(pawnRow, movementRow) + 1; i < Math.max(pawnRow, movementRow); i++) {
            for (int j = Math.min(pawnColumn, movementColumn) + 1; j < Math.max(pawnColumn, movementColumn); j++){
                if (Board.board[i][j] != Board.emptyField && Board.board[i][j] != playerPAWN) {
                    Board.board[i][j] = Board.emptyField;
                }
            }
        }
            Computer.compPawnNumbers -= 1;
    }
    private boolean isEnemyOnQueenRoad() {
        for (int i = Math.min(pawnRow, movementRow) + 1; i < Math.max(pawnRow, movementRow); i++) {
            for (int j = Math.min(pawnColumn, movementColumn) + 1; j < Math.max(pawnColumn, movementColumn); j++) {
                if (Board.board[i][j] == 'X') {
                    return true;
                }
            }
        }
        return false;
    }
    private void jumpToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        if (movementRow == 0)
            Board.board[movementRow][movementColumn] = playerQueenPAWN;
        else
            Board.board[movementRow][movementColumn] = playerPAWN;
    }
    private void jumpQueenToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        Board.board[movementRow][movementColumn] = playerQueenPAWN;
    }
    private void printMessageOfInvalidMove() {
        if (isPawnMoveTooHigh())
            System.out.println("The movement is too high");
        else if (!(isMoveUpward()))
            System.out.println("Only upward movement is allowed");
        else if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if (!(isPawnMoveDiagonal()))
            System.out.println("The move is not diagonal");
        else
            System.out.println("Invalid move");
    }
    private void printMessageOfInvalidQueenMove() {
        if (!(isQueenMoveDiagonal()))
            System.out.println("The move is not diagonal");
        else if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else
            System.out.println("Invalid move");
    }
    private boolean isPawnMoveValid() {
        return (isMoveUpward() && isPawnMoveDiagonal() && isSelectedFieldEmpty());
    }
    private boolean isQueenMoveValid() {
        return isSelectedFieldEmpty() && isQueenMoveDiagonal();
    }
    private boolean isPawnMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }
    private boolean isQueenMoveDiagonal() {
        int rowDifference = Math.abs(movementRow - pawnRow);
        int colDifference = Math.abs(movementColumn - pawnColumn);
        return rowDifference == colDifference;
    }
    private boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }
    boolean ifPlayerPawnSelected() {
        return (ifPawnSelected() || ifQueenSelected());
    }
    private boolean ifPawnSelected() {
        return Board.board[pawnRow][pawnColumn] == playerPAWN;
    }
    private boolean ifQueenSelected() {
        return Board.board[pawnRow][pawnColumn] == playerQueenPAWN;
    }
    private boolean isMoveUpward() {
        return (isRowAbovePawnSelected() || areTwoRowsAbovePawnSelected());
    }
    private boolean isPawnMoveTooHigh() {
        return (movementRow < twoRowsAbovePawn);
    }
    private boolean isRowAbovePawnSelected() {
        return (movementRow == rowAbovePawn &&
                (movementColumn == leftUp || movementColumn == rightUp));
    }
    private boolean areTwoRowsAbovePawnSelected() {
        return (movementRow == twoRowsAbovePawn &&
                (movementColumn == leftUpAfterCapture || movementColumn == rightUpAfterCapture));
    }
    private boolean isEnemyOnPawnRoad(int diagonalUp, int jumpedField){
        return (Board.board[rowAbovePawn][diagonalUp] == Computer.computerPAWN
                && movementColumn == jumpedField);
    }
}
