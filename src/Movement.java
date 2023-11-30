public class Movement {
    int movementRow; int movementColumn;
    int pawnRow; int pawnColumn;
    int moveLeft; int moveRight;
    int rowAbove; int rowBelow;
    int twoRowsAbove; int twoRowsBelow;
    int transitionFieldX; int transitionFieldY;


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
        moveLeft = pawnColumn - 1;
        moveRight = pawnColumn + 1;
        rowAbove = pawnRow - 1;
        rowBelow = pawnRow + 1;
        twoRowsAbove = pawnRow - 2;
        twoRowsBelow = pawnRow + 2;
        transitionFieldX = movementRow + 1;
        transitionFieldY = movementColumn - 1;
    }

    void printMessageOfInvalidMove(){
        if(!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if(!(isMovementUpward()))
            System.out.println("Only upward movement is allowed");
        else if (!(isEnemyOnTransition()))
            System.out.println("There is no enemy on transition field");
        else if (!isMovementOutOfRange()) // Correct it!
            System.out.println("The movement is out of valid range");
        else if(!(isMoveDiagonal()))
            System.out.println("The move is not diagonal");
    }

    boolean isMovementValid() {
        return (isMovementOutOfRange() && isMoveDiagonal() &&
                isMovementUpward() && isSelectedFieldEmpty());
    }
    boolean isMovementOutOfRange(){
        return (movementRow >= twoRowsAbove && movementRow <= twoRowsBelow);
    }

    boolean isMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }
    void jumpToField() {
            Board.board[movementRow][movementColumn] = Player.playerPAWN;
            Board.board[pawnRow][pawnColumn] = Board.emptyField;
    }

    boolean isMovementUpward(){
        return (isRowAboveSelected() || areTwoRowsAboveSelected());
    }
    boolean isRowAboveSelected() {
        return (movementRow == rowAbove && (movementColumn == moveLeft || movementColumn == moveRight));
    }
    boolean areTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbove);
    }

    boolean isSelectedFieldEmpty(){
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }
    boolean isEnemyOnTransition() {
        return (Board.board[transitionFieldX][transitionFieldY] == Player.computerPAWN);
    }

    void capturePawn() {
        if(isSelectedFieldEmpty() && isEnemyOnTransition()){
            Board.board[movementRow][movementColumn] = Player.playerPAWN;
            Board.board[transitionFieldX][transitionFieldY] = Board.emptyField;
            Board.board[pawnRow][pawnColumn] = Board.emptyField;
            Player.compPawnNumbers -= 1;
        }
        else
            System.out.println("There is no enemy in transition.5");
    }
}


