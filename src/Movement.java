public class Movement {
    int movementRow; int movementColumn;
    int pawnRow; int pawnColumn;
    int moveLeft; int moveRight;
    int rowAbove; int twoRowsAbove;
    int transitionFieldX; int transitionFieldY;

    void performMove() {
        setMovementDetails();
        if (isMovementValid()) {
            if (isRowAboveSelected()) {
                jumpToField();
            } else if (isTwoRowsAboveSelected()) {
                capturePawn();
            }
        }
        else
            isMovementInvalid();
        Player.currentPlayer = "Computer";
    }

    void setMovementDetails(){
        moveLeft = pawnColumn - 1;
        moveRight = pawnColumn + 1;
        rowAbove = pawnRow - 1;
        twoRowsAbove = pawnRow - 2;
        transitionFieldX = movementRow + 1;
        transitionFieldY = movementColumn - 1;
    }

    void isMovementInvalid(){
        if(!(isMoveDiagonal()))
            System.out.println("The move is not diagonal");
        else if(!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if (!(isEnemyOnTransition())) {
            System.out.println("There is no enemy on transition field");
        }
        else if (!(isEnemyOnTransition())) // Correct it!
            System.out.println("You can move only 1 row above ");
    }
    boolean isMovementValid() {
        return ((movementRow >= twoRowsAbove) && isMoveDiagonal());
    }
    boolean isMoveDiagonal() {
        return (!(movementColumn == pawnColumn));
    }
    void jumpToField() {
        if(isSelectedFieldEmpty()) {
            Board.board[movementRow][movementColumn] = Player.playerPAWN;
            Board.board[pawnRow][pawnColumn] = Board.emptyField;
        }
        else
            System.out.println("Field is not empty. Try again");
    }
    boolean isRowAboveSelected() {
        return (isSelectedFieldEmpty() && movementRow == rowAbove &&
                (movementColumn == moveLeft || movementColumn == moveRight));

    }
    boolean isTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbove && isEnemyOnTransition());
    }

    void capturePawn() {
        if(isSelectedFieldEmpty() && isEnemyOnTransition()){
            Board.board[movementRow][movementColumn] = Player.playerPAWN;
            Board.board[transitionFieldX][transitionFieldY] = Board.emptyField;
            Board.board[pawnRow][pawnColumn] = Board.emptyField;
            Player.compPawnNumbers -= 1;
        }
        else
            System.out.println("Selected field not empty or there is no enemy in transition. Try again");
    }


    boolean isSelectedFieldEmpty(){
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }

    boolean isEnemyOnTransition() {
        return (Board.board[transitionFieldX][transitionFieldY] == Player.computerPAWN);
    }
}
