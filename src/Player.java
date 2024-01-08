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

    public void performPawnMove() {
        setFieldsDetials();
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
    void setFieldsDetials(){
        leftUp = pawnColumn - 1;
        rightUp = pawnColumn + 1;
        leftUpAfterCapture = pawnColumn - 2;
        rightUpAfterCapture = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
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
    private void jumpToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
            Board.board[movementRow][movementColumn] = playerPAWN;
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
    private boolean isPawnMoveValid() {
        return (isMoveUpward() && isPawnMoveDiagonal() && isSelectedFieldEmpty());
    }
    private boolean isPawnMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }
    private boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }
    public boolean ifPawnSelected() {
        return Board.board[pawnRow][pawnColumn] == playerPAWN;
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
