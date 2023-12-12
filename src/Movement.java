import java.util.ArrayList;
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

    /////// Queen Logic ////

    boolean isQueenMovementValid() {
        return isSelectedFieldEmpty() && isQueenMovementDiagonal();
    }

    boolean isQueenMovementDiagonal() {
        System.out.println("In isQueenMoveDiagonal"); // Flag isQueenMovediagonal
        boolean isDiagonal = false;
        System.out.println("Once");
            if ((movementRow / movementColumn) == ((pawnRow + 1) / (pawnColumn + 1)) ||
                    (movementRow / movementColumn) == ((pawnRow + 1) / (pawnColumn - 1)) ||
                    (movementRow / movementColumn) == ((pawnRow - 1) / (pawnColumn - 1)) ||
                    (movementRow / movementColumn) == ((pawnRow - 1) / (pawnColumn + 1))) {
                isDiagonal = true;
                System.out.println("Out isQueenMoveDiagonal");
            }
        return isDiagonal;
    }


    boolean ifPawnSelected() {
        return Board.board[pawnRow][pawnColumn] == Player.playerPAWN;
    }

    boolean ifQueenSelected() {
        return Board.board[pawnRow][pawnColumn] == Player.playerQueenPAWN;
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

    void performQueenMove() {
        System.out.println("In performQueenMove");
        if (isQueenMovementValid()) {
            System.out.println("Flag QueenMovementValid()"); // flag 1
            jumpQueenToField();
            System.out.println("Flag jumpQueenToField()"); // flag 2
            if (isEnemyOnQueenRoad()) {
                System.out.println("Flag isEnemyOnQueenRoad()");
                capturePawnWithQueen();
                System.out.println("Flag capturePawn();");
            }
        } else
            System.out.println("Message Invalid");
        //printMessageOfInvalidQueenMove();
        Player.currentPlayer = "Computer";
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


    void capturePawn() {
        System.out.println("In capturePawn");
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
        System.out.println("out capturePawn");
    }

    void capturePawnWithQueen(){
        System.out.println("Flag in capturePawnWithQueen");
        for (int i = Math.min(pawnRow, movementRow) + 1; i < Math.max(pawnRow, movementRow); i++) {
            for (int j = Math.min(pawnColumn, movementColumn) + 1; j < Math.max(pawnColumn, movementColumn); j++){

                if (Board.board[i][j] != Board.emptyField && Board.board[i][j] != Player.playerPAWN) {
                    Board.board[i][j] = Board.emptyField;
                }
            }
        }
            Player.compPawnNumbers -= 1;
        System.out.println("Flag in capturePawnWithQueen");
    }

    void jumpQueenToField() {
        Board.board[pawnRow][pawnColumn] = Board.emptyField;
        Board.board[movementRow][movementColumn] = Player.playerQueenPAWN;
    }

    void setQueen() {
        Board.board[movementRow][movementColumn] = Player.playerQueenPAWN;
    }



    boolean ifPlayerPawnSelected() {
        return (ifPawnSelected() || ifQueenSelected());
    }

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
        if (movementRow == 0)
            setQueen();
        else
            Board.board[movementRow][movementColumn] = Player.playerPAWN;
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
}



