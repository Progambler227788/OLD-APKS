package com.example.connectfour;


public class ConnectFourGame {
    // 2. Add member variable board as a two-dimensional integer array, size 7 rows and 6 columns
    private final int[][] board;

    // Constants to be used
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    // 3. Define a custom constructor that instantiates the board array
    public ConnectFourGame() {
        board = new int[ROW][COL]; // rows and columns
    }

    // New member variable
    public int player = BLUE;

    // 4. Define method newGame
    public void newGame() {
        player = BLUE;

        // d. Iterate through the 2-d array board to initialize each element to the value zero (0)
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 0;
            }
        }
    }

    // method getDisc
    public int getDisc(int row, int col) {
        return board[row][col];
    }
    // method isBoardFull
    private boolean isBoardFull() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (board[i][j] == EMPTY) {

                    return false; // If there's an empty slot, the board is not full
                }
            }
        }
        player = -1;
        return true; // If no empty slots, the board is full
    }

    // method isGameOver
    public boolean isGameOver() {
        return isBoardFull() || isWin();
    }

    // method isWin
    public boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    // method checkHorizontal
    private boolean checkHorizontal() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL - 3; j++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i][j + 1] &&
                        board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    // method checkVertical
    private boolean checkVertical() {
        for (int i = 0; i < ROW - 3; i++) {
            for (int j = 0; j < COL ; j++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i + 1][j] &&
                        board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // New method checkDiagonal
    private boolean checkDiagonal() {
        // Check from top-left to bottom-right
        for (int i = 0; i < ROW - 3; i++) {
            for (int j = 0; j < COL - 3; j++) {
                if (board[i][j] != EMPTY && board[i][j] == board[i + 1][j + 1] &&
                        board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    return true;
                }
            }
        }

        // Check from top-right to bottom-left
        for (int i = 0; i < ROW - 3; i++) {
            for (int j = COL - 1; j >= 3; j--) {
                if (board[i][j] != EMPTY && board[i][j] == board[i + 1][j - 1] &&
                        board[i][j] == board[i + 2][j - 2] && board[i][j] == board[i + 3][j - 3]) {
                    return true;
                }
            }
        }

        return false;
    }


    // method setState
    public void setState(String gameState) {
        int index = 0;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = Character.getNumericValue(gameState.charAt(index++));
            }
        }
    }
    private void switchPlayer() {
        player = (player == BLUE) ? RED : BLUE;
    }

    // method selectDisc
    public void selectDisc(int ignoredR, int col) {

        for (int row = ROW - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY) {
                // Set the element in the array to the current player
                board[row][col] = player;
                // Uncomment to see data validation

//                Log.d("Data assigned",Integer.toString(player));
//                Log.d("Data at row",Integer.toString(row));
//                Log.d("Data at column",Integer.toString(col));
                // Switch players by updating the member variable player to the opponent (i.e., RED or BLUE)
                switchPlayer();

                // Break out of the loop after a disc has been placed on the board
                break;
            }
        }
    }


    // 5. Define method getState
    public String getState() {
        // a. Access level modifier: public
        // b. Return type: String
        // c. Parameter list: empty

        // d. Instantiate an instance of class StringBuilder
        StringBuilder stateBuilder = new StringBuilder();

        // e. Iterate through the 2-d array board to append each element to the StringBuilder object
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                stateBuilder.append(board[i][j]);
            }
        }

        // f. Return the StringBuilder object appending method toString()
        return stateBuilder.toString();
    }
}
