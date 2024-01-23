package Project2_108;
/*
6413108 Palarp Wasuwat
6413211 Kobkit Ruangsuriyakit
*/

import java.util.Arrays;

public class LightBoard {
    private String[][] board;
    private int[] brokenPosition = {-1, -1};  // [0] = row, [1] = column
    protected int movingRow = -1, movingCol = -1;
    // Text coloring
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    public LightBoard(String[] lightBits , int n) {
        board = new String[n][n];
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                board[i][j] = lightBits[n*i+j];
            }
        }
    }

    // constructor for finding solution path
    public LightBoard(String[][] lightBits , int n, int movingRow, int movingCol) {
        board = new String[n][n];
        for(int i=0;i<n;i++) board[i] = Arrays.copyOf(lightBits[i], n); // deep copy
        this.movingRow = movingRow;
        this.movingCol = movingCol;
    }

    public void toggleLight (int row , int column) {
        movingRow = row;
        movingCol = column;
        board[row][column] = (board[row][column].equals("0")) ? "1" : "0";
        if (row == brokenPosition[0] && column == brokenPosition[1]) {
            if (row + 1 < board.length) {
                if (column + 1 < board.length)
                    board[row + 1][column + 1] = (board[row + 1][column + 1].equals("1")) ? "0" : "1";
                if (column - 1 >= 0) board[row + 1][column - 1] = (board[row + 1][column - 1].equals("1")) ? "0" : "1";
            }
            if (row - 1 >= 0) {
                if (column + 1 < board.length)
                    board[row - 1][column + 1] = (board[row - 1][column + 1].equals("1")) ? "0" : "1";
                if (column - 1 >= 0) board[row - 1][column - 1] = (board[row - 1][column - 1].equals("1")) ? "0" : "1";
            }
        }
        else {
            if (column + 1 < board.length) board[row][column+1] = (board[row][column+1].equals("1")) ? "0" : "1";
            if (column - 1 >= 0) board[row][column-1] = (board[row][column-1].equals("1")) ? "0" : "1";
            if (row + 1 < board.length) board[row+1][column] = (board[row+1][column].equals("1")) ? "0" : "1";
            if (row - 1 >= 0) board[row-1][column] = (board[row-1][column].equals("1")) ? "0" : "1";
        }
    }

    public void setBrokenPosition (int row, int column) {
        brokenPosition[0] = row;
        brokenPosition[1] = column;
    }

    public String[][] getBoard() { return board; }

    public int[] getBrokenPosition() { return  brokenPosition; }

    public void  printBoard () {
        String light;

        System.out.print(" ".repeat(7) + "|");
        for (int i = 0; i < board.length; i++) System.out.printf(" col%2s |", i);
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.printf(" row%2s |", i);
            for (int j = 0; j < board.length; j++) {
                if (i == brokenPosition[0] && j == brokenPosition[1]) light = board[i][j] + "x";
                else light = board[i][j];
                if (i == movingRow && j == movingCol) System.out.print(ANSI_RED);
                System.out.printf("   %-2s  " + ANSI_RESET + "|", light);
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void printLightBit() {
        for(int i=0; i<board.length;i++){
            for(int j=0; j<board.length;j++){
                System.out.print(board[i][j]);
            }
            System.out.print(" ");
        }
    }

    @Override
    public boolean equals(Object o) {
        LightBoard otherBoard = (LightBoard) o;
        return Arrays.deepEquals(this.board, otherBoard.getBoard());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
