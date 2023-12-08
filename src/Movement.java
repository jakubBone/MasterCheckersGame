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

    boolean ifPlayerPawnSelected(){
        return (ifPawnSelected() || ifQueenSelected());
    }
    boolean isQueenMovementValid(){
        return isSelectedFieldEmpty() && isQueenMovementDiagonal();
    }

    boolean isQueenMovementDiagonal(){
        boolean isDiagonal = false;
        if((movementRow / movementColumn) == (pawnRow + 1 / pawnColumn  + 1) ||
                (movementRow / movementColumn) == (pawnRow + 1 / pawnColumn  - 1) ||
                    (movementRow / movementColumn) == (pawnRow - 1 / pawnColumn  - 1) ||
                        (movementRow / movementColumn) == (pawnRow - 1 / pawnColumn  + 1)) {
            isDiagonal = true;
        }
        return isDiagonal;
    }

    boolean ifPawnSelected(){
        return Board.board[pawnRow][pawnColumn] == Player.playerPAWN;
    }
    boolean ifQueenSelected(){
        return Board.board[pawnRow][pawnColumn] == Player.playerQueenPAWN;
    }

    void performGeneralMove() {
        setMovementDetails();
        if (ifQueenSelected()) {
            performQueenMove();
            System.out.println("Queen move performed");
        } else if(ifPawnSelected()){
            performMove();
            System.out.println("Pawn move performed");
        }
    }

    void performQueenMove(){
            if(isQueenMovementValid()) {
                if(isEnemyOnQueenRoad()){
                    capturePawn();
                }
                else {
                    System.out.println("Flag QueenMovementValid()"); // flag 1
                    jumpQueenToField();
                    System.out.println("Flag jumpQueenToField()");
                }
            }
            else
                printMessageOfInvalidQueenMove();
        Player.currentPlayer = "Computer";
    }
    /*void performQueenMove(){
            if(isQueenMovementValid()) {
                System.out.println("Flag QueenMovementValid()"); // flag 1
                jumpQueenToField();
                System.out.println("Flag jumpQueenToField()");
            }
            else
                printMessageOfInvalidQueenMove();
        Player.currentPlayer = "Computer";
    }*/


    boolean isEnemyOnQueenRoad(){
        return (Board.board[movementRow + 1][movementColumn] == 'X' ||
                Board.board[movementRow][movementColumn - 1] == 'X');
    }

    void printMessageOfInvalidQueenMove() {
        if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if (!(isEnemyOnQueenRoad()))
            System.out.println("There is no enemy on Queen road");
        else
            System.out.println("Invalid move");
    }

    ///////////////////////////////////////////


    void performMove() {
        //setMovementDetails();
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
        moveLeftColumn = pawnColumn - 1;
        moveRightColumn = pawnColumn + 1;
        doubleMoveLeftColumn = pawnColumn - 2;
        doubleMoveRightColumn = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
    }

    void jumpToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        System.out.println("przed pętlą");
            if(movementRow == 0)
                setQueen();
            else
                Board.board[movementRow][movementColumn] = Player.playerPAWN;
    }

    void jumpQueenToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        Board.board[movementRow][movementColumn] = Player.playerQueenPAWN;
    }

    void setQueen(){
            Board.board[movementRow][movementColumn] = Player.playerQueenPAWN;
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

    boolean isEnemyOnPawnLeft() {
        return (Board.board[rowAbovePawn][moveLeftColumn] == Player.computerPAWN
                && movementColumn == doubleMoveLeftColumn);
    }

    boolean isEnemyOnPawnRight() {
        return (Board.board[rowAbovePawn][moveRightColumn] == Player.computerPAWN
                && movementColumn == doubleMoveRightColumn);
    }

    void capturePawn() {
        if (ifPawnSelected()){
            if (isEnemyOnPawnLeft() && movementColumn >= 0) {
                Board.board[rowAbovePawn][moveLeftColumn] = Board.emptyField;
                jumpToField();
                Player.compPawnNumbers -= 1;
            } else if (isEnemyOnPawnRight() && movementColumn <= 8) {
                Board.board[rowAbovePawn][moveRightColumn] = Board.emptyField;
                jumpToField();
                Player.compPawnNumbers -= 1;
            } else
                System.out.println("There is no enemy pawn in the transition field");
        }
        else if (ifQueenSelected()){
            if(isEnemyOnQueenRoad() && movementColumn >= 0 && movementColumn <=8){
                Board.board[rowAbovePawn][moveRightColumn] = Board.emptyField;
                jumpQueenToField();
                Player.compPawnNumbers -=1
            }
        }
    }
    void capturePawnWithQueen() {
        int nextQueenRow = pawnRow + 1;
        int nextQueenColumn = pawnColumn + 1;
        int
        for (int i = nextQueenRow; i <= movementRow; i++)
            for (int j = nextQueenColumn; j < movementColumn; j++) {
                if (Board.board[nextQueenRow][nextQueenColumn] = "X")
                    Board.board[nextQueenRow][nextQueenColumn] = Board.emptyField;

            }
    }
}


