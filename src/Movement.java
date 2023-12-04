public class Movement {
    int movementRow;
    int movementColumn;
    int pawnRow;
    int pawnColumn;
    int moveLeft;
    int moveRight;
    int doubleMoveLeft;
    int doubleMoveRight;
    int rowAbovePawn;
    int twoRowsAbovePawn;

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
        moveLeft = pawnColumn - 1;
        moveRight = pawnColumn + 1;
        doubleMoveLeft = pawnColumn - 2;
        doubleMoveRight = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
    }

    void jumpToField() {
        Board.board[movementRow][movementColumn] = Player.playerPAWN;
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
    }

    void printMessageOfInvalidMove() {
            if (isMovementTooHigh())
                System.out.println("The movement is too high");
            else if (isMovementTooLow())
                System.out.println("Only upward movement is allowed");
            else if (!(isSelectedFieldEmpty()))
                System.out.println("The selected field is not empty");
            else if (!(isMoveDiagonal()))
                    System.out.println("The move is not diagonal");
            else
                System.out.println("???");
    }

    /boolean isMovementValid() {
        return (isMoveUpward() && isMoveDiagonal() && isSelectedFieldEmpty());
    }

    boolean isMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }

    boolean isMoveUpward() {
        return (isRowAboveSelected() || areTwoRowsAboveSelected());
    }

    boolean isMovementTooLow() {
        return (movementRow >= pawnRow);
    }
    boolean isMovementTooHigh() {
        return (movementRow < twoRowsAbovePawn);
    }

    boolean isRowAboveSelected() {
        return (movementRow == rowAbovePawn &&
                (movementColumn == moveLeft || movementColumn == moveRight));
    }

    boolean areTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbovePawn &&
                (movementColumn == doubleMoveLeft || movementColumn == doubleMoveRight));
    }

    boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }

    boolean isEnemyOnLeft() {
        return (Board.board[rowAbovePawn][moveLeft] == Player.computerPAWN
                && movementColumn == doubleMoveLeft);
    }

    boolean isEnemyOnRight() {
        return (Board.board[rowAbovePawn][moveRight] == Player.computerPAWN
                && movementColumn == doubleMoveRight);
    }

    void capturePawn() {
            if (isEnemyOnLeft() && movementColumn >=0) {
                Board.board[rowAbovePawn][moveLeft] = Board.emptyField;
                jumpToField();
                Player.compPawnNumbers -= 1;
            } else if (isEnemyOnRight() && movementColumn <=8) {
                Board.board[rowAbovePawn][moveRight] = Board.emptyField;
                jumpToField();
                Player.compPawnNumbers -= 1;
            }
    }
}


