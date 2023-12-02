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
        doubleMoveLeft = pawnColumn - 1;
        doubleMoveRight = pawnColumn + 1;
        moveLeft = pawnColumn - 2;
        moveRight = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
        transitionColumnOnLeft = pawnColumn - 1;
        transitionColumnOnRight = pawnColumn + 1;
    }

    void jumpToField() {
        Board.board[movementRow][movementColumn] = Player.playerPAWN;
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
    }

    void printMessageOfInvalidMove() {
        if (isRowAboveSelected()) {
            if (!(isMoveDiagonal()))
                System.out.println("The move is not diagonal");
            else if (!(isSelectedFieldEmpty()))
                System.out.println("The selected field is not empty");
        } else if ((areTwoRowsAboveSelected())) { // tutaj w ogóle nie wchodzi przy wyborze 3/4
            if (!(isEnemyOnLeft()) || !(isEnemyOnRight()))
                System.out.println("Any enemy pawn on transition field");
            else if (!(isSelectedFieldEmpty()))
                System.out.println("The selected field is not empty");
        } else {
            if (isMovementTooHigh())
                System.out.println("The movement is too high");
            else if (isMovementTooLow())
                System.out.println("Only upward movement is allowed");
        }
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
    boolean isMovementTooLow() {
        return (movementRow > twoRowsAbovePawn);
    }
    boolean isMovementTooHigh() {
        return (movementRow < pawnRow);
    }

    boolean isRowAboveSelected() {
        return (movementRow == rowAbovePawn &&
                (movementColumn == moveLeft || movementColumn == moveRight));
    }
    boolean areTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbovePawn && (isEnemyOnLeft() || isEnemyOnRight())
                && (movementColumn == moveLeft || movementColumn == moveRight));
    }


    boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }


    boolean isEnemyOnLeft() {
        return (Board.board[rowAbovePawn][transitionColumnOnLeft] == Player.computerPAWN
                && movementColumn == moveLeft);
    }

    boolean isEnemyOnRight() {
        return (Board.board[rowAbovePawn][transitionColumnOnRight] == Player.computerPAWN
                && movementColumn == moveRight);
    }

    void capturePawn() {
            jumpToField();
            if (isEnemyOnLeft()) {
                Board.board[rowAbovePawn][transitionColumnOnLeft] = Board.emptyField;
            } else if (isEnemyOnRight()) {
                Board.board[rowAbovePawn][transitionColumnOnRight] = Board.emptyField;
            }
            Player.compPawnNumbers -= 1;
    }
}


