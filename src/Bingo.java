import java.util.ArrayList;
import java.util.Random;

/**
 * The Bingo class has a constructor to make either an empty board or a board with random numbers,
 * methods to check whether a certain tile on the board or any part of the board is filled (unused).
 * The class uses methods to check if a player has bingo, checking columns, rows, and diagonally,
 * and a toString() method to print the board.
 *
 * @author NBMoore87
 */
public class Bingo {
    private String color; //the color that the player selects
    private final String[][] board = new String[5][5];
    public Bingo() { //empty board constructor
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = " ";
            }
        }
    }

    public Bingo(Random rand, String color) { //random board constructor
        this.color = color;
        for (int i = 0; i < 5; i++) {
            ArrayList<Integer> nums = new ArrayList<>();
            int colBounds = (i+1)*15; //bounds for each column's numbers
            for (int j = 0; j < 5; j++) {
                int cellNum;
                do {
                    cellNum = rand.nextInt(colBounds-14, colBounds+1);
                } while (nums.contains(cellNum));
                nums.add(cellNum);
                board[i][j] = String.valueOf(cellNum);
            }
        }
        board[2][2] = "\u001B["+color+"m" + "X" + "\u001B[0m"; //free space
    }

    public void checkTile(int[] nums, int num) throws IndexOutOfBoundsException { //checks if a tile is the correct value
        if (!(board[nums[0]][nums[1]].equals("\u001B["+color+"m" + "X" + "\u001B[0m"))) {
            if (Integer.parseInt(board[nums[0]][nums[1]]) == num) {
                board[nums[0]][nums[1]] = "\u001B["+color+"m" + "X" + "\u001B[0m";
            }
        }
    }
    public boolean checkForNum(int num) { //checks if board has any x's (unused)
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!board[i][j].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                    if (Integer.parseInt(board[i][j]) == num) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean bingoCheck() { //general check for bingo
        int numBingos = 0;
        if (checkColumns()) numBingos++;
        if (checkRows()) numBingos++;
        if (checkDiag()) numBingos++;

        switch (numBingos) {
            case 1 -> System.out.println("BINGO! Thank you for playing :)");
            case 2 -> System.out.println("DOUBLE BINGO! Thank you for playing :)");
            case 3 -> System.out.println("TRIPLE BINGO!!! Thank you for playing! :D");
        }
        return numBingos > 0;
    }

    public boolean checkColumns() { //checks through each column for a bingo
        for (int i = 0; i < 5; i++) {
            int xCounter = 0;
            for (int j = 0; j < 5; j++) {
                if (board[i][j].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                    xCounter++;
                }
            }
            if (xCounter == 5) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRows() { //checks through each row for a bingo
        for (int i = 0; i < 5; i++) {
            int xCounter = 0;
            for (int j = 0; j < 5; j++) {
                if (board[j][i].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                    xCounter++;
                }
            }
            if (xCounter == 5) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDiag() { //checks through each diagonal for a bingo
        int xCounter = 0;
        for (int i = 0; i < 5; i++) { // top left diagonal
            if (board[i][i].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                xCounter++;
            }
        }
        if (xCounter == 5) {
            return true;
        }

        xCounter = 0;
        for (int i = 4; i >= 0; i--) { // top right diagonal
            if (board[i][i].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                xCounter++;
            }
        }
        if (xCounter == 5) {
            return true;
        }

        xCounter = 0;
        for (int i = 0; i < 5; i++) { // bottom left diagonal
            if (board[i][4-i].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                xCounter++;
            }
        }
        if (xCounter == 5) {
            return true;
        }

        xCounter = 0;
        for (int i = 4; i >= 0; i--) { // bottom right diagonal
            if (board[4-i][i].equals("\u001B["+color+"m" + "X" + "\u001B[0m")) {
                xCounter++;
            }
        }
        return xCounter == 5;
    }


    public String toString() { //prints a bingo card, with rows to guide the player's input
        StringBuilder s = new StringBuilder();
        String[] header = {"B", "I", "N", "G", "O"};
        s.append("ROW | ");
        for (int i = 0; i < 4; i++) {
            s.append(header[i]).append("  | ");
        }
        s.append(header[4]).append("\n");

        for (int i = 0; i < 5; i++) {
            s.append(i+1).append("   | ");
            for (int j = 0; j < 4; j++) {
                try {
                    if (Integer.parseInt(board[j][i]) < 10) {
                        s.append(board[j][i]).append("  | ");
                    } else {
                        s.append(board[j][i]).append(" | ");
                    }
                } catch (NumberFormatException e) {
                    s.append(board[j][i]).append("  | ");
                }
            }
            s.append(board[4][i]);
            s.append("\n");
        }
        return s.toString();
    }

}
