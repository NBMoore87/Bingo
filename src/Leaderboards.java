import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Houses all methods for the leaderboards (a work in progress)
 * Can read and print to the leaderboards file, as well as wipe the data with the unused wipeLeaderboards() function
 *
 * @author NBMoore87
 */
public class Leaderboards {
    private static final int numSlots = 5; //number of slots on the leaderboard
    private static int numScores; //number of scores total (should be size of scores list)
    private static ArrayList<String> names = new ArrayList<>(); //names on the leaderboards
    private static ArrayList<Integer> scores = new ArrayList<>(); //scores on the leaderboards

    private void wipeLeaderboards() throws FileNotFoundException { //wipes the leaderboard
        PrintWriter pw = new PrintWriter("leaderboards.txt");
        pw.println("----- LEADERBOARDS -----");
        pw.close();
    }

    /**
     * Any score pushed off of the leaderboard will be in the archive.txt file
     */
    public static void archives(String name, int score) throws FileNotFoundException {
        File in = new File("archive.txt");
        Scanner scan = new Scanner(in);
        StringBuilder archive = new StringBuilder();
        while (scan.hasNextLine()) {
            archive.append(scan.nextLine()).append("\n");
        }
        archive.append(name).append(" ").append(score);
        PrintWriter pw = new PrintWriter("archive.txt");
        pw.print(archive.toString());
        pw.close();
    }

    /**
     * Gets the amount of scores on the leaderboard by reading the # of lines in the file
     */
    public static void getNumScores() throws FileNotFoundException {
        numScores = 0;
        File input = new File("leaderboards.txt");
        Scanner numScan = new Scanner(input);
        numScan.nextLine();
        while (numScan.hasNextLine()) {
            numScores++;
            numScan.nextLine();
        }
    }

    /**
     * Reads the leaderboards file and updates names and scores variables
     */
    public static void readBoard() throws FileNotFoundException {
        numScores = 0;
        int length = scores.size();
        for (int i = 0; i < length; i++) {
            scores.remove(0);
            names.remove(0);
        }
        File leaderboards = new File("leaderboards.txt");
        Scanner scnr = new Scanner(leaderboards);
        scnr.nextLine();
        while (scnr.hasNextLine()) {
            String lineString = scnr.nextLine();
            Scanner line = new Scanner(lineString);
            line.useDelimiter(" ");
            line.next();
            String name = line.next();
            int score = Integer.parseInt(line.next());
            names.add(name);
            scores.add(score);
            numScores++;
        }
    }

    /**
     * Prints the leaderboards to the console
     * The same as printLeaderboards() but prints to console instead of PrintWriter
     */
    public static void printLeaderboardsConsole() {
        try {
            readBoard();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Leaderboards file not found");
        }
        System.out.println("----- LEADERBOARDS -----");
        for (int i = 0; i < numScores; i++) {
            System.out.println(i + 1 + ") " + names.get(i) + " " + scores.get(i));
        }

        if (numScores == 0) {
            System.out.println("No current leaderboards");
        }
    }

    /**
     * Prints the leaderboards to leaderboards.txt
     */
    public static void printLeaderboards() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("leaderboards.txt");
        pw.println("----- LEADERBOARDS -----");

        for (int i = 0; i < numScores; i++) {
            pw.println(i+1 + ") " + names.get(i) + " " + scores.get(i));
        }
        pw.close();
    }

    /**
     * Checks if a score will make the leaderboard, with exceptions for how many names are on the leaderboards
     */
    public static void checkScore(int score) {
        boolean alreadyScored = false;
        Scanner scnr = new Scanner(System.in);
        for (int leaderboardScores : scores) { //will not run if there are no scores on the leaderboard
            if (score < leaderboardScores) { //checks if a score is less than any score on the leaderboards
                System.out.println("Congratulations, you are on the leaderboard! Enter your initials:");
                String name;
                while (true) {
                    name = scnr.next();
                    if (name.length() == 3) {
                        break;
                    } else {
                        System.out.println("Error: Initials must be length 3"); //name must be 3 chars long
                    }
                }
                int index = scores.indexOf(leaderboardScores);
                scores.add(index, score);
                numScores++;
                names.add(index, name); //adds the scores and names at the correct index
                if (numScores == numSlots+1) { //if a new scores exceeds the number of scores on the leaderboard
                    try {
                        archives(names.getLast(), scores.getLast());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    names.removeLast();
                    scores.removeLast();
                    numScores--;
                }
                alreadyScored = true;
                break;
            }
        }
        if (numScores == 0) { //runs if no scores are on the leaderboard
            System.out.println("Congratulations, you are on the leaderboard! Enter your initials:");
            String name;
            while (true) {
                name = scnr.next();
                if (name.length() == 3) {
                    break;
                } else {
                    System.out.println("Error: Initials must be length 3");
                }
            }
            scores.add(0, score);
            numScores++;
            names.add(0, name);
            alreadyScored = true;
        }

        if (scores.size() < numSlots && !alreadyScored) { //runs if the score is not greater than any others, but not all slots are filled
            System.out.println("Congratulations, you are on the leaderboard! Enter your initials:");
            String name;
            while (true) {
                name = scnr.next();
                if (name.length() == 3) {
                    break;
                } else {
                    System.out.println("Error: Initials must be length 3");
                }
            }
            scores.add(score);
            numScores++;
            names.add(name);
        }
    }
}
