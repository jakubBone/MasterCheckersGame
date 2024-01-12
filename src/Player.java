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

    public void performPawnMove() {
        setFieldsDetials();
        if (isPawnMoveValid()) {
            if (isRowAbovePawnSelected()) {
                jumpToField();
            } else if (areTwoRowsAbovePawnSelected() && isValidCapture()) {
                capturePawn();
            }
            GameLogic.currentPlayer = "Computer";
        } else {
            printMessageOfInvalidMove();
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
    private void capturePawn() {
            if (isEnemyOnPawnRoad(leftUp, leftUpAfterCapture) && movementColumn >= 0)
                captureEnemyPawn(rowAbovePawn, leftUp);
            else if (isEnemyOnPawnRoad(rightUp, rightUpAfterCapture) && movementColumn <= 8)
                captureEnemyPawn(rowAbovePawn, rightUp);
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
            System.out.println("The movement is too high. Please try again ;) \n");
        else if (!(isMoveUpward()))
            System.out.println("Only upward movement allowed. Please try again ;) \n");
        else if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty. Please try again ;) \n");
        else if (!(isPawnMoveDiagonal()))
            System.out.println("The move is not diagonal. Please try again ;) \n");
        else
            System.out.println("The is no enemy in transition. Please try again ;) \n");
    }
    private boolean isPawnMoveValid() {
        return (isMoveUpward() && isPawnMoveDiagonal() && isSelectedFieldEmpty());
    }

    private boolean isValidCapture(){
        return (isEnemyOnPawnRoad(leftUp, leftUpAfterCapture) || isEnemyOnPawnRoad(rightUp, rightUpAfterCapture));
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
