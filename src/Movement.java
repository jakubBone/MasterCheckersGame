import java.util.Scanner;

public class Movement {
    int movementRow;
    int movementColumn;
    int pawnRow;
    int pawnColumn;
    int moveLeftColumn;
    int moveRightColumn;
    int doubleMoveLeftColumn;
    int doubleMoveRightColumn;
    int rowAbovePawn;
    int twoRowsAbovePawn;

    Scanner scanner = new Scanner(System.in);

    void askForMove() {
        while (Player.compPawnNumbers > 0 || Player.playerPawnNumbers > 0) {
            Board.printBoard();
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            pawnColumn = scanner.nextInt();
            if (ifPlayerPawnSelected()) {
                System.out.println();
                while (Player.currentPlayer.equals("Human")) {
                    System.out.println("WHICH FIELD TO MOVE?");
                    System.out.print("Select field row: ");
                    movementRow = scanner.nextInt();
                    System.out.print("Select field column: ");
                    movementColumn = scanner.nextInt();
                    System.out.println();
                    //performMove();
                    performGeneralMove();
                }
            } else
                System.out.println("Incorrect choice. It's not your pawn");
            // computerMove() y change player after movement
            Player.currentPlayer = "Human";
        }
        System.out.println("Game over");
    }

    ///////////////////////////////
    boolean ifPlayerPawnSelected() {
        return (Board.board[pawnRow][pawnColumn] == Player.playerPAWN ||
                Board.board[pawnRow][pawnColumn] == Player.playerQueenPawn);
    }

    void performGeneralMove() {
        if (isQueenMoveValid()) {
            performQueenMove();
            System.out.println("performed the Queen move");
        }
        else {
        performMove();
        System.out.println("performed the Queen move");
        }
    }
    void performQueenMove(){
            if(isQueenMoveValid())
                jumpToField();
            else
                printMessageOfInvalidQueenMove();

    }
    boolean isQueenMoveValid(){
        return isSelectedFieldEmpty() && isEnemyOnQueenRoad() && isQueenMoveInRange();
    }

    boolean isEnemyOnQueenRoad(){
        return (Board.board[movementRow + 1][movementColumn] == 'X' ||
                Board.board[movementRow][movementColumn - 1] == 'X');

    }
    boolean isQueenMoveInRange(){
        return ((movementRow >=0 && movementRow <= 8) || (movementColumn >=0 && movementColumn <=8));
    }

    void printMessageOfInvalidQueenMove() {
        if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if (!(isEnemyOnQueenRoad()))
            System.out.println("There is no enemy on Queen road");
        else if(!(isQueenMoveInRange()))
            System.out.println("Movement out of the range");
        else
            System.out.println("Invalid move");
    }
    ///////////////////////////////////////////


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

    /*void performMove() {
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
    }*/

    void setMovementDetails() {
        moveLeftColumn = pawnColumn - 1;
        moveRightColumn = pawnColumn + 1;
        doubleMoveLeftColumn = pawnColumn - 2;
        doubleMoveRightColumn = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
    }

    void jumpToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        if(movementRow == 0)
            setQueen();
        else
            Board.board[movementRow][movementColumn] = Player.playerPAWN;
    }
    void setQueen(){
            Board.board[movementRow][movementColumn] = Player.playerQueenPawn;
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
                System.out.println("Invalid move");
    }

    boolean isMovementValid() {
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
                (movementColumn == moveLeftColumn || movementColumn == moveRightColumn));
    }

    boolean areTwoRowsAboveSelected() {
        return (movementRow == twoRowsAbovePawn &&
                (movementColumn == doubleMoveLeftColumn || movementColumn == doubleMoveRightColumn));
    }

    boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }

    boolean isEnemyOnLeft() {
        return (Board.board[rowAbovePawn][moveLeftColumn] == Player.computerPAWN
                && movementColumn == doubleMoveLeftColumn);
    }

    boolean isEnemyOnRight() {
        return (Board.board[rowAbovePawn][moveRightColumn] == Player.computerPAWN
                && movementColumn == doubleMoveRightColumn);
    }

    void capturePawn() {
            if (isEnemyOnLeft() && movementColumn >=0) {
                Board.board[rowAbovePawn][moveLeftColumn] = Board.emptyField;
                jumpToField();
                Player.compPawnNumbers -= 1;
            } else if (isEnemyOnRight() && movementColumn <=8) {
                Board.board[rowAbovePawn][moveRightColumn] = Board.emptyField;
                jumpToField();
                Player.compPawnNumbers -= 1;
            }
            else
                System.out.println("There is no enemy pawn in the transition field");
    }
}


