import java.util.Scanner;

public class Player {
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
    final static char playerPAWN  = 'O';
    static int playerPawnNumbers = 12;
    final static char playerQueenPAWN = '@';
    static String currentPlayer = "Human";
    Scanner scanner = new Scanner(System.in);

    void askForMove() {
        while (Computer.compPawnNumbers > 0 || playerPawnNumbers > 0) {
            Board.printBoard();
            System.out.println("WHICH PAWN TO CHOOSE?");
            System.out.print("Select pawn row: ");
            pawnRow = scanner.nextInt();
            System.out.print("Select pawn column: ");
            pawnColumn = scanner.nextInt();
            if (ifPlayerPawnSelected()) {
                System.out.println();
                while (currentPlayer.equals("Human")) {
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
            currentPlayer = "Human";
        }
        System.out.println("Game over");
    }
    void performGeneralMove() {
        setMovementDetails();
        if (ifQueenSelected()) {
            performQueenMove();
            System.out.println("Queen move performed");
        } else if (ifPawnSelected()) {
            performMove();
            System.out.println("Pawn move performed");
        }
    }
    void performMove() {
        if (isPawnMoveValid()) {
            System.out.println("Flag isMovementValid()"); // flag 1
            if (isRowAbovePawnSelected()) {
                System.out.println("Flag isRowAboveSelected()"); // flag 2
                jumpToField();
                System.out.println("Flag jumpToField()");
            } else if (areTwoRowsAbovePawnSelected()) {
                System.out.println("Flag isTwoRowsAboveSelected()"); // flag 3
                capturePawn();
                System.out.println("Flag capturePawn()"); // flag 4
            }
        } else {
            System.out.println("Flag Error"); // error flag
            printMessageOfInvalidMove();
        }
        currentPlayer = "Computer";
    }
    void performQueenMove() {
        System.out.println("In performQueenMove");
        if (isQueenMoveValid()) {
            System.out.println("Flag QueenMovementValid()"); // flag 1
            jumpQueenToField();
            System.out.println("Flag jumpQueenToField()"); // flag 2
            if (isEnemyOnQueenRoad()) {
                System.out.println("Flag isEnemyOnQueenRoad()");
                capturePawnWithQueen();
                System.out.println("Flag capturePawn();");
            }
        } else {
            System.out.println("Message Invalid");
            printMessageOfInvalidQueenMove();
        }
        currentPlayer = "Computer";
    }
    void capturePawn() {
        System.out.println("In capturePawn");
            if (isEnemyOnPawnLeft() && movementColumn >= 0) {
                Board.board[rowAbovePawn][moveLeftColumn] = Board.emptyField;
                jumpToField();
                Computer.compPawnNumbers -= 1;
            } else if (isEnemyOnPawnRight() && movementColumn <= 8) {
                Board.board[rowAbovePawn][moveRightColumn] = Board.emptyField;
                jumpToField();
                Computer.compPawnNumbers -= 1;
            } else
                System.out.println("There is no enemy pawn in the transition field");
        System.out.println("out capturePawn");
    }
    void capturePawnWithQueen(){
        System.out.println("Flag in capturePawnWithQueen");
        for (int i = Math.min(pawnRow, movementRow) + 1; i < Math.max(pawnRow, movementRow); i++) {
            for (int j = Math.min(pawnColumn, movementColumn) + 1; j < Math.max(pawnColumn, movementColumn); j++){
                if (Board.board[i][j] != Board.emptyField && Board.board[i][j] != playerPAWN) {
                    Board.board[i][j] = Board.emptyField;
                }
            }
        }
            Computer.compPawnNumbers -= 1;
        System.out.println("Flag in capturePawnWithQueen");
    }
    boolean isEnemyOnQueenRoad() {
        for (int i = Math.min(pawnRow, movementRow) + 1; i < Math.max(pawnRow, movementRow); i++) {
            for (int j = Math.min(pawnColumn, movementColumn) + 1; j < Math.max(pawnColumn, movementColumn); j++) {
                if (Board.board[i][j] == 'X') {
                    return true;
                }
            }
        }
        return false;
    }
    void jumpToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        System.out.println("przed pętlą");
        if (movementRow == 0)
            setQueen();
        else
            Board.board[movementRow][movementColumn] = playerPAWN;
    }
    void jumpQueenToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        Board.board[movementRow][movementColumn] = playerQueenPAWN;
    }
    void setMovementDetails() {
        moveLeftColumn = pawnColumn - 1;
        moveRightColumn = pawnColumn + 1;
        doubleMoveLeftColumn = pawnColumn - 2;
        doubleMoveRightColumn = pawnColumn + 2;
        rowAbovePawn = pawnRow - 1;
        twoRowsAbovePawn = pawnRow - 2;
    }
    void setQueen() {
        Board.board[movementRow][movementColumn] = playerQueenPAWN;
    }
    void printMessageOfInvalidMove() {
        if (isPawnMoveTooHigh())
            System.out.println("The movement is too high");
        else if (isPawnMoveTooLow())
            System.out.println("Only upward movement is allowed");
        else if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else if (!(isPawnMoveDiagonal()))
            System.out.println("The move is not diagonal");
        else
            System.out.println("Invalid move");
    }
    void printMessageOfInvalidQueenMove() {
        if (!(isQueenMoveDiagonal()))
            System.out.println("The move is not diagonal");
        else if (!(isSelectedFieldEmpty()))
            System.out.println("The selected field is not empty");
        else
            System.out.println("Invalid move");
    }
    
    boolean isPawnMoveValid() {
        return (isMoveUpward() && isPawnMoveDiagonal() && isSelectedFieldEmpty());
    }
    boolean isQueenMoveValid() {
        return isSelectedFieldEmpty() && isQueenMoveDiagonal();
    }
    boolean isPawnMoveDiagonal() {
        return (!(movementColumn == pawnColumn) && !(movementRow == pawnRow));
    }
    boolean isQueenMoveDiagonal() {
        int rowDifference = Math.abs(movementRow - pawnRow);
        int colDifference = Math.abs(movementColumn - pawnColumn);

        return rowDifference == colDifference;
    }

    boolean isSelectedFieldEmpty() {
        return (Board.board[movementRow][movementColumn] == Board.emptyField);
    }

    boolean ifPlayerPawnSelected() {
        return (ifPawnSelected() || ifQueenSelected());
    }

    boolean ifPawnSelected() {
        return Board.board[pawnRow][pawnColumn] == playerPAWN;
    }
    boolean ifQueenSelected() {
        return Board.board[pawnRow][pawnColumn] == playerQueenPAWN;
    }

    boolean isMoveUpward() {
        return (isRowAbovePawnSelected() || areTwoRowsAbovePawnSelected());
    }
    boolean isPawnMoveTooLow() {
        return (movementRow >= pawnRow);
    }

    boolean isPawnMoveTooHigh() {
        return (movementRow < twoRowsAbovePawn);
    }
    boolean isRowAbovePawnSelected() {
        return (movementRow == rowAbovePawn &&
                (movementColumn == moveLeftColumn || movementColumn == moveRightColumn));
    }

    boolean areTwoRowsAbovePawnSelected() {
        return (movementRow == twoRowsAbovePawn &&
                (movementColumn == doubleMoveLeftColumn || movementColumn == doubleMoveRightColumn));
    }

    boolean isEnemyOnPawnLeft() {
        return (Board.board[rowAbovePawn][moveLeftColumn] == Computer.computerPAWN
                && movementColumn == doubleMoveLeftColumn);
    }

    boolean isEnemyOnPawnRight() {
        return (Board.board[rowAbovePawn][moveRightColumn] == Computer.computerPAWN
                && movementColumn == doubleMoveRightColumn);
    }
}



