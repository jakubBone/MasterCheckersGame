public class Movement {
    int movementRow; int movementColumn;
    int pawnRow; int pawnColumn;
    int moveLeft; int moveRight;
    int rowAbove;
    int twoRowsAbove;
    int transitionColumnOnLeft;
    int transitionColumnOnRight;

    void performMove() {
        setMovementDetails();
        if (isMovementValid()) {
            System.out.println("Flag isMovementValid()"); // flag 1
            if (isRowAboveSelected()) {
                System.out.println("Flag isRowAboveSelected()"); // flag 2
                jumpToField();
                System.out.println("Flag jumpToField()");
            } else if (areTwoRowsAboveSelected()) {
                System.out.println("Flag isTwoRowsAboveSelected()"); // flag 3
                capturePawn();
                System.out.println("Flag capturePawn()"); // flag 4
            }
        } else {
            System.out.println("Flag Error"); // error flag
            printMessageOfInvalidMove();
        }
        Player.currentPlayer = "Computer";
    }

    void setMovementDetails() {
        moveLeft = pawnColumn - 2;
        moveRight = pawnColumn + 2;
        rowAbove = pawnRow - 1;
        twoRowsAbove = pawnRow - 2;
        transitionColumnOnLeft = pawnColumn - 1;
        transitionColumnOnRight = pawnColumn + 1;
    }

    void printMessageOfInvalidMove() {
        if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if (!(isMovementUpward()))
            System.out.println("Only upward movement is allowed");
        else if (!(isMoveDiagonal()))
            System.out.println("The move is not diagonal");
        else if (!(isEnemyOnLeft() || !(isEnemyOnRight())))
            System.out.println("Any enemy pawn on transition field");
    }

    void jumpToField() {
        Board.board[movementRow][movementColumn] = Player.playerPAWN;
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
    }

    boolean isMovementValid() {
        return (isMovementUpward() && isMoveDiagonal() && isSelectedFieldEmpty());
    }

    boolean isMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }

    boolean isMovementUpward() {
        return (isRowAboveSelected() || areTwoRowsAboveSelected());
    }

    boolean isRowAboveSelected() {
        return (movementRow == rowAbove && isMoveDiagonal());
    }

    boolean areTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbove && isMoveDiagonal() && (isEnemyOnLeft() || isEnemyOnRight())
                && (movementColumn == moveLeft || movementColumn == moveRight));

    }

    boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }


    boolean isEnemyOnLeft() {
        return (Board.board[rowAbove][transitionColumnOnLeft] == Player.computerPAWN
                && movementColumn == moveLeft);
    }

    boolean isEnemyOnRight() {
        return (Board.board[rowAbove][transitionColumnOnRight] == Player.computerPAWN
                && movementColumn == moveRight);
    }

    void capturePawn() {
            jumpToField();
            if (isEnemyOnLeft()) {
                Board.board[rowAbove][transitionColumnOnLeft] = Board.emptyField;
            } else if (isEnemyOnRight()) {
                Board.board[rowAbove][transitionColumnOnRight] = Board.emptyField;
            }
            Player.compPawnNumbers -= 1;
    }
}


