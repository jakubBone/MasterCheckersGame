public class Movement {
    int movementRow; int movementColumn;
    int pawnRow; int pawnColumn;
    int moveLeft; int moveRight;
    int twoMovesLeft; int twoMovesRight;
    int rowAbove; int twoRowsAbove;
    int transitionColumnOnLeft; int transitionColumnOnRight;

    void performMove() {
        setMovementDetails();
        if (isMovementValid()) {
            System.out.println("isMovementValid()"); //
            if (isRowAboveSelected()) {
                System.out.println("isRowAboveSelected()"); //
                jumpToField();
                System.out.println("jumpToField()");
            } else if (areTwoRowsAboveSelected()) {
                System.out.println("isTwoRowsAboveSelected()"); //
                capturePawn();
                System.out.println("capturePawn()"); //
            }
        }
        else {
            System.out.println("Invalid move!!!!!!!!!!!!!!!!");
            printMessageOfInvalidMove();
        }
        Player.currentPlayer = "Computer";
    }

    void setMovementDetails(){
        moveLeft = pawnColumn - 1; moveRight = pawnColumn + 1;
        twoMovesLeft = pawnColumn - 2; twoMovesRight = pawnColumn + 2;
        rowAbove = pawnRow - 1;
        twoRowsAbove = pawnRow - 2;
        transitionColumnOnLeft = pawnColumn  - 1 ; transitionColumnOnRight = pawnColumn + 1;
    }

    void printMessageOfInvalidMove(){
        if(!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if(!(isMovementUpward()))
            System.out.println("Only upward movement is allowed");
        else if (!(isEnemyOnTransition()))
            System.out.println("There is no enemy on transition field");
        else if(!(isMoveDiagonal()))
            System.out.println("The move is not diagonal");
    }

    void jumpToField() {
        Board.board[movementRow][movementColumn] = Player.playerPAWN;
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
    }

    boolean isMovementValid() {
        return (isMovementUpward() && isMoveDiagonal()  && isSelectedFieldEmpty());
    }

    boolean isMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }

    boolean isMovementUpward(){
        return (isRowAboveSelected() || areTwoRowsAboveSelected());
    }
    boolean isRowAboveSelected() {
        return (movementRow == rowAbove && isMoveDiagonal() &&
                (movementColumn == moveLeft || movementColumn == moveRight));
    }
    boolean areTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbove && isMoveDiagonal() &&
                (movementColumn == twoMovesLeft || movementColumn == twoMovesRight));
    }

    boolean isSelectedFieldEmpty(){
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }
    boolean isEnemyOnTransition() {
        return ((Board.board[rowAbove][transitionColumnOnLeft] == Player.computerPAWN) ||
                (Board.board[rowAbove][transitionColumnOnRight] == Player.computerPAWN));
    }

    void capturePawn() {
        if(isSelectedFieldEmpty() && isEnemyOnTransition()){
            jumpToField();
            if(Board.board[rowAbove][transitionColumnOnLeft] == Player.computerPAWN)
                Board.board[rowAbove][transitionColumnOnLeft] = Board.emptyField;
            else if (Board.board[rowAbove][transitionColumnOnRight] == Player.computerPAWN)
                Board.board[rowAbove][transitionColumnOnRight] = Board.emptyField;
            Player.compPawnNumbers -= 1;
        }
    }

}


