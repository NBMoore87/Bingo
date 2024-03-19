import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Runs the bingo game using the gameLoop() method.
 *
 * @author NBMoore87
 */

public class Game {
    static boolean bingo = false; //true when player gets bingo
    static int[] playerCoords = new int[2]; //stores the players input to translate to board coordinates

    public static int coords(String coordy) { //returns a value for the column that the player inputs
        int column = -1;
        switch (coordy) {
            case "B" -> column = 0;
            case "I" -> column = 1;
            case "N" -> column = 2;
            case "G" -> column = 3;
            case "O" -> column = 4;
        }
        return column;
    }

    public static void gameLoop() { //plays bingo
        Random rnd = new Random();
        Bingo game = new Bingo(rnd);

        int currNum = rnd.nextInt(1, 76);
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(currNum);
        System.out.println(game.toString());

        while (!bingo) { //bingo game loop
            try { //detects invalid input
                Scanner scnr = new Scanner(System.in);

                while (nums.contains(currNum)) { //if the number has already been used, generate a new one
                    currNum = rnd.nextInt(1, 76);
                }
                nums.add(currNum);

                scnr.useDelimiter("");
                System.out.print("Callout: ");
                if (currNum <= 15) System.out.print("B" + currNum);
                else if (currNum <= 30) System.out.print("I" + currNum);
                else if (currNum <= 45) System.out.print("N" + currNum);
                else if (currNum <= 60) System.out.print("G" + currNum);
                else if (currNum <= 75) System.out.print("O" + currNum);
                System.out.println("\nType a coordinate (i.e B1) or 'P' to pass");

                String out = scnr.next();
                if (out.equals("P")) {
                    System.out.println(game.toString());
                    continue;
                }

                int num = scnr.nextInt();
                playerCoords[0] = coords(out);
                playerCoords[1] = num - 1;
                try { //detects invalid input
                    game.checkTile(playerCoords, currNum);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error: Input not valid");
                }
                System.out.println(game.toString());

                if (game.bingoCheck()) {
                    bingo = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input not valid");
            }
        }
    }

    public static void main(String[] args) { //main method
        System.out.println("Welcome to BINGO!");
        gameLoop();
    }
}
