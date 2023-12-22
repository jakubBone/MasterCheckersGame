import java.util.Random;

public class Computer {

    final static char COMPUTER_PAWN = 'X';
    static int compPawnNumbers = 12;
    final static char COMPUTER_QUEEN_PAWN = '#';
    private boolean movePerformed = false;

    public void makeMove() {
        for (int i = 8; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                if (Board.board[i][j] == COMPUTER_PAWN) {
                    System.out.println(i + " / " + j);
                    int compRow = i;
                    int compColumn = j;
                    int rowBelow = compRow + 1;
                    int twoRowsBelow = compRow + 2;
                    int threeRowsBelow = compRow + 3;

                    if (canCaptureWithoutRisk(rowBelow, compColumn, twoRowsBelow, threeRowsBelow)) {
                        System.out.println("capturePawn");
                        capturePawn(compRow, compColumn, rowBelow, twoRowsBelow);
                        System.out.println("Capture");
                        movePerformed = true;
                        return;
                    }else if (canMoveWithoutRisk(compRow, compColumn, threeRowsBelow)) {
                        jumpToField(compRow, compColumn, rowBelow);
                        System.out.println("Jump");
                        movePerformed = true;
                        return;
                    }
                }
            }
        }
    }

    private boolean canCaptureWithoutRisk(int rowBelow, int compColumn, int twoRowsBelow, int threeRowsBelow) {
        System.out.println("IN canCaptureWithoutRisk");
        if (compColumn > 2 && compColumn < 5) {
            if ((Board.board[twoRowsBelow][compColumn - 1] == Player.playerPAWN) &&
                    Board.board[twoRowsBelow][compColumn + 1] == Board.emptyField &&
                    Board.board[threeRowsBelow][compColumn + 2] == Board.emptyField) {
                return !isRiskAfterCapture(compColumn, twoRowsBelow, threeRowsBelow);
            }
        }else if (compColumn == 2) {
            System.out.println("2");
                if (Board.board[rowBelow][compColumn - 1] == Player.playerPAWN &&
                        Board.board[twoRowsBelow][compColumn - 2] == Board.emptyField) {
                        System.out.println("X");
                        return !isRiskAfterCapture(compColumn, twoRowsBelow, threeRowsBelow);
                }
        } else if ((Board.board[twoRowsBelow][compColumn + 1] == Player.playerPAWN) &&
                    Board.board[twoRowsBelow][compColumn - 1] == Board.emptyField &&
                    Board.board[threeRowsBelow][compColumn - 2] == Board.emptyField) {
                return !isRiskAfterCapture(compColumn, twoRowsBelow, threeRowsBelow);
        }
        System.out.println("OUT canCaptureWithoutRisk");
        return false;
    }


    private boolean canMoveWithoutRisk(int compRow, int compColumn, int threeRowsBelow) {
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;

        if ((compColumn == 0 || compColumn == 1) &&
                (Board.board[threeRowsBelow][rightColumn] == Board.emptyField  &&
                !isRiskAfterMove(compColumn, threeRowsBelow))) {
            return true;
        } else if ((compColumn == 6 || compColumn == 7) &&
                (Board.board[threeRowsBelow][leftColumn] == Board.emptyField  &&
                !isRiskAfterMove(compColumn, threeRowsBelow))) {
            return true;
        } else if (compColumn >= 2 && compColumn <= 5) {
            if ((Board.board[threeRowsBelow][leftColumn] == Board.emptyField ||
                    !isRiskAfterMove(compColumn, threeRowsBelow))) {
                return true;
            }
        }
        return false;
    }

    private boolean isRiskAfterMove(int compColumn, int threeRowsBelow) {
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;

        if ((compColumn == 0 || compColumn == 1) &&
                (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN )) {
            return true;
        } else if ((compColumn == 6 || compColumn == 7) &&
                (Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN)) {
            return true;
        } else if (compColumn >= 2 && compColumn <= 5) {
            if ((Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN) ||
                    (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN )) {
                return true;
            }
        }

        return false;
    }
    private boolean isRiskAfterCapture(int compColumn, int twoRowsBelow, int threeRowsBelow) {
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;

        if (compColumn == 0 || compColumn == 1) {
            System.out.println("XYZ");
            if ((Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN ||
                    Board.board[twoRowsBelow][compColumn + 3] == Player.playerPAWN)) {
                System.out.println("XYZQ");
                return true;
            }
        } else if (compColumn == 5 || compColumn == 6 || compColumn == 7) {
            if ((Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN ||
                    Board.board[twoRowsBelow][compColumn - 3] == Player.playerPAWN)) {
                return true;
            }
        } else if (compColumn >= 3 && compColumn <= 4) {
            if ((Board.board[threeRowsBelow][leftColumn] == Player.playerPAWN ||
                    Board.board[twoRowsBelow][compColumn - 3] == Player.playerPAWN) ||
                    (Board.board[threeRowsBelow][rightColumn] == Player.playerPAWN ||
                            Board.board[twoRowsBelow][compColumn + 3] == Player.playerPAWN)) {
                return true;
            }
        }
        return false;
    }
    private void jumpToField(int compRow, int compColumn, int rowBelow) {
        int leftColumn = compColumn - 1;
        int rightColumn = compColumn + 1;

        Random random = new Random();

        if (compColumn == 0 || compColumn == 7) {
            if (compColumn == 0 && Board.board[rowBelow][rightColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][rightColumn] = COMPUTER_PAWN;
            } else if (compColumn == 7 && Board.board[rowBelow][leftColumn] == Board.emptyField) {
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][leftColumn] = COMPUTER_PAWN;
            }
        } else {
            if ((Board.board[rowBelow][leftColumn] == Board.emptyField ||
                    Board.board[rowBelow][rightColumn] == Board.emptyField) &&
                    !isRiskAfterMove(compColumn, rowBelow)) {
                int randomColumn = (random.nextBoolean() ? leftColumn : rightColumn);
                Board.board[compRow][compColumn] = Board.emptyField;
                Board.board[rowBelow][randomColumn] = COMPUTER_PAWN;
            }
        }

        movePerformed = true;
    }

    private void capturePawn(int compRow, int compColumn, int rowBelow, int twoRowsBelow) {
        int columnOnLeft = compColumn - 1;
        int columnOnRight = compColumn + 1;
        int leftAfterCapture = compColumn - 2;
        int rightAfterCapture = compColumn + 2;

        if (compColumn == 0 || compColumn == 1) {
            if (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                performCapture(compRow, compColumn, rowBelow, columnOnRight, rightAfterCapture, twoRowsBelow);
            }
        } else if (compColumn == 6 || compColumn == 7) {
            if (isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField) {
                performCapture(compRow, compColumn, rowBelow, columnOnLeft, leftAfterCapture, twoRowsBelow);
            }
        } else if (compColumn >= 2 && compColumn <= 5) {
            if (isPlayerOnBothSides(compColumn, rowBelow) &&
                    Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField &&
                    Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                Random random = new Random();
                int randomColumn = (random.nextBoolean() ? leftAfterCapture : rightAfterCapture);
                performCapture(compRow, compColumn, rowBelow, (randomColumn == leftAfterCapture) ? columnOnLeft : columnOnRight, randomColumn,twoRowsBelow);
            } else if (isPlayerOnLeft(compColumn, rowBelow) && Board.board[twoRowsBelow][leftAfterCapture] == Board.emptyField) {
                performCapture(compRow, compColumn, rowBelow, columnOnLeft, leftAfterCapture, twoRowsBelow);
            } else if (isPlayerOnRight(compColumn, rowBelow) && Board.board[twoRowsBelow][rightAfterCapture] == Board.emptyField) {
                performCapture(compRow, compColumn, rowBelow, columnOnRight, rightAfterCapture, twoRowsBelow);
            }
        }
    }

    private void performCapture(int compRow, int compColumn, int rowBelow, int opponentColumn, int targetColumn, int twoRowsBelow) {
        Board.board[compRow][compColumn] = Board.emptyField;
        Board.board[rowBelow][opponentColumn] = Board.emptyField;
        Board.board[twoRowsBelow][targetColumn] = COMPUTER_PAWN;
        System.out.println(twoRowsBelow);
        Player.playerPawnNumbers -= 1;
        GameLogic.currentPlayer = "Human";
    }
    private boolean isPlayerOnLeft(int compColumn, int rowBelow) {
        int leftColumn = compColumn - 1;
        return (leftColumn >= 0 && Board.board[rowBelow][leftColumn] == Player.playerPAWN);
    }

    private boolean isPlayerOnRight(int compColumn, int rowBelow) {
        int rightColumn = compColumn + 1;
        return (rightColumn <= 7 && Board.board[rowBelow][rightColumn] == Player.playerPAWN);
    }

    private boolean isPlayerOnBothSides(int compColumn, int rowBelow) {
        return isPlayerOnLeft(compColumn, rowBelow) && isPlayerOnRight(compColumn, rowBelow);
    }


}